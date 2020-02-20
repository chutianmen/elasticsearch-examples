package com.javablog.elasticsearch.test.document;

import com.alibaba.fastjson.JSON;
import com.javablog.elasticsearch.SearchServiceApplication;
import com.javablog.elasticsearch.query.FilterQuery;
import com.javablog.elasticsearch.document.DocService;
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
public class FilterQueryTest {
    private final static Logger log = LoggerFactory.getLogger(BaseQueryTest.class);
    private String indexName = "sms-logs-index";
    private String type = "sms_logs_type";
    @Autowired
    private FilterQuery filterQuery;
    @Autowired
    private DocService docService;

    @Test
    public void testFilterInBoolQuery() throws IOException {
        filterQuery.filterInBoolQuery(indexName,type);
    }

    @Test
    public void testRangeCardQuery() throws IOException {
        filterQuery.rangeQuery(indexName,type,"fee",1,5);
    }

    @Test
    public void testExistQuery() throws IOException, InterruptedException {
        SmsSendLog smsSendLog = new SmsSendLog();
        smsSendLog.setMobile("13998000000");
        smsSendLog.setCorpName("北京海联力捷汽车销售服务有限公司");
        smsSendLog.setCreateDate(new Date());
        smsSendLog.setSendDate(new Date());
        smsSendLog.setIpAddr("10.126.2.9");
        smsSendLog.setLongCode("10690000988");
        //去掉某个值
//        smsSendLog.setReplyTotal(10);
        smsSendLog.setSmsContent("感谢您致电北京海联力捷汽车销售服务有限公司 我们以客户至上的理念，" +
                "为您提供专业的技术服务，全体员工期待您的光临！");
        smsSendLog.setProvince("北京");
        smsSendLog.setOperatorId(1);
        docService.add(indexName,type, JSON.toJSONString(smsSendLog),"300");
        Thread.sleep(2000l);
        filterQuery.existQuery(indexName,type,"replyTotal");
    }

    @Test
    public void testTypeQuery() throws IOException {
        filterQuery.typeQuery(type);
    }
}
