/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Player, World}

final class CompoundPrintAction(initialAction: Action) extends Action {

    private val builder = new StringBuilder

    def run(currentPlayer: Player, world: World): Option[Action] = {
        builder.clear
        runNextAction(currentPlayer, world, initialAction)
        printAction(builder.toString)
    }

    protected def runNextAction(player: Player, world: World, action: Action): Option[Action] = {
        action.run(player, world) match {
            case Some(newAction: PrintAction) => {
                builder.append(newAction.message + "\n")
                newAction.nextAction match {
                    case Some(actionToRun) => runNextAction(player, world, actionToRun)
                    case None => None
                }
            }
            case Some(newAction) => runNextAction(player, world, newAction)
            case None => None
        }
    }
}
