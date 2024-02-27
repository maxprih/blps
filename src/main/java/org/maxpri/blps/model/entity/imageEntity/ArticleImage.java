package org.maxpri.blps.model.entity.imageEntity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author max_pri
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "article_image")
public class ArticleImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "article_id")
    private Long articleId;
}
