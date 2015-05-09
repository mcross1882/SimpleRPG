/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Player, World}

final class ShowInventoryAction(categories: Array[String]) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        val builder = new StringBuilder
        val includeAll = categories.isEmpty

        builder.append("Player Inventory\n")
            .append(s"Gold: ${currentPlayer.inventory.gold}\n")

        if (includeAll || categories.contains("weapons")) {
            appendList("Weapons", currentPlayer.inventory.weapons, builder)
        }
        if (includeAll || categories.contains("armor")) {
            appendList("Armor", currentPlayer.inventory.armor, builder)
        }
        if (includeAll || categories.contains("items")) {
            appendList("Items", currentPlayer.inventory.items, builder)
        }
        printAction(builder.toString)
    }

    protected def appendList[T](title: String, contents: Array[T], builder: StringBuilder) {
        builder.append(title + "\n")
            .append(("*" * title.length) + "\n")
            .append(s"- ${contents.mkString("\n- ")}\n")
    }
}
