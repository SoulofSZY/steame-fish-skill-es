/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: UpdateAPI
 * Author:   EDZ
 * Date:     2019/6/4 20:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.document;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.szy.skill.esop.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author EDZ
 * @create 2019/6/4
 * @since 1.0.0
 */
@Slf4j
public class UpdateAPI extends ElasticsearchClient {

    @Test
    public void test4UpdateRequest() throws IOException, InterruptedException, ExecutionException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(DEFAULT_INDEX).type(DEFAULT_TYPE).id("2")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("user", "zzr")
                        .endObject()
                );
        log.info(updateRequest.toString());
        client.update(updateRequest).get();
    }

    /**
     * 已有字段会覆盖 没有的字段会新添加
     *
     * @throws IOException
     */
    @Test
    public void test4PrepareUpdate() throws IOException {
        client.prepareUpdate(DEFAULT_INDEX, DEFAULT_TYPE, "2")
                .setDoc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("remark", "szy")
                        .endObject()
                ).get();
    }

    /**
     * 无则插入  有则更新
     *
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test4Upsert() throws IOException, InterruptedException, ExecutionException {
        IndexRequest indexRequest = new IndexRequest(DEFAULT_INDEX, DEFAULT_TYPE, "1")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "Joe Smith")
                        .field("gender", "male")
                        .endObject());
        UpdateRequest updateRequest = new UpdateRequest(DEFAULT_INDEX, DEFAULT_TYPE, "1")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("gender", "female")
                        .endObject())
                .upsert(indexRequest);
        client.update(updateRequest).get();
    }

}