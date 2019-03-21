package evgeni.crud.rest.api.tests.delete;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.List;

import org.testng.annotations.Test;

import entities.Player;
import evgeni.crud.rest.tests.utils.TestUtils;

public class RemovePlayerTests extends TestUtils {

    private void removePlayer(final Player player) throws FileNotFoundException {
	app.removePlayer(player.getId());
    }

    @Test
    public void removePlayerTest() throws FileNotFoundException {
	final Player player = addPlayer();
	removePlayer(player);
	final String playersJSON = app.getPlayers();
	final List<Player> players = gson.fromJson(playersJSON, playersAllType);
	assertTrue(players.size() == 0);
	assertFalse(players.contains(player));
    }

}
