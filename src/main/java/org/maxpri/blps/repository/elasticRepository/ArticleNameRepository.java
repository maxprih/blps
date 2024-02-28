package org.maxpri.blps.repository.elasticRepository;

import org.maxpri.blps.model.entity.articleEntity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author max_pri
 */
@Repository
public interface ArticleNameRepository extends ElasticsearchRepository<Article, Long> {
    List<Article> findByNameContaining(String name);
}
