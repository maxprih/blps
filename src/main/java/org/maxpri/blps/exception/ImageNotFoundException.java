package org.maxpri.blps.exception;

import lombok.Getter;

/**
 * @author max_pri
 */
@Getter
public class ImageNotFoundException extends RuntimeException {
    private final Long imageId;

    public ImageNotFoundException(Long imageId) {
        this.imageId = imageId;
    }
}
