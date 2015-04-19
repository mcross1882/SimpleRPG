/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

import scala.collection.mutable.ListBuffer
import simplerpg.action.Action

trait Location {

    def name(): String

    def places(): Array[String]
}

case class SimpleLocation(n: String, p: Array[String]) extends Location {

    def name(): String = n

    def places(): Array[String] = p
}
