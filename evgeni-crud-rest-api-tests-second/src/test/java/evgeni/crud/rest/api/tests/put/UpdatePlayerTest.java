package evgeni.crud.rest.api.tests.put;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

import org.testng.annotations.Test;

import entities.Player;
import evgeni.crud.rest.tests.utils.TestUtils;

public class UpdatePlayerTest extends TestUtils {

    private void update(final Player player) throws FileNotFoundException {
	player.setName(UUID.randomUUID().toString());
	player.incrementAndGet();
	final String playerJSON = gson.toJson(player);
	app.updatePlayer(player.getId(), playerJSON);
    }

    @Test
    public void updatePlayerTest() throws FileNotFoundException {
	final Player player = addPlayer();
	final List<Player> players = gson.fromJson(app.getPlayers(), playersAllType);
	assertTrue(players.size() == 1);
	update(player);
	final Player actualPlayer = app.getPlayer(player.getId());
	assertEquals(actualPlayer, player);
    }

}
