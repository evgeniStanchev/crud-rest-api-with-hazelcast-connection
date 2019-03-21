package services;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import entities.Player;

@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PlayerService {

    private final Map<String, Player> players = new ConcurrentHashMap<>();
    private static Gson gson = new Gson();

    private final ConcurrentHashMap<String, Lock> locks = new ConcurrentHashMap<>();

    @Schedule(second = "*/2", minute = "*", hour = "*")
    public void repeat() {
	System.out.println(players.values());
    }

    @PostConstruct
    public void init() {
    }

    public String addPlayers(final Reader in) {
	try {
	    return addPlayers(IOUtils.toString(in));
	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public String addPlayers(final String playersJSON) {
	final Type playersAllType = new TypeToken<ArrayList<Player>>() {
	}.getType();
	final List<Player> playersList = gson.fromJson(playersJSON, playersAllType);
	for (Player player : playersList) {
	    final Player newPlayer = new Player(player.getName(), player.getAge(), player.getPosition());
	    this.players.put(newPlayer.getId(), newPlayer);
	}
	return gson.toJson(players.values());
    }

    public String updatePlayer(final String id, final String playerJSON) {
	final Player updatedPlayer = gson.fromJson(playerJSON, Player.class);
	final int age = updatedPlayer.getAge();
	final String name = updatedPlayer.getName();
	if (players.get(id) != null) {
	    try {
		if (locks.computeIfAbsent(id, lockId -> new ReentrantLock()).tryLock(1, TimeUnit.SECONDS)) {
		    final Player player = players.get(id);
		    if ((player != null) && (!player.getName().equals(name) || player.getAge() != age)) {
			player.setName(name);
			player.setAge(age);
			player.incrementAndGet();
		    }
		}
	    } catch (final InterruptedException e) {
		e.printStackTrace();
	    } finally {
		locks.get(id).unlock();
	    }
	}
	return gson.toJson(players);
    }

    public String updatePlayer(final String id, final Reader in) {
	try {
	    return updatePlayer(id, IOUtils.toString(in));
	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

    public String removePlayer(final String id) {
	final Player player = players.remove(id);
	final String playerJSON = gson.toJson(player);
	return playerJSON;
    }

    public void removePlayers() {
	players.clear();
    }

    public String getPlayer(final String id) {
	return gson.toJson(players.get(id));
    }

    public String getPlayers() {
	return gson.toJson(players.values());
    }
}
