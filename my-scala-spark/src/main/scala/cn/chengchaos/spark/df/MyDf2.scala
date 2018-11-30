package cn.chengchaos.spark.df

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.slf4j.{Logger, LoggerFactory}

object MyDf2 {


  private val LOGGER:Logger = LoggerFactory.getLogger("MyDf2")

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("MyDf2")
      .master("local[2]")
      .getOrCreate()


    val rdd = spark.sparkContext.textFile("file:///e:/people_info.txt")
        .map(_.split(","))
        .map(arr => Row(arr(0).toInt, arr(1), arr(2).toInt))

    val structType: StructType = StructType(
      Array(StructField("id", IntegerType)
        , StructField("name", StringType)
        , StructField("age", IntegerType)))

    val peopleDf: DataFrame = spark.createDataFrame(rdd, structType)

    peopleDf.printSchema()

    peopleDf.select(peopleDf.col("id"), peopleDf.col("name"))
        .show()

    LOGGER.debug("+++ 执行完成 +++")
    LOGGER.info("+++ 执行完成 +++")
    LOGGER.warn("+++ 执行完成 +++")
    LOGGER.error("+++ 执行完成 +++")

    spark.stop()
  }
}
