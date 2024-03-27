package org.maxpri.blps.service;

import org.maxpri.blps.exception.ArticleNotFoundException;
import org.maxpri.blps.exception.ImageNotFoundException;
import org.maxpri.blps.exception.TagNotFoundException;
import org.maxpri.blps.messaging.KafkaSender;
import org.maxpri.blps.model.dto.ArticleDto;
import org.maxpri.blps.model.dto.ArticlePreviewDto;
import org.maxpri.blps.model.dto.DeletedArticleResponse;
import org.maxpri.blps.model.dto.messages.ArticleMessage;
import org.maxpri.blps.model.dto.messages.ImageMessage;
import org.maxpri.blps.model.dto.messages.ImageNameMessage;
import org.maxpri.blps.model.dto.request.CreateArticleRequest;
import org.maxpri.blps.model.dto.response.MessageResponse;
import org.maxpri.blps.model.entity.Article;
import org.maxpri.blps.model.entity.Tag;
import org.maxpri.blps.repository.articleRepository.ArticleRepository;
import org.maxpri.blps.repository.articleRepository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author max_pri
 */
@Service
public class ArticleService {
    private final String imageTopic = "image-topic";
    private final String imageUrlName = "image-url-topic";
    private final String deleteImageTopic = "delete-image-topic";
    private final String versionTopic = "version-topic";
    private final String replyingImageUrlTopic = "image-url-reply-topic";
    private final String deleteVersionTopic = "delete-version-topic";

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final KafkaSender sender;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, TagRepository tagRepository, KafkaSender sender, JdbcTemplate jdbcTemplate) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.sender = sender;
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArticlePreviewDto getPreviewById(Long id) {
        return articleRepository.findArticlePreview(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    public Object getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));

        if (article.getIsDeleted()) {
            return new DeletedArticleResponse(article.getId(), article.getBody());
        }

        ArticleDto articleDto = articleRepository.findArticleDtoById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));

        Set<Tag> tags = tagRepository.findTagsByArticleId(id);
        articleDto.setTags(tags);
        return articleDto;
    }

    @Transactional
    public Article createArticle(CreateArticleRequest createArticleRequest) {
        Set<Tag> tags = new HashSet<>();

        if (createArticleRequest.getTagIds() != null) {
            createArticleRequest.getTagIds().forEach(tagId -> {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new TagNotFoundException(tagId));
                tags.add(tag);
            });
        }

        Article article = Article.builder()
                .name(createArticleRequest.getName())
                .body(createArticleRequest.getBody())
                .previewText(createArticleRequest.getPreviewText())
                .tags(tags)
                .lastModified(LocalDateTime.now())
                .isApproved(false)
                .isRejected(false)
                .isDeleted(false)
                .build();

        articleRepository.save(article);
        createArticleRequest.getFiles().forEach(file -> {
            try {
                sendImage(article.getId(), file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return article;
    }

    public void addImageToArticle(Long articleId, MultipartFile file) throws IOException {
        articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        sendImage(articleId, file);
    }

    @Transactional
    public void attachImageToArticle(ImageNameMessage message) {
        Article article = articleRepository.findById(message.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException(message.getArticleId()));

        article.attachImage(message.getImageName());

        articleRepository.save(article);
    }

    public Set<String> getImageNames(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        return article.getImageFilenames();
    }

    public String getUrlForImage(String filename) throws ExecutionException, InterruptedException, TimeoutException {
        doesImageExist(filename);
        return sender.sendAndGet(imageUrlName, replyingImageUrlTopic, filename).toString();
    }

    @Transactional
    public Article modifyArticle(CreateArticleRequest modifyRequest, Long id) {
        Set<Tag> tags = new HashSet<>();

        modifyRequest.getTagIds().forEach(tagId -> {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new TagNotFoundException(tagId));
            tags.add(tag);
        });

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));

        sender.send(versionTopic, mapArticleToMessage(article));

        article.setName(modifyRequest.getName());
        article.setBody(modifyRequest.getBody());
        article.setPreviewText(modifyRequest.getPreviewText());
        article.setTags(tags);
        article.setLastModified(LocalDateTime.now());
        article.setIsApproved(false);
        article.setIsRejected(false);

        return articleRepository.save(article);
    }

//    public List<ArticlePreviewDto> search(String query) {
//        return articleRepository.findPreviewsBySearchString(query.toLowerCase());
//    }

    public MessageResponse approveArticle(Long id) {
        Article article = articleRepository.findByIdAndIsApprovedAndIsRejectedAndIsDeleted(id, false, false, false)
                .orElseThrow(() -> new ArticleNotFoundException(id));
        article.setIsApproved(true);
        article.setLastModified(LocalDateTime.now());
        return new MessageResponse("Статья с ID: " + id + " была одобрена модератором");
    }

    public MessageResponse rejectArticle(Long id) {
        Article article = articleRepository.findByIdAndIsApprovedAndIsRejectedAndIsDeleted(id, false, false, false)
                .orElseThrow(() -> new ArticleNotFoundException(id));
        article.setIsRejected(true);
        article.setLastModified(LocalDateTime.now());
        return new MessageResponse("Статья с ID: " + id + " была отклонена модератором");
    }

    public List<ArticlePreviewDto> getNonApprovedArticles() {
        return articleRepository.findArticlePreviewsNonApproved();
    }

    public List<ArticlePreviewDto> findByTags(List<Long> tagIds) {
        tagIds.forEach(tagId -> {
            if (!tagRepository.existsById(tagId)) {
                throw new TagNotFoundException(tagId);
            }
        });

        return articleRepository.findArticlesByTagIds(tagIds, tagIds.size());
    }

    public Long deleteArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
        article.setIsDeleted(true);
        article.setLastModified(LocalDateTime.now());
        article.setBody("");
        articleRepository.save(article);
        return article.getId();
    }

    public void deleteOldRejectedArticles() {
        List<Article> articles = articleRepository.deleteRejectedArticlesOlderThanOneWeek(LocalDateTime.now().minusDays(7));
        System.out.println(articles);
        System.out.println(articles.size());
    }

    @Transactional
    public void fullDeleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));
        Set<String> imageNames = article.getImageFilenames();

        for (String imageName : imageNames) {
            deleteImageByName(imageName);
        }
        deleteArticleVersions(articleId);
        articleRepository.deleteById(articleId);
    }

    public void deleteImageByName(String imageName) {
        doesImageExist(imageName);
        deleteImage(imageName);
    }

    private void deleteArticleVersions(Long articleId) {
        sender.send(deleteVersionTopic, articleId);
    }

    public void saveNewArticleMessage(ArticleMessage articleMessage) {
        Article article = mapMessageToArticle(articleMessage);
        articleRepository.save(article);
    }

    private void sendImage(Long articleId, MultipartFile file) throws IOException {
        sender.send(imageTopic, new ImageMessage(articleId, file.getOriginalFilename(), file.getContentType(), file.getBytes()));
    }

    private void deleteImage(String imageName) {
        sender.send(deleteImageTopic, imageName);
    }

    private void doesImageExist(String filename) {
        String sql = "SELECT EXISTS(SELECT 1 FROM article_images WHERE image_filename = ?)";
        if (!Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, filename))) {
            throw new ImageNotFoundException(filename);
        }
    }

    private ArticleMessage mapArticleToMessage(Article article) {
        return ArticleMessage.builder()
                .articleId(article.getId())
                .name(article.getName())
                .body(article.getBody())
                .isApproved(article.getIsApproved())
                .isRejected(article.getIsRejected())
                .isDeleted(article.getIsDeleted())
                .lastModified(article.getLastModified())
                .previewText(article.getPreviewText())
                .build();
    }

    private Article mapMessageToArticle(ArticleMessage article) {
        return Article.builder()
                .id(article.getArticleId())
                .name(article.getName())
                .body(article.getBody())
                .isApproved(article.getIsApproved())
                .isRejected(article.getIsRejected())
                .isDeleted(article.getIsDeleted())
                .lastModified(article.getLastModified())
                .previewText(article.getPreviewText())
                .build();
    }
}