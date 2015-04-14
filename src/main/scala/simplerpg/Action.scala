/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

/**
 * Actions provide a linkable way to react to the input commands.
 * All input is represented as an array of strings. The first element
 * is considered the initial command which may consist of sub commands
 * or arguments. Refer to the subclass documentation for more
 * information.
 *
 * @author Matthew Cross <matthew@koddi.com>
 */
sealed trait Action {
    def run(currentPlayer: Player, world: World): Option[Action]

    protected def printAction(message: String): Option[Action] = {
        Some(new PrintAction(message))
    }
}

final class WhereAction extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val locationMessage = world.getCurrentLocation(currentPlayer) match {
            case Some(location) => location.name
            case None => s"Could not find ${currentPlayer.name}"
        }
        printAction(locationMessage)
    }
}

final class LeaveAction extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        world.leave(currentPlayer)
        printAction(s"Player has left.")
    }
}

final class StatsAction(categories: Array[String]) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val builder = new StringBuilder
        val includeAll = categories.isEmpty

        builder.append("Player Stats\n")
        if (includeAll || categories.contains("strength")) {
            builder.append(s"""- Strength: ${currentPlayer.stats("strength")}\n""")
        }
        if (includeAll || categories.contains("magic")) {
            builder.append(s"""- Magic:    ${currentPlayer.stats("magic")}\n""")
        }
        if (includeAll || categories.contains("stamina")) {
            builder.append(s"""- Stamina:  ${currentPlayer.stats("stamina")}\n""")
        }
        printAction(builder.toString)
    }
}

final class PlacesAction extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val availablePlaces = world.getCurrentLocation(currentPlayer) match {
            case Some(location) => s"""- ${location.places.mkString("\n- ")}"""
            case None => "There are no places you can go."
        }
        printAction("Places\n" + availablePlaces)
    }
}

final class StoresAction extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val availableStores = world.getCurrentLocation(currentPlayer) match {
            case Some(location) => s"""- ${location.stores.mkString("\n- ")}"""
            case None => "There are no stores in this area."
        }
        printAction("Stores\n" + availableStores)
    }
}

final class GotoAction(newLocationName: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        if (!world.canPlayerMoveTo(currentPlayer, newLocationName)) {
            return printAction(s"$newLocationName cannot be reached from here.")
        }
        world.movePlayer(currentPlayer, newLocationName)
        printAction(s"Moved to $newLocationName.")
    }
}

final class ShowInventoryAction(categories: Array[String]) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val builder = new StringBuilder
        val includeAll = categories.isEmpty

        builder.append("Player Inventory\n")
        if (includeAll || categories.contains("weapons")) {
            appendList("Weapons", currentPlayer.inventory.weapons, builder)
        }
        if (includeAll || categories.contains("armor")) {
            appendList("Armor", currentPlayer.inventory.armor, builder)
        }
        if (includeAll || categories.contains("items")) {
            appendList("Items", currentPlayer.inventory.items, builder)
        }
        printAction(builder.toString)
    }

    protected def appendList[T](title: String, contents: Array[T], builder: StringBuilder) {
        builder.append(title + "\n")
            .append(("*" * title.length) + "\n")
            .append(s"- ${contents.mkString("\n- ")}\n")
    }
}

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

final class InvalidAction extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        printAction("Invalid action given.")
    }
}

final class PrintAction(message: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        println(s"[${currentPlayer.name}] $message")
        None
    }
}

final class InitialParseAction(commands: Array[String]) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        Some(parseCommands)
    }

    protected def parseCommands(): Action = {
        commands match {
            case Array("equip", category, _*) => new EquipAction(category, commands.drop(2).mkString(" "))
            case Array("drop", category, _*)  => new DropAction(category, commands.drop(2).mkString(" "))
            case Array("show", _*)        => new ShowInventoryAction(commands.drop(1))
            case Array("inventory", _*)   => new ShowInventoryAction(commands.drop(1))
            case Array("stats", _*)       => new StatsAction(commands.drop(1))
            case Array("goto", _*)        => new GotoAction(commands.drop(1).mkString(" "))
            case Array("places")          => new PlacesAction
            case Array("where")           => new WhereAction
            case Array("leave")           => new LeaveAction
            case Array("quit")            => new LeaveAction
            case Array("exit")            => new LeaveAction
            case _                        => new InvalidAction
        }
    }
}
