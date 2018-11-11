package com.example.myeureka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaApplication.class);


	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EurekaApplication.class, args);

        LOGGER.info("Eureka is started.");
    }
}
