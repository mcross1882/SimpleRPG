/**
 * A simple text based RPG
 *
 * @package   simplerpg
 * @copyright 2015
 */
package simplerpg

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import simplerpg.action.InitialParseAction

object Application extends App {

    implicit val system = ActorSystem("simplerpg-system")

    val service = system.actorOf(Props[SlackServiceActor], "slack-service")

    implicit val timeout = Timeout(5.seconds)
    IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}
