package org.maxpri.blps.model.dto.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author max_pri
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageNameMessage {
    private String imageName;
    private Long articleId;
}
