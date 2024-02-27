package org.maxpri.blps.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.maxpri.blps.model.dto.TagDto;
import org.maxpri.blps.model.dto.request.CreateTagRequest;
import org.maxpri.blps.model.entity.articleEntity.Tag;
import org.maxpri.blps.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("/api/v1/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @Operation(summary = "Создание тэга")
    public ResponseEntity<Tag> createTag(@RequestBody CreateTagRequest createTagRequest) {
        return ResponseEntity.ok(tagService.createTag(createTagRequest));
    }

    @GetMapping
    @Operation(summary = "Получение всех тэгов")
    public ResponseEntity<List<TagDto>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }
}
