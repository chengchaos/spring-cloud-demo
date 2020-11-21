package org.example

import java.lang.invoke.{MethodHandle, MethodHandles, MethodType}
import java.util.concurrent.TimeUnit

import com.esotericsoftware.reflectasm.ConstructorAccess
import com.google.common.base.Stopwatch
import org.apache.commons.lang3.time.StopWatch
import org.example.netty.server.MyBean2
import org.junit.Test

class MyTest {

  val times: Int = 100_000_000;
  var formatter: String = "%s %d times using %d ms"

  //通过高性能的ReflectAsm库进行测试，仅进行一次methodAccess获取
  @Test def reflectAsmGet(): Unit = {

    val constructorAccess: ConstructorAccess[MyBean] = ConstructorAccess.get(classOf[MyBean])
    var bean: MyBean = null
    val watch: StopWatch = StopWatch.createStarted
    for (i <- 0 until times) {
      bean = constructorAccess.newInstance
      bean.setName("chengchao")
      bean.getAge
    }
    watch.stop()
    val result: String = String.format(formatter,
      "directGet",
      times,
      watch.getTime(TimeUnit.MILLISECONDS))
    System.out.println(result)
  }

  @Test def reflectAsDi(): Unit = {
    var bean: MyBean2 = null
    val watch: Stopwatch = Stopwatch.createStarted
    val lk : MethodHandles.Lookup = MethodHandles.lookup()
    val mt : MethodType = MethodType.methodType(classOf[MyBean2])
    val mh: MethodHandle = lk.findConstructor(classOf[MyBean2], mt)

    for (i <- 0 until times) {
      bean = mh.invokeExact()
    }
    watch.stop
    val result: String = String.format(formatter, "directGet", times, watch.elapsed(TimeUnit.MILLISECONDS))
    System.out.println(result)
  }
}
