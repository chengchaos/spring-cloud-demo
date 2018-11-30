package cn.chengchaos.akka

import org.slf4j.LoggerFactory

import akka.actor.{ActorRef, ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.{Await, Awaitable, Future}
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Random, Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global

class HelloActor01 extends akka.actor.Actor {

  override def receive: Receive = {

    case "hello" => println("hello")
    case i:Int => {
      //println(i + 1)
      val wait = Random.nextInt(3000)
      java.util.concurrent.TimeUnit.MILLISECONDS.sleep(wait)
      sender ! (i +s" + $wait = " + (wait + 1))
    }
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

  private val LOGGER = LoggerFactory.getLogger(classOf[HelloActor01])

  def main(args:Array[String]) :Unit = {
    test02()
  }

  def my1(i:Int, system:ActorSystem): Unit = {

    val myActorRef = system.actorOf(Props[HelloActor01], "HelloActor"+ i)


    println(s">>> 发送消息 ${i} >>>")
    implicit val timeout = Timeout(4 seconds)
    val future:Future[Any] = myActorRef ask i
    //      val result = Await.result(future, Duration(10, "sec"))
    //        .asInstanceOf[Int]
    //      println("<<< "+ result)

    future.onComplete {
      case Success(value) => {
        println(s"<<< Success :: $value")
      }
      case Failure(exception) => {
        LOGGER.error("", exception)
      }
    }
//    future.onSuccess {
//      case strRes => {
//        println(s"<<< Success: $strRes")
//        myActorRef ! PoisonPill
//      }
//    }


//    futureList = Vector(future) ++ futureList
//      val wait = Random.nextInt(3)
    java.util.concurrent.TimeUnit.MILLISECONDS.sleep(10)
  }


  def my2(i:Int, myActorRef: ActorRef): Unit = {

    println(s">>> 发送消息 ${i} >>>")
    implicit val timeout = Timeout(3 seconds)
    val future:Future[Any] = myActorRef ask  (i)


    LOGGER.info("future === > {}", future)


    future.onComplete {
      case Success(value) => {
        println(s"<<< Success :: $value")
      }
      case Failure(exception) => {
        LOGGER.error("", exception)
      }
    }
    java.util.concurrent.TimeUnit.MILLISECONDS.sleep(10)
  }

  def test02(): Unit = {
    val system:ActorSystem = ActorSystem("HelloActor_test02")

    println(">>> 开始发送消息 >>>")
    var futureList = Vector[Future[Any]]()
    val myActorRef = system.actorOf(Props[HelloActor01], "HelloActor01")
    for (i <- 1 to 65)  {
      my1(i, system)
      //      my2(i, myActorRef)
    }

    println(">>> 消息发送完毕 >>>")
    java.util.concurrent.TimeUnit.MINUTES.sleep(10L)
    println(">>> 等灯等灯 >>>")
    system.terminate()
    println(">>> System.terminate >>>")
  }

  def test01(): Unit = {
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
