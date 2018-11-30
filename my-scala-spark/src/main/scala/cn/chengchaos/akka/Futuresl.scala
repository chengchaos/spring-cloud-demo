package cn.chengchaos.akka

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Random, Success}


object Futuresl extends App {





  def m1() : Unit = {
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

  def m2() :Unit = {
    println("starting calculation ..." )

    val f = Future {
      Thread.sleep(Random.nextInt(500))
      42
    }
    println("before onComplete")

    // 设定好回调，一旦 Future 完成，就会调用 onComplete 方法
    f.onComplete {
      case Success(value) => println(s"Got ${value}")
      case Failure(exception) => exception.printStackTrace()
    }

    println("a.."); Thread.sleep(100)
    println("b.."); Thread.sleep(100)
    println("c.."); Thread.sleep(100)
    println("d.."); Thread.sleep(100)
    println("e.."); Thread.sleep(100)
    println("f.."); Thread.sleep(100)
    println("g.."); Thread.sleep(100)
    println("h.."); Thread.sleep(100)
  }


  def m3() : Unit = {
    val f = Future {
      Thread.sleep(Random.nextInt(500));
      if (Random.nextInt(500) > 250) {
        throw new RuntimeException("kao")

      }
      else {
        32
      }
    }

    f.onSuccess {
      case result => println(s"Success: ${result}")
    }

    f.onFailure {
      case t => println(s"Exception : ${t.getMessage}")
    }

    println("a.."); Thread.sleep(100)
    println("b.."); Thread.sleep(100)
    println("c.."); Thread.sleep(100)
    println("d.."); Thread.sleep(100)
    println("e.."); Thread.sleep(100)
    println("f.."); Thread.sleep(100)
    println("g.."); Thread.sleep(100)
    println("h.."); Thread.sleep(100)
  }

  def m4() : Unit = {
    def longRunningComputation(i: Int):Future[Int] = Future[Int] {
      Thread.sleep(100)
      i + 1
    }



    longRunningComputation(11)
      .onComplete {
      case Success(value) => println(s" get $value")
      case Failure(exception) => exception.printStackTrace()
    }


    Thread.sleep(1000)

  }

  /*
    * 一个 Future[T] 就是一个并发计算的容器。
    * 在未来的某个时间可能会返回一个 T 类型的结果
    * 或者一个异常。
    *
    * 算法的计算在 Future 被创建后的某个不确定的时间
    * 开始，通过执行上下文运行在与她绑定的线程上。
    *
    * 当 Future 完成时，计算结果才可用。
    *
    * 当返回一个结果时，Future 就完成了使命。
    * 可能要么成功，要么失败。
    *
    * Future 提供一个读取计算结果值的接口。
    * 包括了回调方法还有其他方法：
    * 例如 for 表达式、 map、 flatMap 等。
    *
    * 一个 ExecutionContext 执行它得到的任务。
    * 可以把它看成一个线程池。
    *
    * ExecutionContext.Implicits.global
    * 的语句引入了默认的全局执行上下文对象。
    *
    *
    * onComplete 接收一个 Try[T] => U 的回调类型
    *
    * onSuccess 和 onFailure 接收偏函数。
    *
    * onComplete, onSuccess, onFailure 有 Unit
    * 的返回类型，所以它们无法lia连起来。这是故意的。
    * 为了避免回调会按照某种特殊的顺序执行的建议。
    *
    * for 表达式（
    *  连接器：map, flatMap, filter,
    *  foreach, recoverWith, fallbackTo,
    *  andThen
    *  ）
    *
    *  recover， recoverWith 和 fallbackTo  连接器
    *  提供了处理 future 错误的方法。
    *  如果
    *
    */
  def runAlgoritm(i: Int):Future[Int] = Future[Int] {

    Thread.sleep(Random.nextInt(100))
     i * 2
  }

  def m5() : Unit = {

    println("starting futures")

    val result1 = runAlgoritm(10)
    val result2 = runAlgoritm(20)
    val result3 = runAlgoritm(30)

    println("before for-comprehension")

    // for 推导用来把结果连接在一起
    // for 推导返回一个新的 Future
    // 需要在 onSuccess 方法中打印结果
    val result = for {
      r1 <- result1
      r2 <- result2
      r3 <- result3
    } yield (r1 + r2 + r3)

    println("before onSuccess")

    result onSuccess {
      case value => println(s"total = $value")
    }

    println("a.."); Thread.sleep(100)
    println("b.."); Thread.sleep(100)
    println("c.."); Thread.sleep(100)
    println("d.."); Thread.sleep(100)
    println("e.."); Thread.sleep(100)
    println("f.."); Thread.sleep(100)
    println("g.."); Thread.sleep(100)
    println("h.."); Thread.sleep(100)

    println("before sleep at the end")


    var meaning = 0
    Future {
      meaning = 12
    } andThen {
      case value => println(s"meaning of life is ${value}")
    }


    Thread.sleep(2000)


  }

  m5()
}
