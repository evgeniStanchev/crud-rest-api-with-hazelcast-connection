package hazelcast;

import javax.ejb.EJB;

import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;

/**
 * 
 * @author deyan
 * 
 */
public class DistributedTask implements Runnable {

    @EJB
    private HazelCastConfiguration hzcast;
    
    
    private final DistributedRequest distributedRequest;
    
    
    private DistributedResponse distributedResponse;

    public DistributedTask(DistributedRequest distributedRequest) {
	super();
	this.distributedRequest = distributedRequest;
    }

    public DistributedResponse getDistributedResponse() {
	return distributedResponse;
    }

    public DistributedRequest getDistributedRequest() {
	return distributedRequest;
    }

    public void run() {
	try {
	    final IQueue<DistributedRequest> taskQueue = hzcast.getIQueue();
	    taskQueue.add(this.distributedRequest);

	    final IMap<Long, DistributedResponse> responses = hzcast.getIMap();

	    final Long id = this.distributedRequest.getId();

	    long timeTaken = 0l;

	    while (true) {
		final DistributedResponse distributedResponse = responses.remove(id);

		if (distributedResponse != null) {
		    this.distributedResponse = distributedResponse;
		    break;
		}

		Thread.sleep(1L);
		timeTaken += 1;

		if (timeTaken > 25000L) {
		    throw new Exception("DR => " + this.distributedRequest + " has timeout");
		}
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    this.distributedResponse = new DistributedResponse(null, null);
	}

    }

}
