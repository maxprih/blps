package org.maxpri.blps.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
public class DeletedArticleResponse {
    private Long id;
    private String name;
}
