package part2actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import part2actors.ChangingActorBehavior.Mom.{Food, Interaction}

object ChangingActorBehavior extends App {

  object FussyKid {
    case class KidAccept()
    case class KidReject()
    val HAPPY = "happy"
    val SAD = "sad"
  }

  class FussyKid extends Actor {
    import Mom._
    import FussyKid._

    var state = HAPPY
    override def receive: Receive = {
      case Food(VEGETABLE) => state = SAD
      case Food(CHOCOLATE) => state = HAPPY
      case Ask(_) => if(state.equals(HAPPY)) sender() ! HAPPY else sender() ! SAD
    }
  }

  class StateLessFussyKid extends Actor {
    import Mom._
    override def receive: Receive = happyReceive

    def happyReceive: Receive = {
      case Food(VEGETABLE) => context.become(sadReceive)
      case Food(CHOCOLATE) =>
      case Ask(_) => println("my kid is happy")
    }

    def sadReceive: Receive = {
      case Food(VEGETABLE) =>
      case Food(CHOCOLATE) => context.become(happyReceive)
      case Ask(_) => println("my kid is sad but at least healthy")
    }
  }

  object Mom {
    case class Interaction(kidRef: ActorRef)
    case class Food(food: String)
    case class Ask(message: String)

    val VEGETABLE = "veggies"
    val CHOCOLATE = "chocos"

  }

  class Mom extends Actor {
    import Mom._

    override def receive: Receive = {
      case Interaction(kidRef: ActorRef) =>
        {
          kidRef ! Food(VEGETABLE)
          kidRef ! Ask("do you want to play?")
        }
      case "happy" => println("my kid is happy")
      case "sad" => println("my kid is sad but at least he is healthy")
    }
  }



  val actorSystem = ActorSystem("actorBehaviour")
  val mom = actorSystem.actorOf(Props[Mom])
  val kid = actorSystem.actorOf(Props[FussyKid])
  val stateLessFussyKid = actorSystem.actorOf((Props[StateLessFussyKid]))

  mom ! Interaction(stateLessFussyKid)
//  mom ! Interaction(kid)
}
