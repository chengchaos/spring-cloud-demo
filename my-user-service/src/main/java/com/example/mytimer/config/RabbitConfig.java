package com.example.mytimer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/1/22 0022 下午 3:22 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }
}
