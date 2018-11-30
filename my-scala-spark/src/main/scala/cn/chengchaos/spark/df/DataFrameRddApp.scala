package cn.chengchaos.spark.df

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

object DataFrameRddApp {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[2]")
      .appName("DataFrameRddApp")
      .getOrCreate()

    inferProgram(spark)

    spark.stop()
  }

  /**
    * 使用编程方式
    * 实现不知道字段信息和数据类型和
    * @param spark SparkSession
    */
  def inferProgram(spark:SparkSession) :Unit = {


    val rdd = spark.sparkContext.textFile("file:///e:/people_info.txt")

    val infoRdd = rdd.map(_.split(","))
      .map(line => Row(line(0).toInt, line(1), line(2).toInt))


    val structType = StructType(Array(StructField("id", IntegerType, false)
      , StructField("name", StringType)
      , StructField("age", IntegerType)))


    val infoDf = spark.createDataFrame(infoRdd, structType)

    infoDf.createOrReplaceTempView("infos")

    spark.sql("select * from infos").show()


  }

  /**
    * 使用反射
    * 定义 case class
    * 需要实现知道字段和数据类型
    * @param spark SparkSession
    */
  def inferReflection(spark: SparkSession):Unit = {
    // RDD => DataFreme

    val rdd = spark.sparkContext.textFile("file:///e:/people_info.txt")

    rdd.foreach(println(_))

    // 隐式转换
    import spark.implicits._

    val infoDf = rdd.map(_.split(","))
      .map(line => Info(line(0).toInt, line(1), line(2).toInt))
      .toDF()

    infoDf.printSchema()

    infoDf.show

    infoDf.select(infoDf.col("id")).show

    infoDf.createOrReplaceTempView("infos")

    spark.sql("select * from infos where age > 44").show
  }

}


case class Info(id:Int, name:String, age:Int)