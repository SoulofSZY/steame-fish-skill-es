/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: MultiGetAPI
 * Author:   EDZ
 * Date:     2019/6/4 20:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.document;

import com.szy.skill.esop.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.junit.Test;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 * 根据多个index条件
 *
 * @author EDZ
 * @create 2019/6/4
 * @since 1.0.0
 */
@Slf4j
public class MultiGetAPI extends ElasticsearchClient {

    @Test
    public void test4MultiGet() {
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                .add(DEFAULT_INDEX, DEFAULT_TYPE, "1", "2")
                .add(DEFAULT_INDEX, DEFAULT_TYPE, "2")
                .add(DEFAULT_INDEX, DEFAULT_TYPE, "3", "4")
                .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                log.info(response.getSourceAsString());
            }
        }
    }
}