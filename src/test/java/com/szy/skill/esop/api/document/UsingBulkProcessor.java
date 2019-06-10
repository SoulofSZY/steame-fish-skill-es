/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: UsingBulkProcessor
 * Author:   EDZ
 * Date:     2019/6/5 20:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.document;

import com.szy.skill.esop.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author EDZ
 * @create 2019/6/5
 * @since 1.0.0
 */
@Slf4j
public class UsingBulkProcessor extends ElasticsearchClient {

    private BulkProcessor bulkProcessor;

    @Test
    public void test4BulkProcessor() {
        bulkProcessor = BulkProcessor.builder(client,
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId,
                                           BulkRequest request) {

                    }

                    @Override
                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          BulkResponse response) {
                        log.info(response + "");
                    }

                    @Override
                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          Throwable failure) {

                    }
                })
                .setBulkActions(1000)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(1)
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();

        addBulkItems();
    }

    private void addBulkItems() {
        bulkProcessor.add(new DeleteRequest(MY_DEFAULT_INDEX, MY_DEFAULT_TYPE, "3"))
                .add(new DeleteRequest(MY_DEFAULT_INDEX, MY_DEFAULT_TYPE, "4"));
    }

    @Override
    public void tearDown() throws InterruptedException, Exception {
        bulkProcessor.awaitClose(10, TimeUnit.SECONDS);
        super.tearDown();
    }
}