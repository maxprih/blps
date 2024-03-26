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
public class ImageMessage {
    private Long articleId;
    private String fileName;
    private String contentType;
    private byte[] fileContent;
}
