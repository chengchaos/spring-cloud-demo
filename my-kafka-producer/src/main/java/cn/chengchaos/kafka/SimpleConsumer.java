package cn.chengchaos.kafka;

import cn.chengchaos.entity.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleConsumer.class);



    @KafkaListener(topics = "${kafka.topic.default}"
    , containerFactory = "kafkaListenerContainerFactory")
    public void receive(MessageEntity message) {

        LOGGER.info("接收到数据: <<<< {}", message);
    }
}
