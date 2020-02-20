package com.javablog.elasticsearch.test.document;

import com.alibaba.fastjson.JSON;
import com.javablog.elasticsearch.SearchServiceApplication;
import com.javablog.elasticsearch.query.HighLightQuery;
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
public class HightLightQueryTest {
    private String indexName = "sms-logs-index";
    private String type = "sms_logs_type";

    @Autowired
    private HighLightQuery hightLightQuery;
    @Autowired
    private DocService docService;

    @Test
    public void testhightLightQuery() throws IOException {
        hightLightQuery.hightLightQuery(indexName,type,"smsContent","中国苹果");
    }

    @Test
    public void testHightLightQueryByFragment() throws IOException, InterruptedException {
        //fragment-size 指定高亮字段最大字符长度
        SmsSendLog smsSendLog5 = new SmsSendLog();
        smsSendLog5.setMobile("13600000088");
        smsSendLog5.setCorpName("中国移动");
        smsSendLog5.setCreateDate(new Date());
        smsSendLog5.setSendDate(new Date());
        smsSendLog5.setIpAddr("10.126.2.8");
        smsSendLog5.setLongCode("10690000998");
        smsSendLog5.setReplyTotal(60);
        smsSendLog5.setProvince("湖北省");
        smsSendLog5.setOperatorId(1);
        smsSendLog5.setSmsContent("新京报快讯 7月16日，国家发改委网站发布《关于印发的通知》（下称《通知》），该《通知》表示，要完善国有企业退出机制。推动国有“僵尸企业”破产退出。对符合破产等退出条件的国有企业，各相关方不得以任何方式阻碍其退出，防止形成“僵尸企业”。不得通过违规提供政府补贴、贷款等方式维系“僵尸企业”生存，有效解决国有“僵尸企业”不愿退出的问题。国有企业退出时，金融机构等债权人不得要求政府承担超出出资额之外的债务清偿责任。《通知》还称，完善特殊类型国有企业退出制度。针对全民所有制企业、厂办集体企业存在的出资人已注销、工商登记出资人与实际控制人不符、账务账册资料严重缺失等问题，明确市场退出相关规定，加快推动符合条件企业退出市场，必要时通过强制清算等方式实行强制退出。\n" +
                "\n" +
                "《通知》表示，要建立市场主体退出预警机制。强化企业信息披露义务。提高企业财务和经营信息透明度，强化信息披露义务主体对信息披露真实性、准确性、完整性的责任要求。公众公司应依法向公众披露财务和经营信息。非公众公司应及时向股东和债权人披露财务和经营信息。鼓励非公众公司特别是大型企业集团、国有企业参照公众公司要求公开相关信息。强化企业在陷入财务困境时及时向股东、债权人等利益相关方的信息披露义务。");
        docService.add(indexName,type, JSON.toJSONString(smsSendLog5),"220");
        Thread.sleep(2000);
        hightLightQuery.hightLightQueryByFragment(indexName,type,20);
    }

    @Test
    public void testHightLightQueryByNumOfFragments() throws IOException {
        hightLightQuery.hightLightQueryByNumOfFragments(indexName,type,20,2);
    }

    @Test
    public void testHightLightNoMatchSize() throws IOException {
        hightLightQuery.hightLightNoMatchSize(indexName,type,20,2,150);
    }
}
