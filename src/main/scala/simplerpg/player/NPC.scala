/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

case class NPC(name: String, health: Long, damage: Long) extends Character {

    def isPlayerControlled(): Boolean = false

    def askForCommands(): Array[String] = Array.empty[String]
}
