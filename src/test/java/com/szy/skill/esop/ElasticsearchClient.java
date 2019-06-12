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
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br>
 * 〈作为一个外部访问者，请求ES的集群，对于集群而言，它是一个外部因素〉
 *
 * @author EDZ
 * @create 2019/6/3
 * @since 1.0.0
 */
@Slf4j
public class ElasticsearchClient {

    private static final String MY_CLUSTER_NAME = "my-application";
    protected static final String MY_DEFAULT_INDEX = "twitter";
    protected static final String MY_DEFAULT_TYPE = "_doc";
    protected static final String MY_IP = "39.108.179.100";
    protected static final int MY_PORT = 9300;


    private static final String RP_CLUSTER_NAME = "***";
    protected static final String RP_DEFAULT_INDEX = "**";
    protected static final String RP_DEFAULT_TYPE = "**";
    private static final String RP_IP = "*****";
    private static final int RP_PORT = 9300;


    protected TransportClient client;

    @Before
    public void setUp() throws UnknownHostException {
        Settings esSettings = Settings.builder()
                // 设置集群名
                .put("cluster.name", MY_CLUSTER_NAME)
                //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                .put("client.transport.sniff", false)
                .build();

        client = new PreBuiltTransportClient(esSettings)
                //:   39.108.179.100:9300
                .addTransportAddress(new TransportAddress(InetAddress.getByName(MY_IP), MY_PORT));
        System.out.println("ElasticsearchClient 连接成功");
    }


    @After
    public void tearDown() throws Exception {
        Optional.ofNullable(client).ifPresent(client -> {
            client.close();
        });
    }

    protected void println(SearchResponse searchResponse) {
        PrintUtils.println(searchResponse);
    }

    protected void searchQueryActuator(QueryBuilder... queryBuilder) {
        Arrays.stream(queryBuilder).forEach(qb->log.info(qb.toString()));
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(MY_DEFAULT_INDEX).setTypes(MY_DEFAULT_TYPE);
        Arrays.stream(queryBuilder).forEach(qb->searchRequestBuilder.setQuery(qb));
        log.info(searchRequestBuilder.toString());
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        SearchHit[] hits = response.getHits().getHits();
        Arrays.stream(hits).forEach(hit -> log.info(hit.toString()));
    }

    protected void searchQueryActuator(AggregationBuilder aggregationBuilder){
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch().setIndices(MY_DEFAULT_INDEX).setTypes(MY_DEFAULT_TYPE);
        searchRequestBuilder.addAggregation(aggregationBuilder);
        log.info(searchRequestBuilder.toString());
        SearchResponse response = searchRequestBuilder.execute().actionGet();
    }
}