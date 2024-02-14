package org.maxpri.blps.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author max_pri
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTagRequest {
    private String name;
}
