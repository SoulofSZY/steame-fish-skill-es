package com.szy.skill.esop.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName: ElasticsearchConfiguration
 * @Description: es搜索引擎配置
 * @author: 杨乐平
 * @date: 2018年9月4日 下午7:56:01
 */
@Configuration
public class ElasticsearchConfiguration implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConfiguration.class);
    private static final String NODE_DELIMITER = ",";
    /**
     * 节点集合
     */
    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;

    /**
     * 集群名称
     */
    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;

    /**
     * 集群密码
     */
    @Value("${spring.data.elasticsearch.pwd:}")
    private String esUserNamePwd;

    private TransportClient client;


    @Override
    public void destroy() throws Exception {
        try {
            LOGGER.info("Closing elasticSearch client");
            if (client != null) {
                client.close();
            }
        } catch (final Exception e) {
            LOGGER.error("Error closing ElasticSearch client: ", e);
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildClient();
    }

    @Override
    public TransportClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    protected void buildClient() throws IOException, InterruptedException, ExecutionException {
        try {
            TransportClient preBuiltTransportClient = getClient();
            if (!"".equals(clusterNodes)) {
                for (String nodes : clusterNodes.split(NODE_DELIMITER)) {
                    String[] addressPortArr = nodes.split(":");
                    String Address = addressPortArr[0];
                    Integer port = Integer.valueOf(addressPortArr[1]);
                    preBuiltTransportClient.addTransportAddress(new TransportAddress(InetAddress.getByName(Address), port));
                }
                client = preBuiltTransportClient;
            }

        } catch (UnknownHostException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 初始化默认的client
     */
    private TransportClient getClient() {
        Builder builder = Settings.builder();
        builder.put("cluster.name", clusterName).put("client.transport.sniff", false);

        if (!StringUtils.isEmpty(esUserNamePwd)) {
            builder.put("xpack.security.user", esUserNamePwd);
            Settings settings = builder.build();
            client = new PreBuiltTransportClient(settings);
        } else {
            Settings settings = builder.build();
            client = new PreBuiltTransportClient(settings);
        }
        return client;
    }
}
