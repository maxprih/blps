package org.maxpri.blps.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
public class ArticleSearchDto {
    private Long id;
    private String name;
    private LocalDateTime lastModified;
}
