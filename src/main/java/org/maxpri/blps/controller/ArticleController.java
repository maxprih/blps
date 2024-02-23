package org.maxpri.blps.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.maxpri.blps.model.dto.ArticleDto;
import org.maxpri.blps.model.dto.ArticlePreviewDto;
import org.maxpri.blps.model.dto.request.CreateArticleRequest;
import org.maxpri.blps.model.dto.response.MessageResponse;
import org.maxpri.blps.model.entity.Article;
import org.maxpri.blps.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/preview")
    @Operation(summary = "Получение превью всех статей")
    public ResponseEntity<List<ArticlePreviewDto>> getPreviews() {
        return ResponseEntity.ok(articleService.getPreviewList());
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск статьи по названию")
    public ResponseEntity<List<ArticlePreviewDto>> searchPreviews(@RequestParam String query) {
        return ResponseEntity.ok(articleService.search(query));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение статьи по ID")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
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