package crud.rest.api.tests.app;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IQueue;

import entities.Player;
import hazelcast.DistributedRequest;
import hazelcast.DistributedResponse;

public class App {

    private final HazelcastInstance hazelcastInstanceClient = Hazelcast.createHazelcastInstance();
    private final IQueue<DistributedRequest> queue = hazelcastInstanceClient.getQueue("tasks");
    private final IMap<Long, DistributedResponse> map = hazelcastInstanceClient.getMap("responses");
    private final Gson gson = new Gson();
    private final Type playersAllType = new TypeToken<ArrayList<Player>>() {
    }.getType();

    public List<Player> addPlayers(final String playersJSON) {
	final Long newId = Hazelcast.getNewId(hazelcastInstanceClient);
	final DistributedRequest req = new DistributedRequest(Hazelcast.getHost(), newId, new Object[] { playersJSON },
		new Class<?>[] { String.class },
		"java:global/evgeni-crud-rest-api-second-0.0.1-SNAPSHOT/PlayerService!services.PlayerService",
		"addPlayers");
	queue.add(req);
	waitfs();
	final String response = (String) map.get(newId).getResponse();
	final List<Player> players = gson.fromJson(response, playersAllType);
	return players;
    }

    public void updatePlayer(final String id, final String playerJSON) {
	final Long newId = Hazelcast.getNewId(hazelcastInstanceClient);
	DistributedRequest req = new DistributedRequest(Hazelcast.getHost(), newId, new Object[] { id, playerJSON },
		new Class<?>[] { String.class, String.class },
		"java:global/evgeni-crud-rest-api-second-0.0.1-SNAPSHOT/PlayerService!services.PlayerService",
		"updatePlayer");
	queue.add(req);
	waitfs();
    }

    public void deletePlayer(final String id) {
	final Long newId = Hazelcast.getNewId(hazelcastInstanceClient);
	final DistributedRequest req = new DistributedRequest(Hazelcast.getHost(), newId, new Object[] { id },
		new Class<?>[] { String.class },
		"java:global/evgeni-crud-rest-api-second-0.0.1-SNAPSHOT/PlayerService!services.PlayerService",
		"removePlayer");
	queue.add(req);
	waitfs();
    }

    public Player getPlayer(final String id) {
	final Long newId = Hazelcast.getNewId(hazelcastInstanceClient);
	final DistributedRequest req = new DistributedRequest(Hazelcast.getHost(), newId, new Object[] { id },
		new Class<?>[] { String.class },
		"java:global/evgeni-crud-rest-api-second-0.0.1-SNAPSHOT/PlayerService!services.PlayerService",
		"getPlayer");
	queue.add(req);
	waitfs();
	final String response = (String) map.get(newId).getResponse();
	final Player player = gson.fromJson(response, Player.class);
	return player;
    }

    public String getPlayers() {
	final Long newId = Hazelcast.getNewId(hazelcastInstanceClient);
	final DistributedRequest req = new DistributedRequest(Hazelcast.getHost(), newId, new Object[] {},
		new Class<?>[] {},
		"java:global/evgeni-crud-rest-api-second-0.0.1-SNAPSHOT/PlayerService!services.PlayerService",
		"getPlayers");
	queue.add(req);
	waitfs();
	return (String) map.get(newId).getResponse();
    }

    public void removePlayers() {
	final Long newId = Hazelcast.getNewId(hazelcastInstanceClient);
	final DistributedRequest req = new DistributedRequest(Hazelcast.getHost(), newId, new Object[] {},
		new Class<?>[] {},
		"java:global/evgeni-crud-rest-api-second-0.0.1-SNAPSHOT/PlayerService!services.PlayerService",
		"removePlayers");
	queue.add(req);
	waitfs();
    }

    public void removePlayer(final String id) {
	final Long newId = Hazelcast.getNewId(hazelcastInstanceClient);
	final DistributedRequest req = new DistributedRequest(Hazelcast.getHost(), newId, new Object[] { id },
		new Class<?>[] { String.class },
		"java:global/evgeni-crud-rest-api-second-0.0.1-SNAPSHOT/PlayerService!services.PlayerService",
		"removePlayer");
	queue.add(req);
	waitfs();
    }

    private void waitfs() {
	try {
	    Thread.sleep(100);
	} catch (final InterruptedException e) {
	    e.printStackTrace();
	}
    }
}
