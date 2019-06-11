/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: JoiningQueries
 * Author:   EDZ
 * Date:     2019/6/11 17:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.query;

import com.szy.skill.esop.ElasticsearchClient;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.join.query.HasChildQueryBuilder;
import org.elasticsearch.join.query.HasParentQueryBuilder;
import org.elasticsearch.join.query.JoinQueryBuilders;
import org.elasticsearch.join.query.ParentIdQueryBuilder;
import org.elasticsearch.search.sort.SortMode;
import org.junit.Test;

/**
 * 〈一句话功能简述〉<br>
 * 〈关联查询〉
 *
 * @author EDZ
 * @create 2019/6/11
 * @since 1.0.0
 */
public class JoiningQueries extends ElasticsearchClient {


    /**
     * {"query":{"nested":{"query":{"bool":{"must":[{"term":{"obj1.job":{"value":"coder","boost":1.0}}}],"adjust_pure_negative":true,"boost":1.0}},"path":"obj1","ignore_unmapped":false,"score_mode":"none","boost":1.0}}}
     */
    @Test
    public void test4NestedQuery() {
        NestedQueryBuilder queryBuilder = QueryBuilders.nestedQuery("obj1", QueryBuilders.boolQuery().must(QueryBuilders.termQuery("obj1.job", "coder")), ScoreMode.None);
        searchQueryActuator(queryBuilder);
    }

    /**
     * {"query":{"has_child":{"query":{"term":{"user":{"value":"cba","boost":1.0}}},"type":"my_child","score_mode":"none","min_children":0,"max_children":2147483647,"ignore_unmapped":false,"boost":1.0}}}
     */
    @Test
    public void test4HashChild() {
        HasChildQueryBuilder hasChildQueryBuilder = JoinQueryBuilders.hasChildQuery("my_child", QueryBuilders.termQuery("user", "cba"), ScoreMode.None);
        searchQueryActuator(hasChildQueryBuilder);
    }

    /**
     * {"query":{"has_parent":{"query":{"term":{"user":{"value":"nba","boost":1.0}}},"parent_type":"my_parent","score":false,"ignore_unmapped":false,"boost":1.0}}}
     */
    @Test
    public void test4HasParent() {
        HasParentQueryBuilder queryBuilder = JoinQueryBuilders.hasParentQuery("my_parent", QueryBuilders.termQuery("user", "nba"), false);
        searchQueryActuator(queryBuilder);
    }

    /**
     * {"query":{"parent_id":{"type":"my_child","id":"1","ignore_unmapped":false,"boost":1.0}}}
     */
    @Test
    public void test4ParentId() {
        ParentIdQueryBuilder queryBuilder = JoinQueryBuilders.parentId("my_child", "1");
        searchQueryActuator(queryBuilder);
    }
}