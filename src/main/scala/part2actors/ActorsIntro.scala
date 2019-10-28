package part2actors

import akka.actor.{Actor, ActorSystem, Props}


object ActorsIntro extends App {

  //part1 - create actor system
  val actorSystem = ActorSystem("firstActorSystem")
    println(">>>>>" +actorSystem.name)

  //part2 - create actors

  class WordCounterActor extends Actor {
    def receive: PartialFunction[Any, Unit] = {
      case message: String => println(s"[received message] $message")
      case other => println(">>>>.")
    }
  }
  val counter = actorSystem.actorOf(Props[WordCounterActor], "actorCounter")
  counter ! "Hello from Ravi"
}
