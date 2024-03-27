package org.maxpri.blps.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.maxpri.blps.model.entity.Article;
import org.maxpri.blps.repository.elasticRepository.ArticleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author max_pri
 */
@Service
public class SearchService {
    private final ElasticsearchClient elasticsearchClient;
    private final ArticleNameRepository articleNameRepository;

    @Autowired
    public SearchService(ElasticsearchClient elasticsearchClient, ArticleNameRepository articleNameRepository) {
        this.elasticsearchClient = elasticsearchClient;
        this.articleNameRepository = articleNameRepository;
    }

    public Page<Article> search(String name, Pageable pageable) {
        Page<Article> articlePage = articleNameRepository.findByNameContaining(name, pageable);
        List<Article> articles = articlePage.getContent();
        return new PageImpl<>(articles, pageable, articlePage.getTotalElements());
    }

//    public List<ArticleSearchDto> findByNameFuzzy(String name) throws IOException {
//        Supplier<Query> supplier = ElasticUtils.createSupplierQuery(name);
//        var res = elasticsearchClient
//                .search(s->s.index("article").query(supplier.get()).size(30), Article.class);
//        return res.hits().hits().stream().map(Hit::source).map(article -> new ArticleSearchDto(article.getId(), article.getName(), article.getLastModified())).collect(Collectors.toList());
//    }
//
//    public List<ArticleSearchDto> findByNameWildcard(String name) throws IOException {
//        Supplier<Query> supplier = ElasticUtils.createSupplierWildcardQuery(name);
//        var res = elasticsearchClient
//                .search(s->s.index("article").query(supplier.get()).size(30), Article.class);
//        return res.hits().hits().stream().map(Hit::source).map(article -> new ArticleSearchDto(article.getId(), article.getName(), article.getLastModified())).collect(Collectors.toList());
//    }
}
