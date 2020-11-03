package luxe.chaos.train.springboottrain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * https://www.cnblogs.com/Grand-Jon/p/10089387.html
 *
 * !!!
 * An Executor is required to handle java.util.concurrent.Callable return values.
 * Please, configure a TaskExecutor in the MVC config under "async support".
 * The SimpleAsyncTaskExecutor currently in use is not suitable under load.
 *
 */
@Configuration
public class AsyncSupportWebMvcConfigurer implements WebMvcConfigurer {


    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        executor.setCorePoolSize(4);
        executor.setThreadNamePrefix("JBPool-");

        return executor;
    }

    public TimeoutCallableProcessingInterceptor timeoutCallableProcessingInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        System.out.println("configureAsyncSupport ... ");
        configurer.setTaskExecutor(threadPoolTaskExecutor());
        configurer.setDefaultTimeout(60 * 1000L);
        configurer.registerCallableInterceptors(timeoutCallableProcessingInterceptor());

    }
}
