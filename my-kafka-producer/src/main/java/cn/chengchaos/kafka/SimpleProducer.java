package cn.chengchaos.kafka;

import cn.chengchaos.entity.MessageEntity;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class SimpleProducer {

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;


    public void send(String topic, MessageEntity message) {
        kafkaTemplate.send(topic, JSON.toJSONString(message));
    }

    public void send(String topic, String key, MessageEntity entity) {

        String json = JSON.toJSONString(entity);

        ProducerRecord<String, String> record = new ProducerRecord<>(
                topic
                , key
                , json
        );

        long startTime = System.currentTimeMillis();
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);

        future.addCallback(new MyProducerCallback(startTime, key, json));
    }

}
