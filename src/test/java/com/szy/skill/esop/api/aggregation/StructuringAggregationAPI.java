/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: StructuringAggregationAPI
 * Author:   EDZ
 * Date:     2019/6/12 16:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.aggregation;

import com.alibaba.fastjson.JSONObject;
import com.szy.skill.esop.ElasticsearchClient;
import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
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
public class StructuringAggregationAPI extends ElasticsearchClient {

    @Test
    public void test4StructuringAggregation() {

        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("user_name").field("gender");

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(MY_DEFAULT_INDEX).setTypes(MY_DEFAULT_TYPE)
                .addAggregation(aggregationBuilder);

        log.info(searchRequestBuilder.toString());

        SearchResponse response = searchRequestBuilder.execute().actionGet();

        log.info(JSONObject.toJSONString(response.getHits().getHits()));
        Terms agg =  response.getAggregations().get("user_name");

        log.info("");
    }
}