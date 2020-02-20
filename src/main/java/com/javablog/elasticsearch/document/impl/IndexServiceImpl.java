package com.javablog.elasticsearch.document.impl;

import com.javablog.elasticsearch.document.IndexService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service("indexService")
public class IndexServiceImpl implements IndexService {
    private final static Logger log = LoggerFactory.getLogger(IndexServiceImpl.class);
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     *  创建索引
     * @param index       索引名称
     * @param type        索引类型
     * @param request     创建索引的REQUEST
     * @throws IOException
     */
    @Override
    public void createIndex(String index,String type,CreateIndexRequest request) throws IOException {
        log.info("source:" + request.toString());
        if (!existsIndex(index)) {
            CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            System.out.println(response.toString());
            log.info("索引创建结查：" + response.isAcknowledged());
        } else {
            log.warn("索引：{}，已经存在，不能再创建。", index);
        }
    }

    /**
     * 删除索引
     * @param index 索引名称
     * @throws IOException
     */
    @Override
    public void deleteIndex(String index) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(index);
        if (restHighLevelClient.indices().exists(getIndexRequest)) {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
            log.info("source:" + deleteIndexRequest.toString());
            restHighLevelClient.indices().delete(deleteIndexRequest);
        }
    }

    /**
     * 判断索引是否存在
     * @param index
     * @return
     * @throws IOException
     */
    public boolean existsIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        log.info("source:" + request.toString());
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        log.debug("existsIndex: " + exists);
        return exists;
    }

}
