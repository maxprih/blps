package org.maxpri.blps.repository.imageRepository;

import org.maxpri.blps.model.entity.imageEntity.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author max_pri
 */
@Repository
public interface ImageRepository extends JpaRepository<ArticleImage, Long> {
    List<ArticleImage> findAllByArticleId(Long articleId);
}
