/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.Player
import simplerpg.World

final class DropAction(category: String, name: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        try {
            category match {
                case "weapon" => currentPlayer.dropWeapon(name)
                case "armor"  => currentPlayer.dropArmor(name)
                case "item"   => currentPlayer.dropItem(name)
                case _        => return printAction(s"Cannot drop items from $category")
            }
        } catch {
            case e: Exception => return printAction(e.getMessage)
        }
        printAction(s"Dropped $name")
    }
}
