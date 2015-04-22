/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

import scala.collection.mutable.ListBuffer
import simplerpg.action.{Action, PrintAction}

final class World {

    private val currentPlayers = new ListBuffer[Player]

    private val stores = Array(
        ItemStore("Terra", Array(
            Item("1lb. to-go", 8.5, Map("health" -> 15)),
            Item("Sit down meal", 16.0, Map("health" -> 20))
        ), Array("The parking lot"))
    )

    private val locations = Array(
        SimpleLocation("The office", Array("The parking lot")),
        SimpleLocation("The parking lot", Array("The office", "Terra"))
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

    def react(player: Player, action: Action): String = {
        action.run(player, this) match {
            case Some(newAction) => return react(player, newAction)
            case None =>
        }
        
        action match {
            case printer: PrintAction => printer.message
            case _ => "Task complete"
        }
    }

    def findPlayer(playerName: String): Option[Player] = {
        currentPlayers.find(_.name.toLowerCase.equals(playerName.toLowerCase))
    }

    def canPlayerMoveTo(player: Player, locationName: String): Boolean = {
        getCurrentLocation(player) match {
            case Some(location) => location.places.contains(locationName)
            case None => stores.exists(_.name equals locationName)
        }
    }

    def movePlayer(player: Player, locationName: String) {
        val updatedPlayer = player.copy(currentLocation = locationName)
        currentPlayers.update(currentPlayers.indexOf(player), updatedPlayer)
    }

    def isEmpty(): Boolean = currentPlayers.isEmpty

    def getCurrentLocation(player: Player): Option[Location] = {
        val playerLocation = player.currentLocation.toLowerCase
        locations.find(_.name.toLowerCase.equals(playerLocation)) match {
            case Some(location) => Some(location)
            case None => getCurrentStore(player)
        }
    }

    def getCurrentStore(player: Player): Option[Location] = {
        val playerLocation = player.currentLocation.toLowerCase
        stores.find(_.name.toLowerCase.equals(playerLocation))
    }
}
