/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.Player
import simplerpg.World

final class WhereAction extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val locationMessage = world.getCurrentLocation(currentPlayer) match {
            case Some(location) => location.name
            case None => s"Could not find ${currentPlayer.name}"
        }
        printAction(locationMessage)
    }
}
