package luxe.chaos.train.springboottrain.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class ShutdownConfig {


    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownConfig.class);

    @Bean
    public TerminateBean terminateBean() {
        return new TerminateBean();
    }

    public static class TerminateBean {


        @PostConstruct
        public void postConstruct() {
            LOGGER.warn("TerminateBean is Construct! ");
        }
        @PreDestroy
        public void preDestroy() {
            LOGGER.warn("TerminateBean is Destroyed! ");
        }
    }
}
