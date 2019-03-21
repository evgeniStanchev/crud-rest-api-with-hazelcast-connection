package evgeni.crud.rest.api.tests.get;

import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.List;

import org.testng.annotations.Test;

import com.beust.jcommander.internal.Lists;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import entities.Player;
import evgeni.crud.rest.tests.utils.TestUtils;

public class GetAndAddPlayersTests extends TestUtils {

    @Test
    public void getPlayersTest() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
	final List<Player> addedPlayers = addPlayers();

	final String actualPlayersJSON = app.getPlayers();
	final List<Player> actualPlayers = gson.fromJson(actualPlayersJSON, playersAllType);
	for (Player player : actualPlayers) {
	    assertTrue(addedPlayers.contains(player));
	}
    }

}
