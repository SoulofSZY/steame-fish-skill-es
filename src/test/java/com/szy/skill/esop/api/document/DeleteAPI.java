/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: DeleteAPI
 * Author:   EDZ
 * Date:     2019/6/3 20:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.api.document;

import com.alibaba.fastjson.JSONObject;
import com.szy.skill.esop.ElasticsearchClient;
import org.elasticsearch.action.delete.DeleteResponse;
import org.junit.Test;

/**
 * 〈一句话功能简述〉<br>
 * 〈根据文档id 删除〉
 *
 * @author EDZ
 * @create 2019/6/3
 * @since 1.0.0
 */
public class DeleteAPI extends ElasticsearchClient {

    @Test
    public void test4DeleteAPI() {
        DeleteResponse response = client.prepareDelete(MY_DEFAULT_INDEX, MY_DEFAULT_TYPE, "1").get("60s");
        System.out.print("删除成功:" + JSONObject.toJSONString(response));
    }
}