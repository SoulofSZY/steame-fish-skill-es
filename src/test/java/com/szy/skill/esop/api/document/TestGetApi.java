/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: TestGetApi
 * Author:   EDZ
 * Date:     2019/6/3 20:06
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.document;

import com.alibaba.fastjson.JSONObject;
import com.szy.skill.esop.ElasticsearchClient;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

/**
 * 〈一句话功能简述〉<br>
 * 〈根据id查看文档〉
 *
 * @author EDZ
 * @create 2019/6/3
 * @since 1.0.0
 */
public class TestGetApi extends ElasticsearchClient {

    @Test
    public void testGetAPI() {
        //GetResponse response = client.prepareGet(MY_DEFAULT_INDEX, MY_DEFAULT_TYPE, "1").get(TimeValue.timeValueSeconds(60));
        GetResponse response = client.prepareGet("rabbit_audience_index_76", "audience_user", "578258175166386176").get(TimeValue.timeValueSeconds(60));

        if (response.isExists()) {
            System.out.println("GetApi 有此文档：" + response.toString());
        } else {
            System.out.println("GetApi 没有此文档：" + response.toString());
        }

    }

    @Test
    public void testQuery(){
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("rabbit_audience_index_76").setTypes("audience_user");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.mustNot(QueryBuilders.termQuery("_id", "578258175166386176"));
        searchRequestBuilder.setQuery(boolQueryBuilder);
        SearchResponse response = searchRequestBuilder.execute().actionGet();

        System.out.println("--------------------");
        System.out.println(JSONObject.toJSONString(response));
        System.out.println("--------------------");
    }
}