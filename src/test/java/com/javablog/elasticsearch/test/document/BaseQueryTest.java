package com.javablog.elasticsearch.test.document;

import com.alibaba.fastjson.JSON;
import com.javablog.elasticsearch.SearchServiceApplication;
import com.javablog.elasticsearch.document.DocService;
import com.javablog.elasticsearch.query.BaseQuery;
import org.elasticsearch.index.query.Operator;
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
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SearchServiceApplication.class)
@WebAppConfiguration
public class BaseQueryTest {
    private final static Logger log = LoggerFactory.getLogger(BaseQueryTest.class);
    private String indexName = "sms-logs-index";
    private String type = "sms_logs_type";
    @Autowired
    private BaseQuery baseQuery;
    @Autowired
    private DocService docService;

    //查询省份为湖北省的数据
    @Test
    public void testTerm() throws IOException {
        List<Map<String, Object>> list = baseQuery.termQuery(indexName, type, "province", "湖北省");
        System.out.println("term查询数量：" + list.size());
        System.out.println(list);
    }

    ////查询省份为湖北省或北京的数据
    @Test
    public void testTerms() throws IOException {
        List<Map<String, Object>> list1 = baseQuery.termsQuery(indexName, type, "province", "湖北省","北京");
        System.out.println("terms查询数量：" + list1.size());
        System.out.println(list1);
    }

    //查询全部
    @Test
    public void testMatchsAll() throws IOException {
        baseQuery.queryAll(indexName, type);
    }

    @Test
    public void testQueryMatch() throws IOException {
        baseQuery.queryMatch(indexName,type,"smsContent","收货安装");
    }

    @Test
    public void testMatchWithOperate() throws IOException {
        baseQuery.queryMatchWithOperate(indexName, type, "smsContent", "中国 健康", Operator.AND);
        baseQuery.queryMatchWithOperate(indexName, type, "smsContent", "中国 健康", Operator.OR);
    }

    @Test
    public void testQueryMulitMatch() throws IOException {
        baseQuery.queryMulitMatch(indexName,type,"北京","smsContent","province");
    }

    @Test
    public void testMatchPhrase() throws IOException, InterruptedException {
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
        smsSendLog5.setSmsContent("java is my favourite programming language, and I also think spark is a very good big data system..");
        docService.add(indexName,type, JSON.toJSONString(smsSendLog5),"200");
        smsSendLog5.setSmsContent("java spark very related, because scala is spark's programming language and scala is also based on jvm like java.");
        docService.add(indexName,type, JSON.toJSONString(smsSendLog5),"300");
        //不是实时刷新，不休眠一下可能查不出来
        Thread.sleep(3000l);
        baseQuery.queryMatchPhrase(indexName,type,"smsContent","java spark");
    }

    @Test
    public void testIdsQuery() throws IOException, InterruptedException {
        baseQuery.idsQuery(indexName,type,"1","2","3");
    }

    @Test
    public void testPrefixQuery() throws IOException, InterruptedException {
        baseQuery.prefixQuery(indexName,type,"corpName","中国");
    }

    @Test
    public void testfuzzyQuery() throws IOException, InterruptedException {
        baseQuery.fuzzyQuery(indexName,type,"corpName","中国联动");
    }

    @Test
    public void testWildCardQuery() throws IOException, InterruptedException {
        baseQuery.wildCardQuery(indexName,type,"corpName","中国*");
        baseQuery.wildCardQuery(indexName,type,"corpName","学?思");
    }

    @Test
    public void testRangeCardQuery() throws IOException {
        baseQuery.rangeQuery(indexName,type,"replyTotal",1,20);
    }

    @Test
    public void testRegexpQuery() throws IOException {
        // 查找长号码1069开头的短信
        String regex = "1069[0-9].+";
        baseQuery.regexpQuery(indexName,type,"longCode",regex);
    }

    @Test
    public void testScrollQuery() throws IOException {
        baseQuery.scrollQuery(indexName,type);
    }

//    @Test
//    public void testMoreLikeThisQuery() throws IOException {
//        // 查找长号码1069开头的短信
//        String[] fields =new String []{"corpName"};
//        String[] likeTexts =new String []{"移动"};
//        baseQuery.moreLikeThisQuery(indexName,type,fields,likeTexts);
//    }
}
