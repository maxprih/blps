package org.maxpri.blps.exception;

import lombok.Getter;

/**
 * @author max_pri
 */
public class TagNotFoundException extends RuntimeException {
    @Getter
    private Long tagId;

    public TagNotFoundException(Long tagId) {
        this.tagId = tagId;
    }

}
