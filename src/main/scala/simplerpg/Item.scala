package simplerpg

sealed class Item(name: String, price: Double, effects: Map[String,Long])

case class Weapon(name: String, price: Double, damage: Long, minLevel: Long, var isEquipped: Boolean)

case class Armor(name: String, price: Double, physicalProtection: Long, magicProtection: Long, var isEquipped: Boolean)

case class Inventory(var weapons: Array[Weapon], var armor: Array[Armor], var items: Array[Item])
