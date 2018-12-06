package cn.chengchaos;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SaveDataToHive {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveDataToHive.class);

    private static final Pattern COMMOA = Pattern.compile(",");

    public static void main(String[] args) {

//        System.setProperty("spark.sql.warehouse.dir","hdfs://minan-kf-2:8020/user/warehouseLocation/");
//        yourSparkConf.set("spark.hadoop.validateOutputSpecs", "false")
//        val sc = SparkContext(yourSparkConf)

        System.setProperty("spark.hadoop.validateOutputSpecs", "false");

        SparkSession session = SparkSession.builder()

                .master("local[2]")
                .appName("SaveDataToHive")
                //.config("spark.sql.warehouse.dir", warehouseLocation)
                .config("spark.hadoop.validateOutputSpecs", "false")
                .enableHiveSupport()
                .getOrCreate();


        List<String> lines = Arrays.asList(
                "4,曲强,chengchaos@gmail.com"
                , "2,高继原,gaojiyuan@163.com"
                , "3,韩冬,handong66666@yahoo.com.cn"
        );


        String hdfsPrefix = "hdfs://minan-kf-2:8020";
        String filePath = "/user/spark/info_txt"; // NOSONAR


        try (JavaSparkContext jsc = new JavaSparkContext(session.sparkContext())) {

            Configuration hadoopConf = jsc.hadoopConfiguration();

            org.apache.hadoop.fs.Path path = new org.apache.hadoop.fs.Path(filePath);
            FileSystem hdfs = FileSystem.get(hadoopConf);

            LOGGER.info("准备删除目录");
            if (hdfs.exists(path)) {
                // false: 为防止误删，禁止递归删除
                // true: ...
                hdfs.delete(path, true);
                LOGGER.info("删除目录: {}", path);
            }

            jsc.parallelize(lines)
                    .saveAsTextFile(hdfsPrefix + filePath);

            String createTable = " CREATE TABLE IF NOT EXISTS test_cc (id STRING, name STRING, email STRING) " +
                    "row format delimited fields terminated by ',' ";
            String loadData = " LOAD DATA INPATH '"+ filePath + "/part-*' INTO TABLE test_cc ";

            String sqlSelect = "SELECT * FROM test_cc ";

            LOGGER.info("创建表: {}", createTable);
            session.sql(createTable);

            LOGGER.info("装在数据:{}", loadData);
            session.sql(loadData).show();

            LOGGER.info("查询一下: {}", sqlSelect);
            session.sql(sqlSelect).show();

            session.stop();
        } catch (IOException e) {
            LOGGER.error("(*＾ー＾)", e);
        }


    }
}
