package org.maxpri.blps.model.entity.articleEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Column(columnDefinition="text")
    private String body;

    @Column(name = "preview_text")
    private String previewText;

    @Column(name = "last_modified")
    @Field(type = FieldType.Date, name = "last_modified")
    private LocalDateTime lastModified;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "is_rejected")
    private Boolean isRejected;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToMany
    @JoinTable(
            name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
}
