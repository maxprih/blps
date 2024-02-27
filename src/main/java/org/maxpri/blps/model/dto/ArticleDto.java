package org.maxpri.blps.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.maxpri.blps.model.entity.articleEntity.Tag;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
public class ArticleDto {
    private Long id;

    private String name;

    private String body;

    private LocalDateTime lastModified;

    private Set<Tag> tags;

    public ArticleDto(Long id, String name, String body, LocalDateTime lastModified) {
        this.id = id;
        this.name = name;
        this.body = body;
        this.lastModified = lastModified;
    }
}
