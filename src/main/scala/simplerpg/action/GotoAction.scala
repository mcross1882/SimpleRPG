/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.Player
import simplerpg.World

final class GotoAction(newLocationName: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        if (!world.canPlayerMoveTo(currentPlayer, newLocationName)) {
            return printAction(s"$newLocationName cannot be reached from here")
        }
        world.movePlayer(currentPlayer, newLocationName)
        printAction(s"Moved to $newLocationName")
    }
}
