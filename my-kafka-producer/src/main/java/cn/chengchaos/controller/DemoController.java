package cn.chengchaos.controller;

import cn.chengchaos.entity.ErrorCode;
import cn.chengchaos.entity.MessageEntity;
import cn.chengchaos.entity.MessageResponse;
import cn.chengchaos.kafka.SimpleProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private SimpleProducer producer;

    @Value("${kafka.topic.default}")
    private String topic;


    @GetMapping("/hello")
    public String sayHello() {

        return "hello";
    }

    @PostMapping("/send")
    public MessageResponse send(@RequestBody MessageEntity message) {

        LOGGER.info("message >>> {}", message);
        producer.send(topic, "key", message);

        return new MessageResponse(ErrorCode.SUCCESS, "OK");


    }
}
