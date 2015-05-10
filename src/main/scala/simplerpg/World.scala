/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

import com.owlike.genson.defaultGenson._
import java.io.FileInputStream
import scala.collection.mutable.ListBuffer
import simplerpg.action.{Action, PrintAction}

final class World {

    private val currentPlayers = new ListBuffer[Player]

    private val stores = fromJson[Array[ItemStore]](new FileInputStream("data/itemstores.json"))

    private val locations = fromJson[Array[SimpleLocation]](new FileInputStream("data/locations.json"))

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

    def update(player: Player) {
        val index = currentPlayers.indexOf(player)
        if (-1 != index) {
            currentPlayers(index) = player
            println(s"Player ${player.name} has been updated.")
        }
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
