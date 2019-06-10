/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: BulkAPI
 * Author:   EDZ
 * Date:     2019/6/5 19:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.document;

import com.szy.skill.esop.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *  单个请求的批量操作
 * @author EDZ
 * @create 2019/6/5
 * @since 1.0.0
 */
@Slf4j
public class BulkAPI extends ElasticsearchClient {


    @Test
    public void test4BulkAPI() throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        bulkRequest.add(client.prepareIndex(MY_DEFAULT_INDEX, MY_DEFAULT_TYPE, "3").setSource(
                jsonBuilder().startObject()
                        .field("user", "shd")
                        .field("postDate", new Date())
                        .field("message", "hahahahhahah")
                        .endObject()
        ));

        bulkRequest.add(client.prepareIndex(MY_DEFAULT_INDEX, MY_DEFAULT_TYPE, "4").setSource(
                jsonBuilder().startObject()
                        .field("user", "ljl")
                        .field("postDate", new Date())
                        .field("message", "heiheihei")
                        .endObject()
        ));
        BulkResponse responses = bulkRequest.get();
        if (responses.hasFailures()) {
            log.info(responses+"");
        }

    }

//    bulkRequest.add(client.prepareIndex("twitter", "_doc", "1")
//            .setSource(jsonBuilder()
//                    .startObject()
//                        .field("user", "kimchy")
//                        .field("postDate", new Date())
//            .field("message", "trying out Elasticsearch")
//                    .endObject()
//                  )
//                          );
}