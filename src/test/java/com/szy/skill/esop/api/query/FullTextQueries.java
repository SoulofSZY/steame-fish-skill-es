/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: FullTextQueries
 * Author:   EDZ
 * Date:     2019/6/10 17:30
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.query;

import com.szy.skill.esop.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.junit.Test;

import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈全文检索〉
 *
 * @author EDZ
 * @create 2019/6/10
 * @since 1.0.0
 */
@Slf4j
public class FullTextQueries extends ElasticsearchClient {

    /**
     * {
     * "match" : {
     * "message" : {
     * "query" : "Elasticsearch",
     * "operator" : "OR",
     * "prefix_length" : 0,  // 不能被模糊搜做的初始字符数
     * "max_expansions" : 50,
     * "fuzzy_transpositions" : true, // 模糊查询
     * "lenient" : false,
     * "zero_terms_query" : "NONE", // 停词器
     * "auto_generate_synonyms_phrase_query" : true,  // 同义词
     * "boost" : 1.0
     * }
     * }
     * }
     */
    @Test
    public void test4MatchQuery_Operator() {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("message", "Elasticsearch 123").operator(Operator.AND);
        //QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("message", "123");
        searchQueryActuator(queryBuilder);
    }

    /**
     * 表示用来在查询时如果数据类型不匹配且无法转换时会报错。如果设置成 true 会忽略错误
     */
    @Test
    public void test4MatchQuery_Lenient() {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("age", "a").lenient(false);
        //QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("message", "123");
        searchQueryActuator(queryBuilder);
    }


    /**
     * {
     *   "multi_match" : {
     *     "query" : "Elasticsearch",
     *     "fields" : [
     *       "message^1.0",
     *       "remark^1.0"
     *     ],
     *     "type" : "best_fields",
     *     "operator" : "OR",
     *     "slop" : 0,
     *     "prefix_length" : 0,
     *     "max_expansions" : 50,
     *     "zero_terms_query" : "NONE",
     *     "auto_generate_synonyms_phrase_query" : true,
     *     "fuzzy_transpositions" : true,
     *     "boost" : 1.0
     *   }
     * }
     */
    @Test
    public void test4MultiMatchQuery() {
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("Elasticsearch", "message", "remark");
        searchQueryActuator(queryBuilder);
    }

    



}