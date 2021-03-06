/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Location, Player, Store, ItemStore, WeaponStore, ArmorStore, SellableObject, World}

final class BuyAction(category: String, itemName: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val message = world.getCurrentLocation(currentPlayer) match {
            case Some(store: ItemStore) => buyItem(currentPlayer, store)
            case Some(store: WeaponStore) => buyItem(currentPlayer, store)
            case Some(store: ArmorStore) => buyItem(currentPlayer, store)
            case Some(location) => s"You are not in a store"
            case None => s"You are not in a store"
        }
        printAction(message)
    }

    protected def buyItem[T](currentPlayer: Player, store: Store[T]): String = {
        store.buy(currentPlayer, itemName)
        s"Bought $itemName"
    }
}
