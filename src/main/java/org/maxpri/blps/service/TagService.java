package org.maxpri.blps.service;

import org.maxpri.blps.model.dto.TagDto;
import org.maxpri.blps.model.dto.request.CreateTagRequest;
import org.maxpri.blps.model.entity.Tag;
import org.maxpri.blps.repsitory.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author max_pri
 */
@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag createTag(CreateTagRequest createTagRequest) {
        Tag tag = Tag.builder()
                .name(createTagRequest.getName())
                .build();
        return tagRepository.save(tag);
    }

    public List<TagDto> getAllTags() {
        return tagRepository.findAllTagDtos();
    }
}
