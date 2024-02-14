package org.maxpri.blps.exception;

import lombok.Getter;

/**
 * @author max_pri
 */
public class ArticleNotFoundException extends RuntimeException {
    @Getter
    private Long articleId;

    public ArticleNotFoundException(Long articleId) {
        this.articleId = articleId;
    }
}
