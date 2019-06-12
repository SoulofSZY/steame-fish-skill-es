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
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.*;
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
        MaxAggregationBuilder aggregationBuilder = AggregationBuilders.max("age_max").field("age");
        // 此处的setSize(0) 设置不返回具体hits内容
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(MY_DEFAULT_INDEX).setTypes(MY_DEFAULT_TYPE).setSize(0);
        searchRequestBuilder.addAggregation(aggregationBuilder);
        log.info(searchRequestBuilder.toString());
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        Max age_max = response.getAggregations().get("age_max");

        log.info("最大年龄：{}", age_max.getValue());
    }

    /**
     * {"size":0,"aggregations":{"age_sum":{"sum":{"field":"age"}}}}
     */
    @Test
    public void test4SumAggregation() {
        SumAggregationBuilder aggregationBuilder = AggregationBuilders.sum("age_sum").field("age");
        // 此处的setSize(0) 设置不返回具体hits内容
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(MY_DEFAULT_INDEX).setTypes(MY_DEFAULT_TYPE).setSize(0);
        searchRequestBuilder.addAggregation(aggregationBuilder);
        log.info(searchRequestBuilder.toString());
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        Sum age_sum = response.getAggregations().get("age_sum");

        log.info("年龄和：{}", age_sum.getValue());
    }

    /**
     * {"size":0,"aggregations":{"age_avg":{"avg":{"field":"age"}}}}
     */
    @Test
    public void test4AvgAggregation() {
        AvgAggregationBuilder aggregationBuilder = AggregationBuilders.avg("age_avg").field("age");

        // 此处的setSize(0) 设置不返回具体hits内容
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(MY_DEFAULT_INDEX).setTypes(MY_DEFAULT_TYPE).setSize(0);
        searchRequestBuilder.addAggregation(aggregationBuilder);
        log.info(searchRequestBuilder.toString());
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        Avg age_avg = response.getAggregations().get("age_avg");

        log.info("平均值：{}", age_avg.getValue());
    }

    /**
     * {"size":0,"aggregations":{"age_count":{"value_count":{"field":"age"}}}}
     */
    @Test
    public void test4CountAggregation() {
        ValueCountAggregationBuilder aggregationBuilder = AggregationBuilders.count("age_count").field("age");

        // 此处的setSize(0) 设置不返回具体hits内容
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(MY_DEFAULT_INDEX).setTypes(MY_DEFAULT_TYPE).setSize(0);
        searchRequestBuilder.addAggregation(aggregationBuilder);
        log.info(searchRequestBuilder.toString());
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        ValueCount age_count = response.getAggregations().get("age_count");

        log.info("含有age字段的文档数：{}", age_count.getValue());
    }

    /**
     * 该聚合操作 或返回 count/sum/min/max/avg
     * <p>
     * {"size":0,"aggregations":{"age_stats":{"stats":{"field":"age"}}}}
     */
    @Test
    public void test4StatAggregation() {
        StatsAggregationBuilder aggregationBuilder = AggregationBuilders.stats("age_stats").field("age");

        // 此处的setSize(0) 设置不返回具体hits内容
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(MY_DEFAULT_INDEX).setTypes(MY_DEFAULT_TYPE).setSize(0);
        searchRequestBuilder.addAggregation(aggregationBuilder);
        log.info(searchRequestBuilder.toString());
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        Stats age_stats = response.getAggregations().get("age_stats");

        log.info("记录条数：{}", age_stats.getCount());
        log.info("最小年龄：{}", age_stats.getMin());
        log.info("最大年龄：{}", age_stats.getMax());
        log.info("年龄和：{}", age_stats.getSum());
        log.info("平均值：{}", age_stats.getAvg());
    }


    /**
     * {"size":0,"aggregations":{"gender_agg":{"terms":{"field":"gender","size":10,"min_doc_count":1,"shard_min_doc_count":0,"show_term_doc_count_error":false,"order":[{"_count":"desc"},{"_key":"asc"}]},"aggregations":{"top":{"top_hits":{"from":0,"size":3,"version":false,"seq_no_primary_term":false,"explain":false}}}}}}
     */
    @Test
    public void test4TopHitsAggregation(){
       TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("gender_agg").field("gender")
                .subAggregation(AggregationBuilders.topHits("top"));

        // 此处的setSize(0) 设置不返回具体hits内容
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(MY_DEFAULT_INDEX).setTypes(MY_DEFAULT_TYPE).setSize(0);
        searchRequestBuilder.addAggregation(aggregationBuilder);
        log.info(searchRequestBuilder.toString());
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        // sr is here your SearchResponse object
        Terms agg = response.getAggregations().get("gender_agg");

        // For each entry
        for (Terms.Bucket entry : agg.getBuckets()) {
            String key = (String) entry.getKey();                    // bucket key
            long docCount = entry.getDocCount();            // Doc count
            log.info("key [{}], doc_count [{}]", key, docCount);

            // We ask for top_hits for each bucket
            TopHits topHits = entry.getAggregations().get("top");
            for (SearchHit hit : topHits.getHits().getHits()) {
                log.info(" -> id [{}], _source [{}]", hit.getId(), hit.getSourceAsString());
            }
        }

    }

}