package com.example.mytimer.mq;

import com.example.mytimer.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/1/22 0022 下午 3:24 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class HelloMQTest {

    @Autowired
    private Sender sender;

    @Test
    public void sendHelloTest() {
        sender.send("hello world");
    }
}
