package excercises

import akka.actor.{Actor, ActorSystem, Props}

object Counter extends App {

  val actorSystem = ActorSystem("excercises")

  object Counter {
    case class Increment(count: Int)
    case class Decrement(count: Int)
    case class PrintCounter(count: Int)
  }



  class Counter extends Actor {
    import Counter._
    override def receive: Receive = counterReceive(0)

    def counterReceive(count: Int): Receive = {
      case Increment => context.become(counterReceive(count + 1))
      case Decrement => context.become(counterReceive(count - 1))
      case PrintCounter => println("counter: "+ count)
    }
  }

  val counter = actorSystem.actorOf(Props[Counter])
  import Counter._
  counter ! Increment
  counter ! Decrement
  counter ! Increment
  counter ! Increment
  counter ! Increment
  counter ! PrintCounter
}