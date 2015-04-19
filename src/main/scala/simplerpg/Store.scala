/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

trait Store[T] {

    def buy(player: Player, itemName: String): Player

    def sell(player: Player, itemName: String): Player

    def items(): Array[Item]
}

case class ItemStore(n: String, i: Array[Item], p: Array[String]) extends Store[Item] with Location {

    private val _restockCharge = 0.75

    def buy(player: Player, itemName: String): Player = {
        val item = items.find(_.name equals itemName) match {
            case Some(i) => i
            case None => throw new Exception(s"$name does not stock $itemName")
        }

        if (player.gold < item.price) {
            throw new Exception(s"$itemName costs ${item.price}c but you only have ${player.gold}c")
        }

        player.inventory.items ++= Array(item)
        player.copy(gold = player.gold - item.price)
    }

    def sell(player: Player, itemName: String): Player = {
        val playerItems = player.inventory.items
        val item = playerItems.find(_.name equals itemName) match {
            case Some(i) => i
            case None => throw new Exception(s"${player.name} does not own $itemName")
        }

        player.inventory.items = player.inventory.items.filter(!_.name.equals(item.name))
        player.copy(gold = player.gold + (item.price * _restockCharge))
    }

    def items(): Array[Item] = i

    override def name(): String = n

    override def places(): Array[String] = p

    override def toString(): String = name
}
