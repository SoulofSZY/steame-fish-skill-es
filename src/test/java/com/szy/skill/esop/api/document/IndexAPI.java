/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: IndexAPI
 * Author:   EDZ
 * Date:     2019/6/3 17:57
 * Description: 插入记录
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szy.skill.esop.ElasticsearchClient;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈插入记录〉
 *
 * @author EDZ
 * @create 2019/6/3
 * @since 1.0.0
 */
public class IndexAPI extends ElasticsearchClient {

    /**
     * 使用Json串 来构造文档内容
     *
     * @throws Exception
     */
    @Test
    public void testForUseStr() throws Exception {
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2019-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        IndexResponse re = client.prepareIndex("twitter", "tweet", "1")
                .setSource(json, XContentType.JSON)
                .get();
        System.out.println("testForUseStr twitter 创建成功");

    }

    /**
     * 使用map来构造文档内容
     *
     * @throws Exception
     */
    @Test
    public void testForUseMap() throws Exception {

        Map<String, Object> json = new HashMap<>();
        json.put("user", "szy");
        json.put("postDate", "2017-10-10");
        json.put("message", "trying out Elasticsearch");

        IndexResponse response = client.prepareIndex("twitter", "tweet", String.valueOf(2))
                .setSource(json)
                .get();

        System.out.println(response.getResult());

        System.out.println("testForUseMap twitter 创建成功");
    }

    /**
     * 使用elasticsearch官方提供的json构造器来构造文档内容
     *
     * @throws Exception
     */
    @Test
    public void testForUseXContentBuilder() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("user", "shd")
                .field("postDate", new Date())
                .field("age", 10)
                .field("gender", "male")
                .field("message", "trying out Elasticsearch 123")
                .endObject();
        IndexResponse response = client.prepareIndex("twitter", "tweet", "3")
                .setSource(builder)
                .get();

        System.out.println(JSONObject.toJSONString(response));

        System.out.println("testForUseXContentBuilder twitter 创建成功");
    }

}