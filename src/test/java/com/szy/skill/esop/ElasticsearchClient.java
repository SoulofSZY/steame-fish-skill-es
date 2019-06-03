/**
 * Copyright (C), 2014-2019, 深圳兔展智能科技有限公司
 * FileName: ElasticsearchClient
 * Author:   EDZ
 * Date:     2019/6/3 17:20
 * Description: 作为一个外部访问者，请求ES的集群，对于集群而言，它是一个外部因素
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szy.skill.esop;

import com.szy.skill.esop.util.PrintUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈作为一个外部访问者，请求ES的集群，对于集群而言，它是一个外部因素〉
 *
 * @author EDZ
 * @create 2019/6/3
 * @since 1.0.0
 */
public class ElasticsearchClient {

    private static final String CLUSTER_NAME = "my-application";

    protected static final String DEFAULT_INDEX = "twitter";
    protected static final String DEFAULT_TYPE = "tweet";

    protected TransportClient client;

    @Before
    public void setUp() throws UnknownHostException {
        Settings esSettings = Settings.builder()
                // 设置集群名
                .put("cluster.name", CLUSTER_NAME)
                //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                .put("client.transport.sniff", false)
                .build();

        client = new PreBuiltTransportClient(esSettings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("39.108.179.100"), 9300));
        System.out.println("ElasticsearchClient 连接成功");
    }


    @After
    public void tearDown() {
        Optional.ofNullable(client).ifPresent(client -> {
            client.close();
        });
    }

    protected void println(SearchResponse searchResponse) {
        PrintUtils.println(searchResponse);
    }
}