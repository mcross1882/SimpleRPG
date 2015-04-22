package simplerpg

import akka.actor.Actor
import spray.routing._
import spray.http._
import spray.http.StatusCodes._
import MediaTypes._
import simplerpg.action.{InitialParseAction, PrintAction}

class SlackServiceActor extends Actor with SlackService {

  def actorRefFactory = context

  def receive = runRoute(serviceRoutes)
}

trait SlackService extends HttpService {

    private val slackToken = System.getenv("SLACK_TOKEN")

    private val world = new World

    val serviceRoutes =
        path("play") {
            get {
                parameters('user_name, 'text, 'token) { (user_name, text, token) =>
                    respondWithMediaType(`text/plain`) {
                        if (null != slackToken && token != slackToken) {
                            complete(BadRequest, "Invalid or missing token.")
                        } else {
                            val message = runCommands(findPlayer(user_name), splitCommands(text))
                            complete(message)
                        }
                    }
                }
            }
        }
    
    protected def findPlayer(username: String): Player = {
        world.findPlayer(username) match {
            case Some(player) => player
            case None => {
                val player = newPlayer(username)
                world.join(player)
                player
            }
        }
    }

    protected def runCommands(player: Player, commands: Array[String]): String = {
        try {
            world.react(player, new InitialParseAction(commands))
        } catch {
            case e: Exception => e.getMessage
        }
    }

    protected def splitCommands(raw: String): Array[String] = {
        raw.split(" ").map(_.trim)
    }

    protected def newPlayer(name: String): Player = {
        val inventory = Inventory(
            Array(Weapon("Short Sword", 20.0d, 10, false)),
            Array(Armor("Chainmail", 50.0d, 25, 0, false)),
            Array(Item("Potion", 15.0d, Map())))

        new Player(name, 100L, 200.0, Map(
            "strength" -> 120,
            "magic"    -> 75,
            "stamina"  -> 110
        ), inventory, "The office")
    }
}