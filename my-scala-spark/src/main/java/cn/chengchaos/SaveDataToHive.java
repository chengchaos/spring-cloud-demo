package cn.chengchaos;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SaveDataToHive {

    private static final Pattern COMMOA = Pattern.compile(",");

    public static void main(String[] args) {

        SparkSession session = SparkSession.builder()
                .master("local[2]")
                .appName("SaveDataToHive")
                //.config("spark.sql.warehouse.dir", warehouseLocation)
                .enableHiveSupport()
                .getOrCreate();


        List<String> lines = Arrays.asList(
                "4,曲强,chengchaos@gmail.com"
                , "2,高继原,gaojiyuan@163.com"
                , "3,韩冬,handong66666@yahoo.com.cn"
        );


        JavaSparkContext jsc = new JavaSparkContext(session.sparkContext());
        jsc.parallelize(lines)
                .saveAsTextFile("hdfs://minan-kf-2:8020/user/spark/info.txt");

        session.sql(" CREATE TABLE IF NOT EXISTS test_cc (id STRING, name STRING, email STRING) " +
                "row format delimited fields terminated by ','");

        session.sql(" LOAD DATA INPATH 'hdfs://minan-kf-2:8020/user/spark/info.txt/part-*' INTO TABLE test_cc");

        session.sql("SELECT * FROM test_cc").show();

        jsc.close();
        session.stop();

    }
}
