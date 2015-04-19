/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.Player
import simplerpg.World

final class StatsAction(categories: Array[String]) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val builder = new StringBuilder
        val includeAll = categories.isEmpty

        builder.append("Player Stats\n")
        if (includeAll || categories.contains("strength")) {
            builder.append(s"""- Strength: ${currentPlayer.stats("strength")}\n""")
        }
        if (includeAll || categories.contains("magic")) {
            builder.append(s"""- Magic:    ${currentPlayer.stats("magic")}\n""")
        }
        if (includeAll || categories.contains("stamina")) {
            builder.append(s"""- Stamina:  ${currentPlayer.stats("stamina")}\n""")
        }
        printAction(builder.toString)
    }
}