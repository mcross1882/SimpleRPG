/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import java.io.{PrintWriter, FileWriter}
import simplerpg.{Player, World}

final class AttackAction(enemyName: String) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        world.findPlayer(enemyName) match {
            case Some(otherPlayer) => dealDamage(currentPlayer, otherPlayer)
            case None => printAction(s"Player ${enemyName} does not exist")
        }
    }

    protected def dealDamage(player: Player, enemy: Player): Option[Action] = {
        if (enemy.isDead) {
            return printAction(s"${enemyName} is already dead")
        }

        val damage = player attack enemy
        if (enemy.isDead) {
            enemy.resetVitals
            player.experience += enemy.experience
            saveAndPrint(s"You killed ${enemyName}")
        } else {
            saveAndPrint(s"You dealt ${damage} to ${enemyName}")
        }
    }
}