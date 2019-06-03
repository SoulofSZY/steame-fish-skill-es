/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: DeleteByQueryAPI
 * Author:   EDZ
 * Date:     2019/6/3 20:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.document;

import com.alibaba.fastjson.JSONObject;
import com.szy.skill.esop.ElasticsearchClient;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.junit.Test;

/**
 * 〈一句话功能简述〉<br>
 * 〈根据查询条件  删除〉
 *
 * @author EDZ
 * @create 2019/6/3
 * @since 1.0.0
 */
public class DeleteByQueryAPI extends ElasticsearchClient {

    @Test
    public void test4DeleteByQuery() {

        BulkByScrollResponse response = new DeleteByQueryRequestBuilder(client, DeleteByQueryAction.INSTANCE)
                // 全文检索
                .filter(QueryBuilders.matchQuery("message", "Elasticsearch"))
                // 指定 index
                .source(DEFAULT_INDEX)
                .get();
        long deleted = response.getDeleted();
        System.out.print(JSONObject.toJSONString(response));
    }

    @Test
    public void test4DeleteByQueryAysnc() throws InterruptedException{
        new DeleteByQueryRequestBuilder(client, DeleteByQueryAction.INSTANCE)
                .filter(QueryBuilders.matchQuery("message", "Elasticsearch"))
                .source(DEFAULT_INDEX)
                .execute(new ActionListener<BulkByScrollResponse>() {
                    @Override
                    public void onResponse(BulkByScrollResponse response) {
                        long deleted = response.getDeleted();
                        System.out.print(deleted);
                    }
                    @Override
                    public void onFailure(Exception e) {
                        // Handle the exception
                    }
                });

            Thread.sleep(1000);
    }
}