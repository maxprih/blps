package org.maxpri.blps.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.search.Hit;
import org.maxpri.blps.model.dto.ArticleSearchDto;
import org.maxpri.blps.model.entity.Article;
import org.maxpri.blps.utils.ElasticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author max_pri
 */
@Service
public class SearchService {
    private final ElasticsearchClient elasticsearchClient;

    @Autowired
    public SearchService(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    public List<ArticleSearchDto> findByNameFuzzy(String name) throws IOException {
        Supplier<Query> supplier = ElasticUtils.createSupplierQuery(name);
        var res = elasticsearchClient
                .search(s->s.index("article").query(supplier.get()).size(30), Article.class);
        return res.hits().hits().stream().map(Hit::source).map(article -> new ArticleSearchDto(article.getId(), article.getName(), article.getLastModified())).collect(Collectors.toList());
    }

    public List<ArticleSearchDto> findByNameWildcard(String name) throws IOException {
        Supplier<Query> supplier = ElasticUtils.createSupplierWildcardQuery(name);
        var res = elasticsearchClient
                .search(s->s.index("article").query(supplier.get()).size(30), Article.class);
        return res.hits().hits().stream().map(Hit::source).map(article -> new ArticleSearchDto(article.getId(), article.getName(), article.getLastModified())).collect(Collectors.toList());
    }
}
