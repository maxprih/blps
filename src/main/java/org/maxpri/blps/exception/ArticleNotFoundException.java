package org.maxpri.blps.exception;

import lombok.Getter;

/**
 * @author max_pri
 */
@Getter
public class ArticleNotFoundException extends RuntimeException {
    private final Long articleId;

    public ArticleNotFoundException(Long articleId) {
        this.articleId = articleId;
    }
}
