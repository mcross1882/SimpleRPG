/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Location, Player, Store, World}

final class BuyAction[T](itemName: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        world.getCurrentLocation(currentPlayer) match {
            case Some(location) => buyItem(currentPlayer, location)
            case None => return printAction(s"${currentPlayer.name} is not in a store")
        }
    }

    protected def buyItem(currentPlayer: Player, location: Location): Option[Action] = {
        location match {
            case store: Store[T] => {
                store.buy(currentPlayer, itemName)
                printAction(s"${currentPlayer.name} just bought $itemName")
            }
            case _ => printAction(s"You are not in a store at the moment")
        }
    }
}
