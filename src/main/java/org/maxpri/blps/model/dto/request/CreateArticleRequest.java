package org.maxpri.blps.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleRequest {
    private String name;

    private String body;

    private String previewText;

    private Set<Long> tagIds;
}
