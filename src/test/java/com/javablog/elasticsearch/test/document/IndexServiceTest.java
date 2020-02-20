package com.javablog.elasticsearch.test.document;

import com.javablog.elasticsearch.SearchServiceApplication;
import com.javablog.elasticsearch.document.IndexService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SearchServiceApplication.class)
@WebAppConfiguration
public class IndexServiceTest {
    private final static Logger log = LoggerFactory.getLogger(IndexServiceTest.class);
    private String indexName = "sms-logs-index";
    private String type = "sms_logs_type";

    @Autowired
    private IndexService indexService;

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        buildSetting(request);
        buildIndexMapping(request, type);
        indexService.createIndex(indexName,type,request);
    }

    @Test
    public void testDelIndex() throws IOException {
        indexService.deleteIndex(indexName);
    }

    //设置分片
    private void buildSetting(CreateIndexRequest request) {
        request.settings(Settings.builder().put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2));
    }

    /**
     * 生成短信下发表索引结构
     *
     * createDate 创建时间
     * sendDate 发送时间
     * longCode 发送的长号码
     * mobile 下发手机号
     * corpName 发送公司名称
     * smsContent 下发短信内容
     * state 短信下发状态  0 成功 1 失败
     * operatorId  '运营商编号  1 移动 2 联通 3 电信
     * province 省份
     * ipAddr 下发服务器IP地址
     * replyTotal 短信状态报告返回时长（秒）
     * fee 费用
     * @param request
     * @param type
     * @throws IOException
     */
    private void  buildIndexMapping(CreateIndexRequest request, String type) throws IOException {
        XContentBuilder mappingBuilder = JsonXContent.contentBuilder()
                .startObject()
                    .startObject("properties")
                        .startObject("mobile")
                        .field("type", "keyword")
                        .field("index", "true")
                        .endObject()

                        .startObject("createDate")
                        .field("type", "date")
                        .field("index", "true")
                        .endObject()

                        .startObject("sendDate")
                        .field("type", "date")
                        .field("index", "true")
                        .endObject()

                        .startObject("longCode")
                        .field("type", "keyword")
                        .field("index", "true")
                        .endObject()

                        .startObject("corpName")
                        .field("type", "keyword")
                        .field("index", "true")
                        .endObject()

                        .startObject("smsContent")
                        .field("type", "text")
                        .field("index", "true")
                        .field("analyzer", "ik_max_word") // ik_max_word 这个分词器是ik的，可以去github上搜索安装es的ik分词器插件
                        .endObject()

                        .startObject("state")
                        .field("type", "integer")
                        .field("index", "true")
                        .endObject()

                        .startObject("province")
                        .field("type", "keyword")
                        .field("index", "true")
                        .endObject()

                        .startObject("operatorId")
                        .field("type", "integer")
                        .field("index", "true")
                        .endObject()

                        .startObject("ipAddr")
                        .field("type", "ip")
                        .field("index", "true")
                        .endObject()

                        .startObject("replyTotal")
                        .field("type", "integer")
                        .field("index", "true")
                        .endObject()

                        .startObject("fee")
                        .field("type", "integer")
                        .field("index", "true")
                        .endObject()
                     .endObject()
                .endObject();
        request.mapping(type, mappingBuilder);
    }


}
