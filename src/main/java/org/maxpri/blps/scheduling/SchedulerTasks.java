package org.maxpri.blps.scheduling;

import org.maxpri.blps.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author max_pri
 */
@EnableScheduling
@Configuration
public class SchedulerTasks {
    private final ArticleService articleService;

    @Autowired
    public SchedulerTasks(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Scheduled(cron = "0 51 14 * * *", zone = "Europe/Moscow")
    public void deleteOldRejectedArticles() {
        articleService.deleteOldRejectedArticles();
    }
}
