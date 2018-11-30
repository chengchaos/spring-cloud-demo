package cn.chengchaos;

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
import java.util.List;
import java.util.regex.Pattern;

public class SparkRdd2DataFrame {

    private static final Pattern COMMOA = Pattern.compile(",");


    /**
     * https://spark.apache.org/docs/2.1.0/sql-programming-guide.html#creating-dataframes
     *
     * @param args : String[]
     */
    public static void main(String[] args) {

        List<StructField> fields = new ArrayList<>();
        fields.add(DataTypes.createStructField("id", DataTypes.IntegerType, true));
        fields.add(DataTypes.createStructField("name", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("age", DataTypes.IntegerType, true));

        StructType schema = DataTypes.createStructType(fields);

        SparkSession session = SparkSession.builder()
                .appName("sparkRdd2DataFrame")
                .master("local[2]")
                .getOrCreate();

        Function<String[], Row> toRow = arr -> {
            Integer id = Integer.valueOf(arr[0]);
            String name = arr[1];
            Integer age = Integer.valueOf(arr[2]);
            return RowFactory.create(id, name, age);
        };

        JavaRDD<Row> rowRdd = session.sparkContext()
                .textFile("file:///e:/people_info.txt", 1)
                .toJavaRDD()
                .map(COMMOA::split)
                .map(toRow);


        Dataset<Row> dataFrame = session.createDataFrame(rowRdd, schema);

        dataFrame.createOrReplaceTempView("infos");

        session.sql("select * from infos").show();

        session.stop();


    }
}
