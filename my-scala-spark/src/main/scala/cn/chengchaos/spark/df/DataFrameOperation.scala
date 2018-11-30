package cn.chengchaos.spark.df

import org.apache.spark.sql.SparkSession

object DataFrameOperation {

  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder
      .appName("DataFrameOperation")
      .master("local[2]")
      .getOrCreate()

    demo1(spark)

    spark.stop
  }


  /**
    * Inferring the Schema
    * @param spark
    */
  def demo2(spark:SparkSession) : Unit = {

  }



  def demo1(spark:SparkSession) = {
    // load 返回一个 DataFrame
    // Loads input in
    val peopleDf =  spark.read.format("json").load("file:///e:/people.json")

    // 输出
    peopleDf.printSchema

    // 默认展示前 20 条
    peopleDf.show
    //peopleDf.show(1)

    // 查询某一列
    // SELECT name FROM table_name
    peopleDf.select("name").show()

    // 查询某列的数据, 并计算
    // SELECT name, age + 10 FROM table_name
    peopleDf.select(peopleDf.col("name")
      , (peopleDf.col("age") + 10).as("new_age")
    ).show

    // 查询年龄大于 10 的
    peopleDf.filter(peopleDf.col("age") > 19).show
    peopleDf.where("age > 20").show()

    // 根据某一列分组
    // select age, count(1) from table group by age
    peopleDf.groupBy("age").count().show


  }


}