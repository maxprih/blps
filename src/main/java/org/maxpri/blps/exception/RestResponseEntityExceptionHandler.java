package org.maxpri.blps.exception;

import org.maxpri.blps.model.dto.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author max_pri
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ArticleNotFoundException.class)
    private ResponseEntity<MessageResponse> handleArticleNotFound(ArticleNotFoundException e) {
        MessageResponse response = new MessageResponse("Не найдена статья с данным ID: " + e.getArticleId());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = TagNotFoundException.class)
    private ResponseEntity<MessageResponse> handleTagNotFound(TagNotFoundException e) {
        MessageResponse response = new MessageResponse("Не найден тэг с данным ID: " + e.getTagId());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ImageNotFoundException.class)
    private ResponseEntity<MessageResponse> handleImageNotFound(ImageNotFoundException e) {
        MessageResponse response = new MessageResponse("Не найдена картинка с данным ID: " + e.getImageId());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
