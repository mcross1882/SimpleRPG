/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.Player
import simplerpg.World

/**
 * Actions provide a linkable way to react to the input commands.
 * All input is represented as an array of strings. The first element
 * is considered the initial command which may consist of sub commands
 * or arguments. Refer to the subclass documentation for more
 * information.
 *
 * @author Matthew Cross <matthew@koddi.com>
 */
trait Action {
    def run(currentPlayer: Player, world: World): Option[Action]

    protected def printAction(message: String): Option[Action] = {
        Some(new PrintAction(message))
    }
}
