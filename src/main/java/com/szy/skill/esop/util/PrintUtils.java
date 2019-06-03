/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: PrintUtils
 * Author:   EDZ
 * Date:     2019/6/3 17:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

/**
 * 〈一句话功能简述〉<br> 
 * 〈json〉
 *
 * @author EDZ
 * @create 2019/6/3
 * @since 1.0.0
 */
public class PrintUtils {

    public static void println(SearchResponse searchResponse) {
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println(JSON.toJSONString(searchHit.getSourceAsMap(), SerializerFeature.PrettyFormat));
        }
    }
}