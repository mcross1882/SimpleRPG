/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.Player
import simplerpg.World

final class LeaveAction extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        world.leave(currentPlayer)
        printAction(s"Player has left")
    }
}