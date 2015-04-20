/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Location, Player, Store, ItemStore, WeaponStore, ArmorStore, SellableObject, World}

final class SellAction(category: String, itemName: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val message = world.getCurrentLocation(currentPlayer) match {
            case Some(store: ItemStore) => sellItem(currentPlayer, store)
            case Some(store: WeaponStore) => sellItem(currentPlayer, store)
            case Some(store: ArmorStore) => sellItem(currentPlayer, store)
            case Some(location) => s"You are not in a store"
            case None => s"You are not in a store"
        }
        printAction(message)
    }

    protected def sellItem[T](currentPlayer: Player, store: Store[T]): String = {
        store.sell(currentPlayer, itemName)
        s"Sold $itemName"
    }
}
