package org.maxpri.blps.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
public class ArticlePreviewDto {
    private Long id;
    private String name;
    private String previewText;
}
