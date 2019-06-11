/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: CompoundQueries
 * Author:   EDZ
 * Date:     2019/6/11 16:31
 * Description: 组合查询
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.query;

import com.szy.skill.esop.ElasticsearchClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

/**
 * 〈一句话功能简述〉<br>
 * 〈组合查询〉
 *
 * @author EDZ
 * @create 2019/6/11
 * @since 1.0.0
 */
public class CompoundQueries extends ElasticsearchClient {


    /**
     * {"query":{"constant_score":{"filter":{"term":{"remark":{"value":"szy","boost":1.0}}},"boost":2.0}}}
     * <p>
     * 该查询 给查询结果指定固定的分数
     */
    @Test
    public void test4ConstantScoreQuery() {
        ConstantScoreQueryBuilder queryBuilder = QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("remark", "szy")).boost(2.0f);
        searchQueryActuator(queryBuilder);
    }

    /**
     * {"query":{"bool":{"must":[{"terms":{"gender":["female"],"boost":1.0}}],"adjust_pure_negative":true,"boost":1.0}}}
     * {"query":{"bool":{"must":[{"terms":{"gender":["female"],"boost":1.0}},{"range":{"age":{"from":20,"to":null,"include_lower":true,"include_upper":true,"boost":1.0}}}],"adjust_pure_negative":true,"boost":1.0}}}
     * {"query":{"bool":{"must":[{"terms":{"gender":["female"],"boost":1.0}},{"range":{"age":{"from":20,"to":null,"include_lower":true,"include_upper":true,"boost":1.0}}}],"must_not":[{"term":{"message":{"value":"hello","boost":1.0}}}],"adjust_pure_negative":true,"boost":1.0}}}
     * {"query":{"bool":{"must":[{"terms":{"gender":["female"],"boost":1.0}},{"range":{"age":{"from":20,"to":null,"include_lower":true,"include_upper":true,"boost":1.0}}}],"filter":[{"match":{"remark":{"query":"szy","operator":"OR","prefix_length":0,"max_expansions":50,"fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","auto_generate_synonyms_phrase_query":true,"boost":1.0}}}],"must_not":[{"term":{"message":{"value":"hello","boost":1.0}}}],"should":[{"term":{"user":{"value":"szy","boost":1.0}}}],"adjust_pure_negative":true,"boost":1.0}}}
     */
    @Test
    public void test4BoolQuery() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termsQuery("gender", "female"));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("age").gte(20));
        boolQueryBuilder.mustNot(QueryBuilders.termQuery("message", "hello"));
        boolQueryBuilder.should(QueryBuilders.termQuery("user", "szy"));
        boolQueryBuilder.filter(QueryBuilders.matchQuery("remark","szy"));
        searchQueryActuator(boolQueryBuilder);
    }

}