package simplerpg

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
        val inventory = Inventory(Array.empty[Weapon], Array.empty[Armor], Array.empty[Item])
        new Player("john", 100L, Map(
            "strength" -> 120,
            "magic"    -> 75,
            "stamina"  -> 110
        ), inventory, "The office")
    }
}
