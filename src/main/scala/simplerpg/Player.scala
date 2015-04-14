package simplerpg

import scala.io.StdIn.readLine

trait Character {

    def askForCommands(): Array[String]
}

case class Player(name: String, health: Long, stats: Map[String,Long], inventory: Inventory, currentLocation: String) extends Character {

    def takeItem(name: String): Option[Item] = inventory.items.find(_ equals name)

    def giveItem(item: Item) {
        inventory.items ++= List(item)
    }

    def takeWeapon(name: String): Option[Weapon] = inventory.weapons.find(_ equals name)

    def giveWeapon(weapon: Weapon) {
        inventory.weapons ++= List(weapon)
    }

    def takeArmor(name: String): Option[Armor] = inventory.armor.find(_ equals name)

    def giveArmor(armor: Armor) {
        inventory.armor ++= List(armor)
    }

    def equipWeapon(name: String) {
        inventory.weapons.find(_.name equals name) match {
            case Some(weapon) => weapon.isEquipped = true
            case None => throw new Exception(s"Weapon $name does not exist")
        }

    }

    def equipArmor(name: String) {
        inventory.armor.find(_.name equals name) match {
            case Some(armor) => armor.isEquipped = true
            case None => throw new Exception(s"Armor $name does not exist")
        }
    }

    override def askForCommands(): Array[String] = readLine().split(" ").filter(!_.isEmpty)

    override def equals(other: Any): Boolean = other match {
        case that: Player => that.name.equalsIgnoreCase(this.name)
        case _ => false
    }

    override def hashCode = name.toLowerCase.hashCode
}

trait NPC {

    def isHostile(): Boolean

    def nextAction(): Unit
}
