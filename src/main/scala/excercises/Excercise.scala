package excercises

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import excercises.Excercise.BankAccount.{Deposit, Failure, Success, Withdraw}
import excercises.Excercise.Person.Enjoy

object Excercise extends App {

  val actorSystem = ActorSystem("exerciseActor")

  class CounterActor extends Actor {
    var counter = 0
    override def receive: Receive = {
      case "increment" => {
        counter += 1
        println("received increment and the count is "+ counter)
      }
      case "decrement" => {
        counter -= 1
        println("received decrement and the count is "+ counter)
      }
    }
  }

  val counterActor = actorSystem.actorOf(Props[CounterActor], "counterActor")

  counterActor ! "increment"
  counterActor ! "increment"
  counterActor ! "increment"
  counterActor ! "decrement"

  object BankAccount {
    case class Withdraw(amount: Int)
    case class Deposit(amount: Int)
    case class Success(msg: String = "success")
    case class Failure(msg: String = "failed")
  }


  class BankAccount extends Actor {
    import BankAccount._
    var balance = 5000

    override def receive: Receive = {

      case Withdraw(amount) => {
        if(balance < 0) {
          sender() ! Failure("not sufficient amount")
        } else if(amount > balance){
          sender() ! Failure("withdrawal amount is greater than the balance")
        } else {
          balance -= amount
          sender() ! Success("withdrawn successfully")
        }
      }

      case Deposit(amount) => {
        balance += amount
        sender() ! Success("Deposited successfully")
      }

      case "statement" => println(s"statement $balance")

      case Failure(msg) => println(msg)
      case Success(msg) => println(msg)
    }
  }

  object Person {
    case class Enjoy(account: ActorRef)
  }

  class Person extends Actor {
    override def receive: Receive = {
      case Enjoy(account) =>
        account ! Deposit(5000)
        account ! Withdraw(1000)
        account ! "statement"
      case Success(msg) => println(msg)
      case Failure(msg) => println(msg)
    }
  }

  val icici = actorSystem.actorOf(Props[BankAccount], "ICICI")
  val ravi = actorSystem.actorOf(Props[Person], "Ravi")

  ravi ! Enjoy(icici)

}
