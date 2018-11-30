package cn.chengchaos

//import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

object HiveContextApp {


  def main(args:Array[String]) :Unit = {

    val sparkConf = new SparkConf()

    sparkConf.setAppName("HiveContextApp")
    sparkConf.setMaster("local[2]")

    val sc = new SparkContext(sparkConf)

//    val hiveContext = new HiveContext(sc)
//
//    hiveContext.table("emp").show()

    sc.stop()
  }

}
