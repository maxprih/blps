package org.maxpri.blps.service;

import org.maxpri.blps.exception.ArticleNotFoundException;
import org.maxpri.blps.exception.TagNotFoundException;
import org.maxpri.blps.model.dto.ArticleDto;
import org.maxpri.blps.model.dto.ArticlePreviewDto;
import org.maxpri.blps.model.dto.request.CreateArticleRequest;
import org.maxpri.blps.model.dto.response.MessageResponse;
import org.maxpri.blps.model.entity.Article;
import org.maxpri.blps.model.entity.Tag;
import org.maxpri.blps.repsitory.ArticleRepository;
import org.maxpri.blps.repsitory.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author max_pri
 */
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
    }

    public List<ArticlePreviewDto> getPreviewList() {
        return articleRepository.findArticlePreviews();
    }

    public ArticleDto getArticleById(Long id) {
        ArticleDto articleDto = articleRepository.findArticleDtoById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
        Set<Tag> tags = tagRepository.findTagsByArticleId(id);
        articleDto.setTags(tags);
        return articleDto;
    }

    @Transactional
    public Article createArticle(CreateArticleRequest createArticleRequest) {
        Set<Tag> tags = new HashSet<>();

        createArticleRequest.getTagIds().forEach(tagId -> {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new TagNotFoundException(tagId));
            tags.add(tag);
        });

        Article article = Article.builder()
                .name(createArticleRequest.getName())
                .body(createArticleRequest.getBody())
                .previewText(createArticleRequest.getPreviewText())
                .tags(tags)
                .lastModified(LocalDateTime.now())
                .isApproved(false)
                .isRejected(false)
                .build();

        return articleRepository.save(article);
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

        article.setName(modifyRequest.getName());
        article.setBody(modifyRequest.getBody());
        article.setPreviewText(modifyRequest.getPreviewText());
        article.setTags(tags);
        article.setLastModified(LocalDateTime.now());
        article.setIsApproved(false);
        article.setIsRejected(false);

        return articleRepository.save(article);
    }

    public List<ArticlePreviewDto> search(String query) {
        return articleRepository.findPreviewsBySearchString(query.toLowerCase());
    }

    public MessageResponse approveArticle(Long id) {
        Article article = articleRepository.findByIdAndIsApprovedAndIsRejected(id, false, false)
                .orElseThrow(() -> new ArticleNotFoundException(id));
        article.setIsApproved(true);
        article.setLastModified(LocalDateTime.now());
        return new MessageResponse("Статья с ID: " + id + " была одобрена модератором");
    }

    public MessageResponse rejectArticle(Long id) {
        Article article = articleRepository.findByIdAndIsApprovedAndIsRejected(id, false, false)
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
}