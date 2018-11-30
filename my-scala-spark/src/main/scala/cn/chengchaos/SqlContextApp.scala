package cn.chengchaos

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

object SqlContextApp {

  def main(args:Array[String]) :Unit = {
    println("kao")

    val path = args(0)

    val sparkConf = new SparkConf()
    sparkConf.setAppName("SqlContextApp")
    sparkConf.setMaster("local[2]")
    val sc = new SparkContext(sparkConf)
    // 创建 SqlContext
    val sqlContext = new SQLContext(sc)

    // 处理数据
    val peoples = sqlContext.read
        .format("json")
        .load(path)

    peoples.printSchema()
    peoples.show()
    //
    sc.stop()
  }
}
