package cn.chengchaos.config;

import cn.chengchaos.entity.MessageEntity;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${kafka.consumer.servers}")
    private String servers;

    @Value("${kafka.consumer.enable.auto.commit}")
    private boolean enableAutoCommit;

    @Value("${kafka.consumer.session.timeout}")
    private String sessionTimeout;

    @Value("${kafka.consumer.auto.commit.interval}")
    private String autoCommitInterval;

    @Value("${kafka.consumer.group.id}")
    private String groupId;

    @Value("${kafka.consumer.auto.offset.reset}")
    private String autoOffsetReset;

    @Value("${kafka.consumer.concurrency}")
    private int concurrency;




    public ConsumerFactory<String, MessageEntity> consumerFactory() {

        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs()
                , new StringDeserializer()
                , new JsonDeserializer<>(MessageEntity.class)
        );
    }

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);

        return props;
    }

    @Bean
    public
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MessageEntity>>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, MessageEntity> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        // 并发
        factory.setConcurrency(concurrency);
        // 超时
        factory.getContainerProperties()
                .setPollTimeout(1500);

        return factory;
    }
}
