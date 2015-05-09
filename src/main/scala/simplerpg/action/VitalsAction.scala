/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Player, World}

final class VitalsAction(categories: Array[String]) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val builder = new StringBuilder
        val includeAll = categories.isEmpty

        builder.append(s"Player ${currentPlayer.name} vitals\n")
        if (includeAll || categories.contains("health")) {
            builder.append(s"""- Health: ${currentPlayer.vitals.health}\n""")
        }
        if (includeAll || categories.contains("mana")) {
            builder.append(s"""- Mana:   ${currentPlayer.vitals.mana}\n""")
        }
        printAction(builder.toString)
    }
}