package com.javablog.elasticsearch.test.document;

import com.alibaba.fastjson.JSON;
import com.javablog.elasticsearch.SearchServiceApplication;
import com.javablog.elasticsearch.document.DocService;
import com.javablog.elasticsearch.query.AggregationQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SearchServiceApplication.class)
@WebAppConfiguration
public class AggregationQueryTest {
    private final static Logger log = LoggerFactory.getLogger(AggregationQueryTest.class);
    private String indexName = "sms-logs-index";
    private String type = "sms_logs_type";
    @Autowired
    private AggregationQuery aggregationQuery;
    @Autowired
    private DocService docService;

    @Test
    public void testAvgAggregations() throws IOException {
        aggregationQuery.AvgAggregations(indexName,type,"replyTotal");
    }

    //其作用是对选择字段先执行类似sql中的distinct操作，去掉集合中的重复项，然后统计排重后的集合长度。
    //总共有多少不同的值  相当于SQL中的 select count(distinct clusterId) from table
    @Test
    public void testCardinalityAggregations() throws IOException {
        //统计去重后的公司数量
        aggregationQuery.cardinalityAggregations(indexName,type,"corpName");
        //统计去重后的手机数量
        aggregationQuery.cardinalityAggregations(indexName,type,"mobile");
    }

    @Test
    public void testDateHistogramAggregation() throws IOException, ParseException, InterruptedException {
        for (int i=0;i<5;i++) {
            SmsSendLog smsSendLog5 = new SmsSendLog();
            smsSendLog5.setMobile("13600000000");
            smsSendLog5.setCorpName("中国移动");
            smsSendLog5.setCreateDate(addDate(i+1));
            smsSendLog5.setSendDate(addDate(i+1));
            smsSendLog5.setIpAddr("10.126.2.8");
            smsSendLog5.setLongCode("10650000998");
            smsSendLog5.setReplyTotal(60);
            smsSendLog5.setSmsContent("【北京移动】尊敬的客户137****0000，5月话费账单已送达您的139邮箱，" +
                    "点击查看账单详情 http://y.10086.cn/\n" +
                    " 回Q关闭通知，关注“中国移动139邮箱”微信随时查账单【中国移动 139邮箱】");
            smsSendLog5.setProvince("湖北省");
            smsSendLog5.setOperatorId(1);
            int k = 50 + i;
            docService.add(indexName, type, JSON.toJSONString(smsSendLog5), String.valueOf(k));
        }
        Thread.sleep(2000);
        System.out.println("======================= 统计开始 ==================================");
        aggregationQuery.dateHistogramAggregation(indexName, type, "createDate");
        System.out.println("======================= 统计结束 ==================================");
    }

    @Test
    public void testDateRangeAggregation() throws IOException, ParseException, InterruptedException {
        System.out.println("======================= 统计开始 dateRangeAggregation ==================================");
        aggregationQuery.dateRangeAggregation(indexName, type, "createDate");
        System.out.println("======================= 统计结束 dateRangeAggregation==================================");
    }

    @Test
    public void testFilterAggregation() throws IOException, ParseException, InterruptedException {
        System.out.println("======================= 统计开始 filterAggregation==================================");
        aggregationQuery.filterAggregation(indexName,type,"province","湖北省");
        System.out.println("======================= 统计结束 filterAggregation==================================");
    }

    @Test
    public void testHistogramAggregation() throws IOException, ParseException, InterruptedException {
        System.out.println("======================= 统计开始 HistogramAggregation==================================");
        aggregationQuery.histogramAggregation(indexName,type,"fee",5);
        System.out.println("======================= 统计结束 HistogramAggregation==================================");
    }

    @Test
    public void testHistogramDateAggregation() throws IOException, ParseException, InterruptedException {
        System.out.println("======================= 统计开始 HistogramAggregation==================================");
        aggregationQuery.histogramDateAggregation(indexName,type,"createDate",1);
        System.out.println("======================= 统计结束 HistogramAggregation==================================");
    }

    @Test
    public void testExtendedStatsAggregation() throws IOException {
        System.out.println("======================= 统计开始 ExtendedStatsAggregation==================================");
        aggregationQuery.extendedStatsAggregation(indexName,type,"replyTotal");
        System.out.println("======================= 统计结束 ExtendedStatsAggregation==================================");
    }

    @Test
    public void testTermsAggregation() throws IOException {
        System.out.println("======================= 统计开始 TermsAggregation==================================");
        aggregationQuery.termsAggregation(indexName,type,1458332215006l,1659035753905l);
        System.out.println("======================= 统计结束 TermsAggregation==================================");
    }

    @Test
    public void testGeoDistanceAggregation() throws IOException {
        System.out.println("======================= 统计开始 GeoDistanceAggregation==================================");
        aggregationQuery.geoDistanceAggregation("cn_large_cities","city_type");
        System.out.println("======================= 统计结束 GeoDistanceAggregation==================================");
    }

    private Date addDate(long day) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
        Date date = new Date();
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        Date newDate = new Date(time);
        return newDate;
    }


}
