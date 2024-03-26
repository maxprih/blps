package org.maxpri.blps.exception;

import lombok.Getter;

/**
 * @author max_pri
 */
@Getter
public class ImageNotFoundException extends RuntimeException {
    private final String filename;

    public ImageNotFoundException(String filename) {
        this.filename = filename;
    }
}
