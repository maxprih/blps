package org.maxpri.blps.service;

import org.maxpri.blps.messaging.KafkaSender;
import org.maxpri.blps.model.dto.messages.ArticleAllVersions;
import org.maxpri.blps.model.dto.messages.ArticleMessage;
import org.maxpri.blps.model.dto.messages.ArticleVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author max_pri
 */
@Service
public class VersionService {
    private final String allVersionTopic = "all-version-topic";
    private final String allVersionReplyingTopic = "all-version-reply-topic";
    private final String rollbackVersionTopic = "rollback-version-topic";
    private final String rollbackVersionReplyingTopic = "rollback-version-reply-topic";

    private final KafkaSender sender;
    private final ArticleService articleService;

    @Autowired
    public VersionService(KafkaSender sender, ArticleService articleService) {
        this.sender = sender;
        this.articleService = articleService;
    }

    public List<ArticleVersion> getAllVersions(Long articleId) throws ExecutionException, InterruptedException, TimeoutException {
        return ((ArticleAllVersions) sender.sendAndGet(allVersionTopic, allVersionReplyingTopic, articleId)).getVersions();
    }

    public void rollbackToVersion(Long versionId) throws ExecutionException, InterruptedException, TimeoutException {
        ArticleMessage message = ((ArticleMessage) sender.sendAndGet(rollbackVersionTopic, rollbackVersionReplyingTopic, versionId));
        articleService.saveNewArticleMessage(message);
    }
}
