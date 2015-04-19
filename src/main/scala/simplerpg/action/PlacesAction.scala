/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.Player
import simplerpg.World

final class PlacesAction extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val availablePlaces = world.getCurrentLocation(currentPlayer) match {
            case Some(location) if !location.places.isEmpty => s"""- ${location.places.mkString("\n- ")}"""
            case _ => "There are no places you can go"
        }
        printAction("Places\n" + availablePlaces)
    }
}
