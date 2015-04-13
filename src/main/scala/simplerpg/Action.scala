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

final class StatsAction(category: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        if (!currentPlayer.stats.contains(category)) {
            return printAction(s"$category is not a valid stat")
        }
        printAction(s"${currentPlayer.name} has ${currentPlayer.stats(category)} $category")
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

final class GotoAction(newLocationName: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        if (!world.canPlayerMoveTo(currentPlayer, newLocationName)) {
            return printAction(s"$newLocationName cannot be reached from here.")
        }
        world.movePlayer(currentPlayer, newLocationName)
        printAction(s"${currentPlayer.name} moved to $newLocationName.")
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
            case Array("stats", category) => new StatsAction(category)
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
