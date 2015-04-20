/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Location, Player, Store, World, ItemStore, WeaponStore, ArmorStore}

final class ShowStoreInventoryAction(category: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        world.getCurrentLocation(currentPlayer) match {
            case Some(location) => listItems(location)
            case None => return printAction("You are not in a store")
        }
    }

    protected def listItems(location: Location): Option[Action] = {
        val builder = new StringBuilder
        
        builder.append(location.name + "\n")
            .append(("*" * location.name.length) + "\n")

        val message = (category, location) match {
            case ("items", store: ItemStore) => store.items.mkString("\n")
            case ("weapons", store: WeaponStore) => store.items.mkString("\n")
            case ("armor", store: ArmorStore) => store.items.mkString("\n")
            case _ => "You are not in a store"
        }

        printAction(builder.append(message).toString)
    }
}
