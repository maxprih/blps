package org.maxpri.blps.repository.articleRepository;

import org.maxpri.blps.model.dto.TagDto;
import org.maxpri.blps.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author max_pri
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT a.tags FROM Article a WHERE a.id = :articleId")
    Set<Tag> findTagsByArticleId(@Param("articleId") Long articleId);

    @Query("SELECT new org.maxpri.blps.model.dto.TagDto(t.id, t.name) from Tag t")
    List<TagDto> findAllTagDtos();
}
