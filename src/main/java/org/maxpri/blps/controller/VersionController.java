package org.maxpri.blps.controller;

import org.maxpri.blps.model.dto.messages.ArticleVersion;
import org.maxpri.blps.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("/api/v1/version")
public class VersionController {
    private final VersionService versionService;

    @Autowired
    public VersionController(VersionService versionService) {
        this.versionService = versionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ArticleVersion>> getAllVersions(@PathVariable Long id) throws ExecutionException, InterruptedException, TimeoutException {
        return ResponseEntity.ok(versionService.getAllVersions(id));
    }

    @GetMapping("/rollback/{id}")
    public ResponseEntity<?> rollbackToVersion(@PathVariable Long id) throws ExecutionException, InterruptedException, TimeoutException {
        versionService.rollbackToVersion(id);
        return ResponseEntity.ok().build();
    }
}
