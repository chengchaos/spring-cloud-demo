package cn.chengchaos;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@EnableAutoConfiguration
@SpringBootApplication
public class MyKafkaProducerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MyKafkaProducerApplication.class, args);



    }
}
