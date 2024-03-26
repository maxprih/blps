package org.maxpri.blps.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

/**
 * @author max_pri
 */
@Configuration
public class KafkaConfig {
    private String replyImageUrlTopic = "image-url-reply-topic";
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> pf) {
        return new KafkaTemplate<>(pf);
    }

    @Bean
    public ReplyingKafkaTemplate<String, Object, Object> rkt(ProducerFactory<String, Object> pf,
                                                      ConcurrentKafkaListenerContainerFactory<String, Object> factory,
                                                      KafkaTemplate<String, Object> template) {

        factory.setReplyTemplate(template);
        ConcurrentMessageListenerContainer<String, Object> container = factory.createContainer(replyImageUrlTopic);
        container.getContainerProperties().setGroupId(groupId);
        ReplyingKafkaTemplate<String, Object, Object> replier = new ReplyingKafkaTemplate<>(pf, container);
        replier.setDefaultTopic(replyImageUrlTopic);
        return replier;
    }

//    @Bean
//    public ConcurrentMessageListenerContainer<String, Object> repliesContainer(
//            ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory) {
//        ConcurrentMessageListenerContainer<String, Object> repliesContainer = containerFactory.createContainer(REPLY_TOPICS);
//        repliesContainer.getContainerProperties().setGroupId(CONSUMER_GROUPS);
//        repliesContainer.setAutoStartup(false);
//        return repliesContainer;
//    }
}
