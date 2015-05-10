/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

import com.owlike.genson.defaultGenson._

sealed abstract class SellableObject

case class Item(name: String, price: Double, effects: Vitals) extends SellableObject {
    override def toString(): String = f"$name%32s ($price%08.2f) ${effects.toString}"
}

case class Weapon(name: String, price: Double, damage: Int, var isEquipped: Boolean) extends SellableObject {
    override def toString(): String = {
        val message = f"$name%32s ($price%08.2f) ${damage}pt   "
        message + (if (isEquipped) "Equipped" else "")
    }
}

case class Armor(name: String, price: Double, physicalProtection: Int, magicProtection: Int, var isEquipped: Boolean) extends SellableObject {
    override def toString(): String = {
        val message = f"$name%32s ($price%08.2f) Phys: ${physicalProtection}pt Mag: ${magicProtection}pt "
        message + (if (isEquipped) "Equipped" else "")
    }
}

case class Inventory(var gold: Double, var weapons: Array[Weapon], var armor: Array[Armor], var items: Array[Item]) {
    override def toString(): String = toJson(this)
}
