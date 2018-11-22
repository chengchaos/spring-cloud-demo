package cn.chengchaos.akka

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object Futuresl extends App {

  // used by 'time' method
  implicit val baseTime = System.currentTimeMillis

  val f = Future {
    Thread.sleep(500)
    1 + 1
  }

  f onSuccess {
    case result => println(s"Success: $result")
  }

  f.onComplete {
    case Success(value) => println(s"Got the callbck, $value")
    case Failure(e) => e.printStackTrace
  }

  println("."); Thread.sleep(100)
  // Await.result
  // 此方法说明他最多等待 1 秒的 Future 的返回。
  // 如果没有在规定时间内返回，则抛出 java.util.concurrent.TimeoutException 异常
  val result = Await.result(f, 1 second)

  println(result)

  Thread.sleep(3000)
}
