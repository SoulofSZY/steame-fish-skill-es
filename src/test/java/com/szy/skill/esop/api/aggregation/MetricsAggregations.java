/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: MetricsAggregations
 * Author:   EDZ
 * Date:     2019/6/12 18:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.aggregation;

import com.szy.skill.esop.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.Min;
import org.elasticsearch.search.aggregations.metrics.MinAggregationBuilder;
import org.junit.Test;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author EDZ
 * @create 2019/6/12
 * @since 1.0.0
 */
@Slf4j
public class MetricsAggregations extends ElasticsearchClient {

    /**
     * 聚集是对查询结果聚集  且 单独一个doc 包装聚集信息结果
     * {"aggregations":{"age_min":{"min":{"field":"age"}}}}
     */
    @Test
    public void test4MinAggregation() {
        MinAggregationBuilder aggregationBuilder = AggregationBuilders.min("age_min").field("age");

        // 此处的setSize(0) 设置不返回具体hits内容
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(MY_DEFAULT_INDEX).setTypes(MY_DEFAULT_TYPE).setSize(0);
        searchRequestBuilder.addAggregation(aggregationBuilder);
        log.info(searchRequestBuilder.toString());
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        Min age_min = response.getAggregations().get("age_min");

        log.info("最小年龄：{}", age_min.getValue());
    }

    @Test
    public void test4MaxAggregation() {
        MinAggregationBuilder aggregationBuilder = AggregationBuilders.min("age_max").field("age");
        // 此处的setSize(0) 设置不返回具体hits内容
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(MY_DEFAULT_INDEX).setTypes(MY_DEFAULT_TYPE).setSize(0);
        searchRequestBuilder.addAggregation(aggregationBuilder);
        log.info(searchRequestBuilder.toString());
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        Min age_min = response.getAggregations().get("age_max");

        log.info("最大年龄：{}", age_min.getValue());
    }
}