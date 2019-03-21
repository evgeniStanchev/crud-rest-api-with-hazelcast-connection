package crud.rest.api.tests.app;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IdGenerator;

public class Hazelcast {

    private static final String HAZELCAST_HOST = "localhost";
    private static final String HAZELCAST_PORT = "5701";

    public static HazelcastInstance createHazelcastInstance() {
	final ClientConfig config = new ClientConfig();
	final ClientNetworkConfig networkConfig = new ClientNetworkConfig()
		.addAddress(HAZELCAST_HOST + ":" + HAZELCAST_PORT);
	config.setNetworkConfig(networkConfig);
	HazelcastInstance hazelcastInstanceClient = HazelcastClient.newHazelcastClient(config);
	return hazelcastInstanceClient;
    }

    public static Long getNewId(final HazelcastInstance hzlcInstanceClient) {
	final IdGenerator idGenerator = hzlcInstanceClient.getIdGenerator("newid");
	final Long id = idGenerator.newId();
	return id;
    }

    public static String getHost() {
	return HAZELCAST_HOST;
    }

    public static String getPort() {
	return HAZELCAST_PORT;
    }

}
