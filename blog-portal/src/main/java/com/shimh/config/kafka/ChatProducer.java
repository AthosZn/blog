package com.shimh.config.kafka;

import ai.yunxi.im.common.pojo.ImRouterRequestMessage;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class ChatProducer {

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, ImRouterRequestMessage> kafkaTemplate;

    public void send(String topic, ImRouterRequestMessage message) {
        kafkaTemplate.send(topic, message);
    }

    public void send(String topic, String key, ImRouterRequestMessage entity) {
        ProducerRecord<String, ImRouterRequestMessage> record = new ProducerRecord<>(
                topic,
                entity);

        long startTime = System.currentTimeMillis();

        ListenableFuture<SendResult<String, ImRouterRequestMessage>> future = kafkaTemplate.send(record);
        future.addCallback(new ProducerCallback(startTime, entity));
    }

}
