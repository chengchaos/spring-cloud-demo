package luxe.chaos.train.springboottrain;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringBootTrainApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootTrainApplication.class);

    public static void main(String[] args) {
//        method1(args);
        method2(args);
//        method3(args);
    }

    private static void method1(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringBootTrainApplication.class, args);

        try {
            TimeUnit.SECONDS.sleep(10L);
        } catch (InterruptedException e) {
            LOGGER.error(StringUtils.EMPTY, e);
        }

        ctx.close();
    }

    private static void method2(String[] args) {
        SpringApplication application = new SpringApplication(SpringBootTrainApplication.class);
        application.addListeners(new ApplicationPidFileWriter("c:/works/temp/app.txt"));
        application.run();
    }

    private static void method3(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringBootTrainApplication.class, args);

        try {
            TimeUnit.SECONDS.sleep(10L);
        } catch (InterruptedException e) {
            LOGGER.error(StringUtils.EMPTY, e);
        }

        exitApplication(ctx);
    }

    private static void exitApplication(ConfigurableApplicationContext context) {
        int exitCode = SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);
        System.exit(exitCode);
    }

}
