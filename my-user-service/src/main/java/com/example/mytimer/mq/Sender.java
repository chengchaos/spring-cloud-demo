package com.example.mytimer.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/1/22 0022 下午 3:19 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;


    public void send() {
        String context = "hello "+ new Date();
        System.out.println("Sender: "+ context);

        this.rabbitTemplate.convertAndSend("hello", context);
    }
}
