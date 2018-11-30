package cn.chengchaos.akka.mydb

import akka.actor.ActorSystem
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success}

class ClientActor extends {

  private implicit val timeout = Timeout(200 seconds)
  private implicit val system = ActorSystem("LocalSystem")

  private val remoteDb = system.actorSelection(
    s"akka.tcp://akkademy@127.0.0.1:2552/user/akkademy-db"
  )

  def set(key:String, value: Object) = {
    remoteDb ? SetRequest(key, value)
  }

  def get(key:String) : Future[Any] = {
    remoteDb ? GetRequest(key)
  }

  java.util.concurrent.TimeUnit.SECONDS.sleep(20L)
  system.terminate()


}

object ClientActor {

  def main(args: Array[String]): Unit = {
    val key = "hello"
    val value1 = "chengchao"

    val ca = new ClientActor

    println(s"key: $key,  value1: $value1")
    ca.set(key,value1)

    val future = ca.get(key)

    import scala.concurrent.ExecutionContext.Implicits.global
    future.onComplete{
      case Success(value) => println(s" value == $value")
      case Failure(exception) => exception.printStackTrace()
    }



    val value3 = Await.result(future, 10 seconds)
      .asInstanceOf[String]

    println("valuee == "+ value3)

  }
}