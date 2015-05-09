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

    private val savedDocumentRoot = if (null == System.getenv("SIMPLERPG_PLAYER_ROOT")) ""
        else System.getenv("SIMPLERPG_PLAYER_ROOT")

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val writer = openPlayerFile(currentPlayer.name)
        writer.println(currentPlayer)
        writer.flush
        writer.close
        nextAction
    }

    protected def openPlayerFile(playerName: String): PrintWriter = {
        new PrintWriter(new FileWriter(savedDocumentRoot + playerName + ".json"))
    }
}