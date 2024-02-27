package org.maxpri.blps.model.entity.articleEntity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author max_pri
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}
