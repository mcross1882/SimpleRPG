package simplerpg

sealed class Item(name: String, price: Double, effects: Map[String,Long]) {
    override def toString(): String = s"$name (${price}c)"
}

case class Weapon(name: String, price: Double, damage: Long, var isEquipped: Boolean) {
    override def toString(): String = s"$name (${price}c)"
}

case class Armor(name: String, price: Double, physicalProtection: Long, magicProtection: Long, var isEquipped: Boolean) {
    override def toString(): String = s"$name (${price}c)"
}

case class Inventory(var weapons: Array[Weapon], var armor: Array[Armor], var items: Array[Item])
