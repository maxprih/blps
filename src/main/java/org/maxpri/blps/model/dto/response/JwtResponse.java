package org.maxpri.blps.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author max_pri
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
