package com.javablog.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;

@Configuration
public class ElasticsearchConfig {
    private final static Logger log = LoggerFactory.getLogger(ElasticsearchConfig.class);
    private static final String SCHEMA = "http"; // 使用的协议
    private  ArrayList<HttpHost> hostList;
    @Value("${spring.data.elasticsearch.host}")
    private String hosts; // 集群地址，多个用,隔开
    @Value("${spring.data.elasticsearch.port}")
    private int port; // 集群地址，多个用,隔开
    @Value("${spring.data.elasticsearch.connectTimeOut}")
    private int connectTimeOut = 1000; // 连接超时时间
    @Value("${spring.data.elasticsearch.socketTimeOut}")
    private int socketTimeOut = 30000; // 连接超时时间
    @Value("${spring.data.elasticsearch.connectionRequestTimeOut}")
    private int connectionRequestTimeOut = 500; // 获取连接的超时时间
    @Value("${spring.data.elasticsearch.maxConnectNum}")
    private int maxConnectNum = 100; // 最大连接数
    @Value("${spring.data.elasticsearch.maxConnectPerRoute}")
    private int maxConnectPerRoute = 100; // 最大路由连接数
    private RestClientBuilder builder;

    @Bean
    @ConditionalOnMissingBean(RestHighLevelClient.class)
    public RestHighLevelClient client() {
        hostList = new ArrayList<>();
        String[] hostStrs = hosts.split(",");
        for (String host : hostStrs) {
            String server = host;
            hostList.add(new HttpHost(host, port , SCHEMA));
        }
        builder = RestClient.builder(hostList.toArray(new HttpHost[0]));
        setConnectTimeOutConfig();
        setMutiConnectConfig();
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }

    // 异步httpclient的连接延时配置
    public void setConnectTimeOutConfig() {
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(connectTimeOut);
                requestConfigBuilder.setSocketTimeout(socketTimeOut);
                requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
                return requestConfigBuilder;
            }
        });
    }

    // 异步httpclient的连接数配置
    public void setMutiConnectConfig() {
        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.setMaxConnTotal(maxConnectNum);
                httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                return httpClientBuilder;
            }
        });
    }

}