/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import java.io.{PrintWriter, FileWriter}
import simplerpg.{Player, World}

final class SavePlayerAction(nextAction: Option[Action] = None) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val writer = openPlayerFile(currentPlayer.name)
        writer.println(currentPlayer)
        writer.flush
        writer.close
        Some(new PrintAction("Player data successfully saved!", nextAction))
    }

    protected def openPlayerFile(playerName: String): PrintWriter = {
        new PrintWriter(new FileWriter("data/players/" + playerName + ".json"))
    }
}