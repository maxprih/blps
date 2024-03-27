package org.maxpri.blps.model.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author max_pri
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleMessage {
    private Long articleId;
    private String name;
    private String body;
    private String previewText;
    private LocalDateTime lastModified;
    private Boolean isApproved;
    private Boolean isRejected;
    private Boolean isDeleted;
}
