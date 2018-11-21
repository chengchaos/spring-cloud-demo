package cn.chengchaos

import org.apache.spark.sql.SparkSession

/**
 * Hello world!
 *
 */
object App {

  def main(args:Array[String]) :Unit = {

    println( "Hello World!" )

    val spark = SparkSession.builder()
      .appName("Spark SQL basic example")
      .config("spark.someconfig.otpion", "some-value")
      .getOrCreate()

  }
}
