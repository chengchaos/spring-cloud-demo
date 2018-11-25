package cn.chengchaos.kafka;

import cn.chengchaos.entity.MessageEntity;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;


public class MyProducerCallback implements ListenableFutureCallback<SendResult<String, MessageEntity>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyProducerCallback.class);


    private long startTime;
    private String key;
    private MessageEntity message;

    public MyProducerCallback(long startTime, String key , MessageEntity message) {
        this.startTime = startTime;
        this.key = key;
        this.message = message;
    }


    @Override
    public void onSuccess(SendResult<String, MessageEntity> result) {

        if (result == null) {
            return;
        }

        RecordMetadata metadata = result.getRecordMetadata();
        if (metadata == null) {
            return;
        }


        if (LOGGER.isInfoEnabled()) {

            long elapsedTime = System.currentTimeMillis() - startTime;
            StringBuilder record = new StringBuilder();
            record.append("message(")
                    .append("key = ").append(key)
                    .append(", messsage = ").append(message)
                    .append(") sent to partition(")
                    .append(metadata.partition())
                    .append(") with offset(")
                    .append(metadata.offset())
                    .append(") in ")
                    .append(elapsedTime)
                    .append(" ms !!!");

            LOGGER.info("发送成功 >>>> {}", record);
        }
    }

    @Override
    public void onFailure(Throwable ex) {

        ex.printStackTrace();
    }
}
