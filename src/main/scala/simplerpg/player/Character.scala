/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

trait Character {

    def isPlayerControlled(): Boolean

    def askForCommands(): Array[String]
}