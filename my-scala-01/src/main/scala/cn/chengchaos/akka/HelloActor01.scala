package cn.chengchaos.akka

import akka.actor.{ActorRef, ActorSystem, Props}

class HelloActor01 extends akka.actor.Actor {

  override def receive: Receive = {

    case "hello" => println("hello")
    case _ => println("huh?")

  }
}


/**
  * ActorSystem 是一个 Actor 的层级组，
  * 这些 Actor 共享同样的配置（例如分发器、部署、远端能力和地址）。
  * 也是创建和查找 Actor 的入口。
  *
  * ActorSystem 为应用申请一个或多个线程的结构，因此可以
  * 在每个应用（逻辑上）创建一个 ActorSystem 。
  *
  */
object HelloActor01 {

  def main(args:Array[String]) :Unit = {

    val system = ActorSystem("HelloActor001System")

    val helloActorRef:ActorRef = system.actorOf(Props[HelloActor01])

    helloActorRef ! "hello"

    helloActorRef ! "kao"

    println("===================")

    val helloActorRef2 = system.actorOf(Props(new HelloActor02("Fred")), name = "helloActor" )

    helloActorRef2 ! "hello"
    helloActorRef2 ! "kao"

    system.terminate

  }
}
