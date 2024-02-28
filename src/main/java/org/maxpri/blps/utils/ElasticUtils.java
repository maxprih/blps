package org.maxpri.blps.utils;

import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.WildcardQuery;

import java.util.function.Supplier;

/**
 * @author max_pri
 */
public class ElasticUtils {
    public static Supplier<Query> createSupplierQuery(String name){
        return ()-> Query.of(q->q.fuzzy(createFuzzyQuery(name)));
    }

    public static Supplier<Query> createSupplierWildcardQuery(String name){
        return ()-> Query.of(q->q.wildcard(createWildcardQuery(name)));
    }

    public static FuzzyQuery createFuzzyQuery(String name){
        return new FuzzyQuery.Builder().field("name").value(name).fuzziness("2").build();
    }

    public static WildcardQuery createWildcardQuery(String name){
        return new WildcardQuery.Builder().field("name").value("*" + name.toLowerCase() + "*").build();
    }
}
