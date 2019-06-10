/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: MatchAllQuery
 * Author:   EDZ
 * Date:     2019/6/10 17:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.query;

import com.szy.skill.esop.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;

import java.util.Arrays;

/**
 * 〈一句话功能简述〉<br>
 * 〈matches all documents, giving them all a _score of 1.0.〉
 *
 * @author EDZ
 * @create 2019/6/10
 * @since 1.0.0
 */
@Slf4j
public class MatchAllQuery extends ElasticsearchClient {

    /**
     * {"query":{"match_all":{"boost":1.0}}}
     */
    @Test
    public void test4MatchAll() {
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        searchQueryActuator(queryBuilder);
    }
}