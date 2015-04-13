package simplerpg

object Application {

    def main(args: Array[String]) {
        
        val world = new World
        
        world.join(new Player("john", 100L, Map(
            "strength" -> 120,
            "magic"    -> 75,
            "stamina"  -> 110
        ), "The office"))

        var player: Option[Player] = None
        while (!world.isEmpty) {
            world.findPlayer("john") match {
                case Some(player) => world.react(player, new InitialParseAction(player.askForCommands))
                case None => s"Could not find player john"
            }
        }

        println("No more players are in world. Exiting game...")
    }
}
