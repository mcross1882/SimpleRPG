/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Player, World, Item}

final class InitialParseAction(commands: Array[String]) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        Some(parseCommands)
    }

    protected def parseCommands(): Action = {
        commands match {
            case Array("shop", "list", "item")    => new ShowStoreInventoryAction[Item]
            case Array("shop", "buy", "item", _*) => new BuyAction[Item](commands.drop(3).mkString(" "))
            case Array("equip", category, _*)     => new EquipAction(category, commands.drop(2).mkString(" "))
            case Array("drop", category, _*)      => new DropAction(category, commands.drop(2).mkString(" "))
            case Array("use", _*)         => new UseItemAction(commands.drop(1).mkString(" "))
            case Array("show", _*)        => new ShowInventoryAction(commands.drop(1))
            case Array("inventory", _*)   => new ShowInventoryAction(commands.drop(1))
            case Array("stats", _*)       => new StatsAction(commands.drop(1))
            case Array("goto", _*)        => new GotoAction(commands.drop(1).mkString(" "))
            case Array("places")          => new PlacesAction
            case Array("where")           => new WhereAction
            case Array("leave")           => new LeaveAction
            case Array("quit")            => new LeaveAction
            case Array("exit")            => new LeaveAction
            case _                        => new InvalidAction
        }
    }
}
