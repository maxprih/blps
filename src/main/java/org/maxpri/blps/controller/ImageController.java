package org.maxpri.blps.controller;

import org.maxpri.blps.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ArticleService articleService;

    @Autowired
    public ImageController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{filename}")
    public ResponseEntity<String> getUrl(@PathVariable String filename) throws ExecutionException, InterruptedException, TimeoutException {
        return ResponseEntity.ok(articleService.getUrlForImage(filename));
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<String> deleteImage(@PathVariable String filename){
        articleService.deleteImageByName(filename);
        return ResponseEntity.ok().build();
    }
}
