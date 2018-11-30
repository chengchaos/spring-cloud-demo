package cn.chengchaos.akka.mydb

import akka.actor.{ActorSystem, Props}

object MyDbMain {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("akkademy")

    system.actorOf(Props[ServerActor], name = "akkademy-db")

  }
}
