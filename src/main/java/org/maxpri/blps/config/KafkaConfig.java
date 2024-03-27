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
    private String replyAllVersionTopic = "all-version-reply-topic";
    private String rollbackVersionReplyingTopic = "rollback-version-reply-topic";
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
        ConcurrentMessageListenerContainer<String, Object> container = factory.createContainer(replyImageUrlTopic, replyAllVersionTopic, rollbackVersionReplyingTopic);
        container.getContainerProperties().setGroupId(groupId);
        ReplyingKafkaTemplate<String, Object, Object> replier = new ReplyingKafkaTemplate<>(pf, container);
        replier.setDefaultTopic(replyImageUrlTopic);
        return replier;
    }

}
