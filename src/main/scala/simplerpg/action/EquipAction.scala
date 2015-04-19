/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.Player
import simplerpg.World

final class EquipAction(category: String, name: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        try {
            category match {
                case "weapon" => currentPlayer.equipWeapon(name)
                case "armor"  => currentPlayer.equipArmor(name)
                case _        => return printAction(s"Cannot equip items from $category")
            }
        } catch {
            case e: Exception => return printAction(e.getMessage)
        }
        printAction(s"Equipped $name")
    }
}
