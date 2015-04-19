/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Location, Player, Store, World}

final class ShowStoreInventoryAction[T] extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        world.getCurrentLocation(currentPlayer) match {
            case Some(location) => listItems(location)
            case None => return printAction(s"${currentPlayer.name} is not in a store")
        }
    }

    protected def listItems(location: Location): Option[Action] = {
        val message = location match {
            case store: Store[T] => store.items.mkString("\n")
            case _ => "You are not in a store"
        }
        printAction(message)
    }
}
