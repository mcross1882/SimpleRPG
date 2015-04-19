/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

import scala.io.StdIn.readLine

trait Character {

    def isPlayerControlled(): Boolean

    def askForCommands(): Array[String]
}

case class Player(
    name: String,
    health: Long,
    gold: Double,
    stats: Map[String,Long],
    inventory: Inventory,
    currentLocation: String) extends Character {

    def attackDamage(): Long = inventory.weapons.find(_.isEquipped) match {
        case Some(weapon) => weapon.damage
        case None => 0L
    }

    def dropItem(name: String) {
        inventory.items.find(_.name equals name) match {
            case Some(item) => inventory.items = inventory.items.filter(!_.equals(item))
            case None => throw new Exception(s"$name is not in your inventory")
        }
    }

    def giveItem(item: Item) {
        inventory.items ++= List(item)
    }

    def dropWeapon(name: String) {
        inventory.weapons.find(_.name equals name) match {
            case Some(weapon) => inventory.weapons = inventory.weapons.filter(!_.equals(weapon))
            case None => throw new Exception(s"$name is not in your inventory")
        }
    }

    def giveWeapon(weapon: Weapon) {
        inventory.weapons ++= List(weapon)
    }

    def dropArmor(name: String) {
        inventory.armor.find(_.name equals name) match {
            case Some(armor) => inventory.armor = inventory.armor.filter(!_.equals(armor))
            case None => throw new Exception(s"$name is not in your inventory")
        }
    }

    def giveArmor(armor: Armor) {
        inventory.armor ++= List(armor)
    }

    def equipWeapon(name: String) {
        inventory.weapons.find(_.name equals name) match {
            case Some(weapon) => weapon.isEquipped = !weapon.isEquipped
            case None => throw new Exception(s"Weapon $name does not exist")
        }
    }

    def equipArmor(name: String) {
        inventory.armor.find(_.name equals name) match {
            case Some(armor) => armor.isEquipped = !armor.isEquipped
            case None => throw new Exception(s"Armor $name does not exist")
        }
    }

    override def isPlayerControlled(): Boolean = true

    override def askForCommands(): Array[String] = readLine().split(" ").filter(!_.isEmpty)

    override def equals(other: Any): Boolean = other match {
        case that: Player => that.name.equalsIgnoreCase(this.name)
        case _ => false
    }

    override def hashCode = name.toLowerCase.hashCode
}

case class NPC(name: String, health: Long, damage: Long) extends Character {

    def isPlayerControlled(): Boolean = false

    def askForCommands(): Array[String] = Array.empty[String]
}
