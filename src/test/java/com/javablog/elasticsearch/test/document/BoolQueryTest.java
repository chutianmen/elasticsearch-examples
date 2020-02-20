package com.javablog.elasticsearch.test.document;

import com.alibaba.fastjson.JSON;
import com.javablog.elasticsearch.SearchServiceApplication;
import com.javablog.elasticsearch.query.BoolQuery;
import com.javablog.elasticsearch.document.DocService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SearchServiceApplication.class)
@WebAppConfiguration
public class BoolQueryTest {
    private String indexName = "sms-logs-index";
    private String type = "sms_logs_type";

    @Autowired
    private BoolQuery boolQuery;
    @Autowired
    private DocService docService;

    @Test
    public void testBoolQuery() throws IOException {
        boolQuery.boolQuery(indexName,type);
    }

    /**
     * 它接受一个positive查询和一个negative查询。只有匹配了positive查询的文档才会被包含到结果集中，
     * 但是同时匹配了negative查询的文档会被降低其相关度，通过将文档原本的_score和negative_boost参数进行相乘来得到新的_score。
     * 因此，negative_boost参数必须小于1.0。在上面的例子中，任何包含了指定负面词条的文档的_score都会是其原本_score的一半。
     * @throws IOException
     */
    @Test
    public void testBoostingQuery() throws InterruptedException, IOException {
        SmsSendLog smsSendLog = new SmsSendLog();
        smsSendLog.setMobile("13800000000");
        smsSendLog.setCorpName("天猫商城");
        smsSendLog.setCreateDate(new Date());
        smsSendLog.setSendDate(new Date());
        smsSendLog.setIpAddr("10.126.2.9");
        smsSendLog.setLongCode("10690000988");
        smsSendLog.setReplyTotal(10);
        smsSendLog.setSmsContent("【天猫商城】苹果手机双11大优惠，原价8000块钱的苹果土豪金IPNONE手机，惊爆降价2000元，速来抢购。你也可以拥有苹果");
        smsSendLog.setProvince("北京");
        smsSendLog.setOperatorId(1);
        docService.add(indexName,type, JSON.toJSONString(smsSendLog),"17");

        smsSendLog.setMobile("13700000001");
        smsSendLog.setProvince("上海");
        smsSendLog.setSmsContent("苹果，是水果中的一种，是蔷薇科亚科属植物，其树木为落叶乔木。营养价值很高，富含矿物质和维生素，" +
                "含钙量丰富，有助于代谢掉体内多余盐分，酸可代谢热量，防止下半身肥胖。是人们经常食用的水果之一。");
        docService.add(indexName,type, JSON.toJSONString(smsSendLog),"18");
        Thread.sleep(3000l);
        //都可以查出来，只是SCORE值减少，可以通过SCORE值来去掉排名在后面的
        boolQuery.boostingQuery(indexName,type);
    }
}
