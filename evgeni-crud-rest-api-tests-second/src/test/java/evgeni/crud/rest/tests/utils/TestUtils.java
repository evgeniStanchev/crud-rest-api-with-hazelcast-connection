package evgeni.crud.rest.tests.utils;

import static org.testng.Assert.assertFalse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeTest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import crud.rest.api.tests.app.App;
import entities.Player;

public class TestUtils {

    private static final String singlePlayerPath = "src/test/resources/create.json";
    private static final String multiplePlayersPath = "src/test/resources/create2.json";
    protected static final App app = new App();
    protected static final Type playersAllType = new TypeToken<ArrayList<Player>>() {
    }.getType();
    protected static final Gson gson = new Gson();

    @BeforeTest
    public void deleteInfo() {
	app.removePlayers();
    }

    protected static List<Player> addPlayers() throws FileNotFoundException {
	final List<Player> players = gson.fromJson(new FileReader(multiplePlayersPath), playersAllType);
	assertFalse(players.isEmpty());
	final List<Player> addedPlayers = app.addPlayers(gson.toJson(players));
	return addedPlayers;
    }

    protected Player addPlayer() throws FileNotFoundException {
	final List<Player> players = gson.fromJson(new FileReader(singlePlayerPath), playersAllType);
	final List<Player> addedPlayers = app.addPlayers(gson.toJson(players));
	return addedPlayers.get(0);
    }

}
