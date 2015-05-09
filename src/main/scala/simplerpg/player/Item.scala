/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

import com.owlike.genson.defaultGenson._

sealed abstract class SellableObject {
    override def toString(): String = toJson(this)
}

case class Item(name: String, price: Double, effects: Vitals) extends SellableObject

case class Weapon(name: String, price: Double, damage: Int, var isEquipped: Boolean) extends SellableObject

case class Armor(name: String, price: Double, physicalProtection: Int, magicProtection: Int, var isEquipped: Boolean) extends SellableObject

case class Inventory(var gold: Double, var weapons: Array[Weapon], var armor: Array[Armor], var items: Array[Item]) {
    override def toString(): String = toJson(this)
}
