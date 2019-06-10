/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: TermLevelQueries
 * Author:   EDZ
 * Date:     2019/6/10 19:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.query;

import com.sun.org.apache.bcel.internal.generic.FADD;
import com.szy.skill.esop.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.*;
import org.junit.Test;

import java.util.Arrays;

/**
 * 〈一句话功能简述〉<br>
 * 〈术语检索〉
 * 完全匹配
 *
 * @author EDZ
 * @create 2019/6/10
 * @since 1.0.0
 */
@Slf4j
public class TermLevelQueries extends ElasticsearchClient {


    /**
     * {
     * "query": {
     * "term": {
     * "user": {
     * "boost": 1.0,
     * "value": "szy"
     * }
     * }
     * }
     * }
     */
    @Test
    public void test4TermQuery() {
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("user", "szy");
        searchQueryActuator(queryBuilder);
    }


    /**
     * {"query":{"terms":{"user":["szy","zzr"],"boost":1.0}}}
     * <p>
     * 字段值 在数组中
     */
    @Test
    public void test4TermsQuery() {
        TermsQueryBuilder queryBuilder = QueryBuilders.termsQuery("user", "szy", "zzr");
        searchQueryActuator(queryBuilder);
    }

    /**
     * {"query":{"range":{"age":{"from":10,"to":20,"include_lower":true,"include_upper":false,"boost":1.0}}}}
     */
    @Test
    public void test4RangeQuery() {
        RangeQueryBuilder queryBuilder = QueryBuilders.rangeQuery("age").from(10).to(20).includeLower(true).includeUpper(false);
        searchQueryActuator(queryBuilder);
    }

    /**
     * {"query":{"range":{"age":{"from":20,"to":30,"include_lower":true,"include_upper":false,"boost":1.0}}}}
     */
    @Test
    public void test4RangeQuery2() {
        RangeQueryBuilder queryBuilder = QueryBuilders.rangeQuery("age").gte(20).lt(30);
        searchQueryActuator(queryBuilder);
    }

    /**
     * {"query":{"exists":{"field":"remark","boost":1.0}}}
     */
    @Test
    public void test4ExistsQuery() {
        ExistsQueryBuilder queryBuilder = QueryBuilders.existsQuery("remark");
        searchQueryActuator(queryBuilder);
    }

    /**
     * {"query":{"prefix":{"user":{"value":"s","boost":1.0}}}}
     */
    @Test
    public void test4PrefixQuery() {
        PrefixQueryBuilder queryBuilder = QueryBuilders.prefixQuery("user", "s");
        searchQueryActuator(queryBuilder);
    }


    /**
     * 通配符 {"query":{"wildcard":{"user":{"wildcard":"s*z?","boost":1.0}}}}
     * *  >=0
     * ?  >=1
     */
    @Test
    public void test4WildcardQuery() {
        WildcardQueryBuilder queryBuilder = QueryBuilders.wildcardQuery("user", "s*z?");
        searchQueryActuator(queryBuilder);
    }

    /**
     * {"query":{"regexp":{"user":{"value":"[szy]+","flags_value":65535,"max_determinized_states":10000,"boost":1.0}}}}
     */
    @Test
    public void test4RegexpQuery() {
        RegexpQueryBuilder queryBuilder = QueryBuilders.regexpQuery("user", "[szy]+");
        searchQueryActuator(queryBuilder);
    }

    /**
     * {"query":{"fuzzy":{"user":{"value":"sszy","fuzziness":"AUTO","prefix_length":0,"max_expansions":50,"transpositions":true,"boost":1.0}}}}
     */
    @Test
    public void test4FuzzyQuery() {
        FuzzyQueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("user", "sszy");
        searchQueryActuator(queryBuilder);
    }


    /**
     * {"query":{"ids":{"values":["1","3"],"boost":1.0}}}
     */
    @Test
    public void test4IdsQuery() {
        IdsQueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds("1", "3");
        searchQueryActuator(queryBuilder);
    }
}