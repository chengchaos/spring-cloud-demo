package com.example.mytimer.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/1/22 0022 下午 3:20 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@Component
@RabbitListener(queues="hello")
public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @RabbitHandler
    public void process(String hello) {
        LOGGER.info("receive and process message ==> {}", hello);
    }
}
