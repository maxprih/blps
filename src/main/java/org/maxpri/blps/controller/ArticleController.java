package org.maxpri.blps.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.maxpri.blps.model.dto.ArticleDto;
import org.maxpri.blps.model.dto.ArticlePreviewDto;
import org.maxpri.blps.model.dto.ArticleSearchDto;
import org.maxpri.blps.model.dto.request.CreateArticleRequest;
import org.maxpri.blps.model.dto.response.MessageResponse;
import org.maxpri.blps.model.entity.articleEntity.Article;
import org.maxpri.blps.service.ArticleService;
import org.maxpri.blps.service.ImageService;
import org.maxpri.blps.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    private final ArticleService articleService;
    private final ImageService imageService;
    private final SearchService searchService;

    @Autowired
    public ArticleController(ArticleService articleService, ImageService imageService, SearchService searchService) {
        this.articleService = articleService;
        this.imageService = imageService;
        this.searchService = searchService;
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск статьи по названию")
    public ResponseEntity<List<ArticleSearchDto>> search(@RequestParam String query) throws IOException {
        return ResponseEntity.ok(searchService.findByNameFuzzy(query));
    }

    @GetMapping("/search-sub")
    @Operation(summary = "Поиск статьи по подстроке названия")
    public ResponseEntity<List<ArticleSearchDto>> searchWildcard(@RequestParam String query) throws IOException {
        return ResponseEntity.ok(searchService.findByNameWildcard(query));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение статьи по ID")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    @GetMapping("/{id}/preview")
    @Operation(summary = "Получение превью статьи по ID")
    public ResponseEntity<ArticlePreviewDto> getArticlePreview(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getPreviewById(id));
    }

    @PostMapping()
    @Operation(summary = "Создание новой статьи")
    public ResponseEntity<Article> createNewArticle(@RequestBody CreateArticleRequest createArticleRequest) {
        return ResponseEntity.ok(articleService.createArticle(createArticleRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование статьи")
    public ResponseEntity<Article> modifyArticle(@RequestBody CreateArticleRequest modifyArticleRequest,
                                                 @PathVariable Long id) {
        return ResponseEntity.ok(articleService.modifyArticle(modifyArticleRequest, id));
    }

    @PostMapping("/{id}/image")
    @Operation(summary = "Добавление картинки к статье")
    public ResponseEntity<Long> addImageToArticle(@PathVariable Long id,
                                                  @RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok(imageService.addImageToArticle(image, id));
    }

    @GetMapping("/{id}/images")
    @Operation(summary = "Получение ID картинок статьи")
    public ResponseEntity<List<Long>> getImagesIds(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getImagesForArticle(id));
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Одобрение статьи модератором")
    public ResponseEntity<MessageResponse> approveArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.approveArticle(id));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Отклонение статьи модератором")
    public ResponseEntity<MessageResponse> rejectArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.rejectArticle(id));
    }

    @GetMapping("/to_approve")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Получение непроверенных статей модератором")
    public ResponseEntity<List<ArticlePreviewDto>> getArticlesToApprove() {
        return ResponseEntity.ok(articleService.getNonApprovedArticles());
    }

    @GetMapping("/by_tags")
    @Operation(summary = "Получение статей по тэгам")
    public ResponseEntity<List<ArticlePreviewDto>> getArticlesByTags(@RequestParam List<Long> tagIds) {
        return ResponseEntity.ok(articleService.findByTags(tagIds));
    }
}