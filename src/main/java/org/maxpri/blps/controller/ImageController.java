package org.maxpri.blps.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.maxpri.blps.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение картинки по ID")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }
}
