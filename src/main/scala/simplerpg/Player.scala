package simplerpg

import scala.io.StdIn.readLine

trait Character {
    
    def askForCommands(): Array[String]
}

case class Player(name: String, health: Long, stats: Map[String,Long], currentLocation: String) extends Character {

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
