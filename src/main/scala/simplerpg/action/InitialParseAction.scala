/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Player, World, Item, Weapon, Armor}

final class InitialParseAction(commands: Array[String]) extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = Some(parseCommands)

    protected def parseCommands(): Action = {
        val initialAction = commands match {
            case Array("attack", enemyName)       => new AttackAction(enemyName)
            case Array("list", category)          => new ShowStoreInventoryAction(category)
            case Array("buy", category, _*)       => new BuyAction(category, implode(commands, 2))
            case Array("sell", category, _*)      => new SellAction(category, implode(commands, 2))
            case Array("equip", category, _*)     => new EquipAction(category, implode(commands, 2))
            case Array("drop", category, _*)      => new DropAction(category, implode(commands, 2))
            case Array("use", _*)                 => new UseItemAction(implode(commands))
            case Array("show", _*)                => new ShowInventoryAction(commands.drop(1))
            case Array("inventory", _*)           => new ShowInventoryAction(commands.drop(1))
            case Array("stats", _*)               => new StatsAction(commands.drop(1))
            case Array("vitals", _*)              => new VitalsAction(commands.drop(1))
            case Array("exp", _*)                 => new ExperienceAction(commands.drop(1))
            case Array("goto", _*)                => new GotoAction(implode(commands))
            case Array("places")                  => new PlacesAction
            case Array("where")                   => new WhereAction
            case Array("roll")                    => new DiceAction
            case Array("leave")                   => new LeaveAction
            case Array("quit")                    => new LeaveAction
            case Array("exit")                    => new LeaveAction
            case _                                => new InvalidAction
        }
        new CompoundPrintAction(initialAction)
    }

    protected def implode(items: Array[String], offset: Int = 1): String = items.drop(offset).mkString(" ")
}
