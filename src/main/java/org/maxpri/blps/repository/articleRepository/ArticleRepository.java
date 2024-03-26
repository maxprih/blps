package org.maxpri.blps.repository.articleRepository;

import org.maxpri.blps.model.dto.ArticleDto;
import org.maxpri.blps.model.dto.ArticlePreviewDto;
import org.maxpri.blps.model.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author max_pri
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT new org.maxpri.blps.model.dto.ArticlePreviewDto(a.id, a.name, a.previewText) FROM Article a where a.isApproved=true and a.isRejected=false and a.isDeleted=false and a.id = ?1")
    Optional<ArticlePreviewDto> findArticlePreview(Long id);

    @Query("SELECT new org.maxpri.blps.model.dto.ArticlePreviewDto(a.id, a.name, a.previewText) FROM Article a where a.isApproved=false and a.isRejected=false and a.isDeleted=false")
    List<ArticlePreviewDto> findArticlePreviewsNonApproved();

    @Query("SELECT new org.maxpri.blps.model.dto.ArticleDto(a.id, a.name, a.body, a.lastModified) FROM Article a WHERE a.id = ?1 and a.isApproved=true and a.isRejected=false and a.isDeleted=false")
    Optional<ArticleDto> findArticleDtoById(Long id);

    @Query("SELECT new org.maxpri.blps.model.dto.ArticlePreviewDto(a.id, a.name, a.previewText) FROM Article a WHERE LOWER(a.name) LIKE %:query% and a.isApproved = true and a.isRejected=false and a.isDeleted=false")
    List<ArticlePreviewDto> findPreviewsBySearchString(@Param("query") String query);

    Optional<Article> findByIdAndIsApprovedAndIsRejectedAndIsDeleted(Long id, Boolean isApproved, Boolean isRejected, Boolean isDeleted);

    @Query("SELECT DISTINCT new org.maxpri.blps.model.dto.ArticlePreviewDto(a.id, a.name, a.previewText) FROM Article a join a.tags t where t.id in (:tagIds) and a.isDeleted = false GROUP BY a.id HAVING COUNT(DISTINCT t.id) = :tagCount")
    List<ArticlePreviewDto> findArticlesByTagIds(@Param("tagIds") List<Long> tagIds, @Param("tagCount") Integer tagCount);
}