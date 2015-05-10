/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

import scala.io.StdIn.readLine
import com.owlike.genson.defaultGenson._

case class Experience(var level: Int, var currentExperience: Int, var maxExperience: Int) {
    def +=(that: Experience): Experience = {
        currentExperience += that.currentExperience
        if (currentExperience >= maxExperience) {
            level += 1
            currentExperience -= maxExperience
            maxExperience += (maxExperience / 10)
        }
        this
    }

    override def toString(): String = toJson(this)
}

case class Stats(var strength: Int, var magic: Int, var stamina: Int) {
    override def toString(): String = toJson(this)
}

case class Vitals(var health: Int, var mana: Int) {
    def +=(that: Vitals): Vitals = {
        health += that.health
        mana += that.mana
        this
    }

    override def toString(): String = toJson(this)
}

case class Player(
    name: String,
    experience: Experience,
    vitals: Vitals,
    maxVitals: Vitals,
    stats: Stats,
    inventory: Inventory,
    currentLocation: String) extends Character {

    private val strengthMultiplier = 100

    def attack(enemy: Player): Int = {
        val damage = inventory.weapons.find(_.isEquipped) match {
            case Some(weapon) => weapon.damage * (stats.strength / strengthMultiplier)
            case None => (stats.strength / strengthMultiplier)
        }

        enemy.vitals.health = enemy.vitals.health - damage
        damage
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

    def capMaxVitals() {
        if (vitals.health > maxVitals.health)
            vitals.health = maxVitals.health

        if (vitals.mana > maxVitals.mana)
            vitals.mana = maxVitals.mana
    }

    def resetVitals() {
        vitals.health = maxVitals.health
        vitals.mana = maxVitals.mana
    }

    def isDead(): Boolean = vitals.health <= 0

    def hasMana(): Boolean = vitals.mana > 0

    override def isPlayerControlled(): Boolean = true

    override def askForCommands(): Array[String] = readLine().split(" ").filter(!_.isEmpty)

    override def equals(other: Any): Boolean = other match {
        case that: Player => that.name.equalsIgnoreCase(this.name)
        case _ => false
    }

    override def hashCode = name.toLowerCase.hashCode

    override def toString(): String = toJson(this)
}

