package evgeni.crud.rest.api.tests.get;

import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.testng.annotations.Test;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import entities.Player;
import evgeni.crud.rest.tests.utils.TestUtils;

public class GetPlayerTests extends TestUtils {

    @Test
    public void getPlayerTest() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
	final Player player = addPlayer();
	final Player actualPlayer = app.getPlayer(player.getId());
	assertEquals(actualPlayer, player);
    }

}
