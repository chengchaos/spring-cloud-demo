package cn.chengchaos

import org.apache.spark.sql.SparkSession

object SparkSessionApp {


  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder()
      .appName("SparkSessionApp")
      .master("local[2]")
      .getOrCreate()

    val peoples = sparkSession.read.json("file:///e:/people.json")

    peoples.show()

    sparkSession.stop()
  }

}
