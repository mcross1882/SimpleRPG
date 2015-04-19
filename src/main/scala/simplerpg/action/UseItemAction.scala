/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.Player
import simplerpg.World

final class UseItemAction(itemName: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val dropItemAction = Some(new DropAction("item", itemName))
        Some(new PrintAction(s"Applied $itemName", dropItemAction))
    }
}
