package org.maxpri.blps.repository.elasticRepository;

import org.maxpri.blps.model.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author max_pri
 */
@Repository
public interface ArticleNameRepository extends ElasticsearchRepository<Article, Long> {
    @Query("{\"query\":{\"fuzzy\":{\"name\":{\"value\":\"?0\",\"fuzziness\":\"AUTO\"}}}}\n")
    Page<Article> findByNameContaining(String name, Pageable pageable);
}
