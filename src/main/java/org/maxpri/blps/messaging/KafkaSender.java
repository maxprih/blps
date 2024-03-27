package org.maxpri.blps.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author max_pri
 */
@Service
public class KafkaSender {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;

    public KafkaSender(KafkaTemplate<String, Object> kafkaTemplate, ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.replyingKafkaTemplate = replyingKafkaTemplate;
    }

    public void send(String topicName, Object payload) {
        kafkaTemplate.send(topicName, payload);
    }

    public Object sendAndGet(String topicName, String replyingTopicName, Object payload) throws ExecutionException, InterruptedException, TimeoutException {
        replyingKafkaTemplate.setDefaultTopic(replyingTopicName);
        ProducerRecord<String, Object> record = new ProducerRecord<>(topicName, payload);
        RequestReplyFuture<String, Object, Object> future = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, Object> response = future.get(15, TimeUnit.SECONDS);
        return response.value();
    }
}
