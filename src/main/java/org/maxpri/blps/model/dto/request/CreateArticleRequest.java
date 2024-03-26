package org.maxpri.blps.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    private List<MultipartFile> files;
}
