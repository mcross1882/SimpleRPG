/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Player, World}

final class ExperienceAction(categories: Array[String]) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val builder = new StringBuilder
        val includeAll = categories.isEmpty

        builder.append("Player Experience\n")
        if (includeAll || categories.contains("level")) {
            builder.append(s"""- Level:       ${currentPlayer.experience.level}\n""")
        }
        if (includeAll || categories.contains("current")) {
            builder.append(s"""- Current:     ${currentPlayer.experience.currentExperience}\n""")
        }
        if (includeAll || categories.contains("nextlevel")) {
            builder.append(s"""- Next level:  ${currentPlayer.experience.maxExperience}\n""")
        }
        printAction(builder.toString)
    }
}