package evgeni.crud.rest.api.tests.connection;

import org.testng.annotations.Test;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;

public class ConnectionTest {

    @Test
    public void connectHazelcast() {
	try {
	    final ClientConfig config = new ClientConfig();
	    final ClientNetworkConfig networkConfig = new ClientNetworkConfig().addAddress(
		    crud.rest.api.tests.app.Hazelcast.getHost() + ":" + crud.rest.api.tests.app.Hazelcast.getPort());
	    config.setNetworkConfig(networkConfig);
	    HazelcastClient.newHazelcastClient(config);
	} catch (final Exception e) {
	    throw new RuntimeException(e);
	}
    }

}
