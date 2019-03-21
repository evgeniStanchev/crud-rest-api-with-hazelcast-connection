package hazelcast;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;

@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class HazelCastConfiguration {

    public static final String HAZELCAST_HOST = "localhost";
    public static final String HAZELCAST_PORT = "5701";
    private HazelcastInstance hazelcastInstance;

    private IQueue<DistributedRequest> queue;

    private IMap<Long, DistributedResponse> map;

    public IMap<Long, DistributedResponse> getIMap() {
	return this.map;
    }

    public IQueue<DistributedRequest> getIQueue() {
	return queue;
    }

    @Schedule(second = "*/2", minute = "*", hour = "*")
    public void repeat() {
    }

    @PostConstruct
    public void init() {
	hazelcastInstance = Hazelcast.newHazelcastInstance();
	queue = hazelcastInstance.getQueue("tasks");
	map = hazelcastInstance.getMap("responses");
	new Thread() {
	    @Override
	    public void run() {
		while (true) {
		    checkQueue();
		    try {
			Thread.sleep(1L);
		    } catch (InterruptedException e) {
			e.printStackTrace();
			break;
		    }
		}
	    }
	}.start();
    }

    private void checkQueue() {
	synchronized (queue) {
	    if (!queue.isEmpty()) {
		final DistributedRequest request = queue.poll();
		final Long id = request.getId();
		final DistributedResponse response = new DistributedResponse(id, tryToExecute(request));
		map.putIfAbsent(id, response);
	    }
	}
    }

    private Object tryToExecute(final DistributedRequest simpleTask) {
	final Object lookupObject = BeanLocator.lookup(simpleTask.getLookupName());
	Method m;
	try {
	    m = lookupObject.getClass().getDeclaredMethod(simpleTask.getMethodName(), simpleTask.getTypes());
	    return m.invoke(lookupObject, simpleTask.getArgs());
	} catch (final Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @PreDestroy
    public void uninit() {
	hazelcastInstance.getLifecycleService().shutdown();
    }
}
