/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Item, Player, World}

final class UseItemAction(itemName: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val dropItemAction = Some(new DropAction("item", itemName))
        val actionToPrint = Some(new PrintAction(s"Applied $itemName", dropItemAction))

        findItemOnPlayer(currentPlayer) match {
            case Some(item) => world.update(applyEffects(currentPlayer, item))
            case None =>
        }
        actionToPrint
    }

    protected def findItemOnPlayer(player: Player): Option[Item] = {
        player.inventory.items.find(_.name equals itemName)
    }

    protected def applyEffects(player: Player, item: Item): Player = {
        player.vitals += item.effects
        player.capMaxVitals
        player
    }
}
