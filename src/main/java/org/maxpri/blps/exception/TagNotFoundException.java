package org.maxpri.blps.exception;

import lombok.Getter;

/**
 * @author max_pri
 */
@Getter
public class TagNotFoundException extends RuntimeException {
    private final Long tagId;

    public TagNotFoundException(Long tagId) {
        this.tagId = tagId;
    }

}
