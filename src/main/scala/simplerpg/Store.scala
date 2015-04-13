package simplerpg

case class Store(name: String, inventory: Map[String,Long]) {

    def buy(player: Player, itemName: String): Player = {
        player
    }
    
    def sell(player: Player, itemName: String): Player = {
        player
    }

    def showSellableItems(player: Player) {
    
    }

    override def toString(): String = name
}
