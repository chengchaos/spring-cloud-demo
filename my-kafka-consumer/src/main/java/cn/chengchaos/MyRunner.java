package cn.chengchaos;

import org.apache.spark.sql.SparkSession;

public class MyRunner {

    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder()
                .appName("MyRunner")
                .enableHiveSupport()
                .getOrCreate();


        spark.sql("select * from mytb")
                .show();

        spark.stop();

    }
}
