package excercises

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object VotingSystem extends App {


  val actorSystem = ActorSystem("VotingSystem")



  case class Vote(candidate: String)
  class Citizen extends Actor {
    override def receive: Receive = ???

    def receiveVotes(votesMap: Map[String, Int]): Receive = {
      case Vote(candidate) => if(votesMap.get(candidate).isDefined) votesMap.
    }
  }


  case class AggregateVotes(citizens: Seq[ActorRef])
  class VotingAggregator extends Actor {
    override def receive: Receive = ???
  }





  val ravi = actorSystem.actorOf(Props[Citizen])
  val akshay = actorSystem.actorOf(Props[Citizen])
  val gunjan = actorSystem.actorOf(Props[Citizen])
  val kumar = actorSystem.actorOf(Props[Citizen])

  ravi ! Vote("Modi")
  akshay ! Vote("Uddhav")
  gunjan ! Vote("Modi")
  kumar ! Vote("Nitish")


  val votingAggregator = actorSystem.actorOf(Props[VotingAggregator])

  votingAggregator ! AggregateVotes(Seq(ravi, akshay, gunjan, kumar))


}
