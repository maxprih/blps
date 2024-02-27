package org.maxpri.blps.model.entity.articleEntity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author max_pri
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(columnDefinition="text")
    private String body;

    @Column(name = "preview_text")
    private String previewText;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "is_rejected")
    private Boolean isRejected;

    @ManyToMany
    @JoinTable(
            name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
}
