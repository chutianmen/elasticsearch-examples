package com.javablog.elasticsearch.test.document;

import com.alibaba.fastjson.JSON;
import com.javablog.elasticsearch.SearchServiceApplication;
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
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SearchServiceApplication.class)
@WebAppConfiguration
public class DeleteByQueryTest {
    private final static Logger log = LoggerFactory.getLogger(DeleteByQueryTest.class);
    private String indexName = "sms-logs-index";
    private String type = "sms_logs_type";
    @Autowired
    private DocService docService;


    @Test
    public void testAddDatas() throws IOException {
        for (int i=0;i<1000;i++) {
            SmsSendLog smsSendLog = new SmsSendLog();
            smsSendLog.setMobile("13800000000");
            smsSendLog.setCorpName("贝壳网");
            smsSendLog.setCreateDate(new Date());
            smsSendLog.setSendDate(new Date());
            smsSendLog.setIpAddr("10.126.2.9");
            smsSendLog.setLongCode("10690000988");
            smsSendLog.setReplyTotal(10);
            smsSendLog.setSmsContent("【贝壳找房】择水而居，北京669万高品质两居 http://xxxxxxx，退订回T");
            smsSendLog.setProvince("北京");
            smsSendLog.setOperatorId(1);
            smsSendLog.setFee(3);
            int k = 1000 + i;
            docService.add(indexName, type, JSON.toJSONString(smsSendLog), String.valueOf(k));
        }
    }

    @Test
    public void testDeleteByQuery() throws IOException {
        docService.deleteByQuery(indexName, type, "corpName", "贝壳网");
    }

}
