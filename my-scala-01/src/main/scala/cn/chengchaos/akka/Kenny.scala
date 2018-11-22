package cn.chengchaos.akka

import akka.actor.{Actor, ActorSystem, Props}

class Kenny extends Actor {

  println("进入构造方法")

  /**
    * actor 启动时立即调用，重启时被 postRestart 调用。
    */
  override def preStart = println("preStart")

  /**
    * actor 停止后调用，可以用来执行清理工作。
    * 根据 Akka 文档，这个钩子“在 actor 的消息队列被禁用后一定会运行”
    *
    */
  override def postStop = println("postStop")

  /**
    * 根据 Akka 文档，actor 重启时，进程通知旧的 actor。
    * 引起重启的异常，调用 preRestart 方法，而消息触发异常。
    * 如果重启不是因为处理消息引起，消息也许为 None
    * @param reason
    * @param message
    */
  override def preRestart(reason: Throwable, message: Option[Any]) = {
    println("preRestart")
    println(s" Message: ${message.getOrElse("")}")
    println(s" Reason: ${reason.getMessage}")
    super.preRestart(reason, message)
  }

  /**
    * 引起重启的异常触发了新 actor 的 postRestart 方法。
    * 缺省会调用 preStart 方法。
    * @param reason
    */
  override def postRestart(reason: Throwable) = {
    println("postRestart")
    println(s" REASON: ${reason.getMessage}")
    super.postRestart(reason)
  }


  override def receive: Receive = {
    case ForceRestart => throw new Exception("Boom!")
    case _ => println("Kenny received a message")
  }
}


case object ForceRestart

object LifecycleDemo extends  App {

  val system = ActorSystem("LifecycleDemo")

  val kenny = system.actorOf(Props[Kenny], name = "Kenny")
  println("sending kenny a simple String message")
  kenny ! "hello"
  Thread.sleep( 1000)
  println("make kenny restart")
  kenny ! ForceRestart
  Thread.sleep(1000)
  println("stopping kenny")
  system.stop(kenny)
  println("shutting down system")
  system.terminate()
}