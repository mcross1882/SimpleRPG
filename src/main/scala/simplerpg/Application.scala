/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

import simplerpg.action.InitialParseAction

object Application {

    def main(args: Array[String]) {

        val world = new World

        world.join(createPlayer)

        var player: Option[Player] = None
        while (!world.isEmpty) {
            world.findPlayer("john") match {
                case Some(player) => world.react(player, new InitialParseAction(player.askForCommands))
                case None => s"Could not find player john"
            }
        }

        println("No more players are in world. Exiting game...")
    }

    protected def createPlayer(): Player = {
        val inventory = Inventory(
            Array(Weapon("Short Sword", 20.0d, 10, false)),
            Array(Armor("Chainmail", 50.0d, 25, 0, false)),
            Array(Item("Potion", 15.0d, Map())))

        new Player("john", 100L, 200.0, Map(
            "strength" -> 120,
            "magic"    -> 75,
            "stamina"  -> 110
        ), inventory, "The office")
    }
}
