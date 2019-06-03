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

import com.szy.skill.esop.ElasticsearchClient;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.common.unit.TimeValue;
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
        GetResponse response = client.prepareGet(DEFAULT_INDEX, DEFAULT_TYPE, "1").get(TimeValue.timeValueSeconds(60));

        if (response.isExists()) {
            System.out.println("GetApi 有此文档：" + response.toString());
        } else {
            System.out.println("GetApi 没有此文档：" + response.toString());
        }

    }
}