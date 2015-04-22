/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg.action

import simplerpg.{Player, World}

final class DiceAction extends Action {

    def run(currentPlayer: Player, world: World): Option[Action] = {
        printAction(s"You toss the dice and roll a ${rollDice}")
    }

    protected def rollDice(): Int = Math.floor(Math.random()*6).toInt + 1
}

