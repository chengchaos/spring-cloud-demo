package cn.chengchaos.akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case object PingMessage
case object PongMessage
case object StartMessage
case object StopMessage

class Ping(pong: ActorRef) extends Actor {
  var count = 0

  def incrementAndPrint: Unit = {
    count += 1
    println("ping")
  }

//  override def receive: Receive =
  override def receive: Receive = {
    case  StartMessage => {
      incrementAndPrint
      pong ! PingMessage
    }
    case PongMessage => {
      incrementAndPrint
      if (count > 99) {
        // 当一个 actor 收到一条来自另一个 actor 的消息
        // 她也会收到一个叫做隐式的 sender 的引用，
        // 二期可以用这个引用给源 actor 回复消息。
        sender ! StopMessage
        println("ping stopped")
        // context 对象隐式的对所有 actor 可用
        // 并且可以用来停止 actor
        context.stop(self)
      } else {
        sender ! PingMessage
      }
    }
    case _ => println("Ping got something unexpected.")
  }
}

class Pong extends Actor {

  override def receive: Receive = {
    case PingMessage => {
      println(" pong")
      sender ! PongMessage
    }
    case StopMessage => {
      println("pong stopped")
      context.stop(self)
    }
    case _ => println("Pong got something unexpected.")
  }
}


object PingPongTest {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("PingPongSystem")
    val pong = system.actorOf(Props[Pong], name = "pong")
    val ping = system.actorOf(Props(new Ping(pong)), name = "ping")

    // start the action
    ping ! StartMessage
    // commented-out so you can see all the output
    java.util.concurrent.TimeUnit.SECONDS.sleep(10L)


    ping ! StartMessage
    java.util.concurrent.TimeUnit.SECONDS.sleep(10L)
    system.terminate()
  }
}