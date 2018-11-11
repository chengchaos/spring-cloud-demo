package com.example.myzuul;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@EnableZuulProxy
public class MyZuulApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyZuulApplication.class);


	public static void main(String[] args) {

        ConfigurableApplicationContext context =
                SpringApplication.run(MyZuulApplication.class, args);

        LOGGER.info("MyZuulApplication is started ... {}", context);
    }
}
