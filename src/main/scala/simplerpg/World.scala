package simplerpg

import scala.collection.mutable.ListBuffer

case class Location(name: String, npcs: Array[NPC], stores: Array[Store], places: Array[String]) {

    override def toString(): String = name
}

final class World {

    private val currentPlayers = new ListBuffer[Player]

    private val locations = Array(
        Location("The office", Array(), Array(), Array("The parking lot")),
        Location("The parking lot", Array(), Array(), Array("The office", "Terra")),
        Location("Terra", Array(), Array(), Array())
    )

    def join(player: Player) {
        if (currentPlayers.contains(player)) {
            println(s"Player ${player.name} is already connected.")
            return
        }
        currentPlayers += player
    }

    def leave(player: Player) {
        if (!currentPlayers.contains(player)) {
            println(s"Player ${player.name} is not in the game.")
            return
        }
        currentPlayers -= player
    }

    def react(player: Player, action: Action) {
        action.run(player, this) match {
            case Some(newAction) => react(player, newAction)
            case None =>
        }
    }

    def findPlayer(playerName: String): Option[Player] = {
        currentPlayers.find(_.name.toLowerCase.equals(playerName.toLowerCase))
    }

    def canPlayerMoveTo(player: Player, locationName: String): Boolean = {
        getCurrentLocation(player) match {
            case Some(location) => location.places.contains(locationName)
            case None => false
        }
    }

    def movePlayer(player: Player, locationName: String) {
        val updatedPlayer = player.copy(currentLocation = locationName)
        currentPlayers.update(currentPlayers.indexOf(player), updatedPlayer)
    }

    def isEmpty(): Boolean = currentPlayers.isEmpty

    def getLocations(): Array[Location] = locations

    def getCurrentLocation(player: Player): Option[Location] = {
        locations.find(_.name.toLowerCase.equals(player.currentLocation.toLowerCase))
    }
}
