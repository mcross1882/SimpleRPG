/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.Player
import simplerpg.World

final class PrintAction(message: String, nextAction: Option[Action] = None) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        println(s"[${currentPlayer.name}] $message")
        nextAction
    }
}
