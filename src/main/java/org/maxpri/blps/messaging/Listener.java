package org.maxpri.blps.messaging;

import org.maxpri.blps.model.dto.messages.ImageNameMessage;
import org.maxpri.blps.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author max_pri
 */
@Service
public class Listener {
    private final String imageNameTopic = "image-name-topic";
    private final String imageUrlTopic = "image-url-topic";
    private final ArticleService articleService;

    @Autowired
    public Listener(ArticleService articleService) {
        this.articleService = articleService;
    }

    @KafkaListener(topics = imageNameTopic, groupId = "${spring.kafka.consumer.group-id}")
    public void attachImageToArticle(ImageNameMessage message) {
        articleService.attachImageToArticle(message);
    }
//
//    @KafkaListener(topics = imageUrlTopic, groupId = "${spring.kafka.consumer.group-id}")
//    public void attachImageToArticle(String url) {
//        articleService.attachImageToArticle(message);
//    }
}
