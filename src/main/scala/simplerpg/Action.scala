package simplerpg

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
        printAction(s"Player ${currentPlayer.name} has left.")
    }
}

final class StatsAction(categories: Array[String]) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val builder = new StringBuilder
        val includeAll = categories.isEmpty

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
        printAction(availablePlaces)
    }
}

final class StoresAction extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val availableStores = world.getCurrentLocation(currentPlayer) match {
            case Some(location) => s"""- ${location.stores.mkString("\n- ")}"""
            case None => "There are no stores in this area."
        }
        printAction(availableStores)
    }
}

final class GotoAction(newLocationName: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        if (!world.canPlayerMoveTo(currentPlayer, newLocationName)) {
            return printAction(s"$newLocationName cannot be reached from here.")
        }
        world.movePlayer(currentPlayer, newLocationName)
        printAction(s"${currentPlayer.name} moved to $newLocationName.")
    }
}

final class ShowInventoryAction(categories: Array[String]) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val builder = new StringBuilder
        val includeAll = categories.isEmpty

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

final class EquipWeaponAction(weaponName: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        try {
            currentPlayer.equipWeapon(weaponName)
        } catch {
            case e: Exception => return printAction(e.getMessage)
        }
        printAction(s"${currentPlayer.name} equipped $weaponName")
    }
}

final class InvalidAction extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        printAction("Invalid action given.")
    }
}

final class PrintAction(message: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        println(message)
        None
    }
}

final class InitialParseAction(commands: Array[String]) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        Some(parseCommands)
    }

    protected def parseCommands(): Action = {
        commands match {
            case Array("equip", "weapon", weaponName) => new EquipWeaponAction(weaponName)
            case Array("show", _*)        => new ShowInventoryAction(commands.drop(1))
            case Array("inventory", _*)   => new ShowInventoryAction(commands.drop(1))
            case Array("stats", _*) => new StatsAction(commands.drop(1))
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
