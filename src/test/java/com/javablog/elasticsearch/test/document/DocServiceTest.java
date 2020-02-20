package com.javablog.elasticsearch.test.document;

import com.alibaba.fastjson.JSON;
import com.javablog.elasticsearch.SearchServiceApplication;
import com.javablog.elasticsearch.document.DocService;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.tools.Tool;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SearchServiceApplication.class)
@WebAppConfiguration
public class DocServiceTest {
    private final static Logger log = LoggerFactory.getLogger(DocServiceTest.class);
    private String indexName = "sms-logs-index";
    private String type = "sms_logs_type";
    @Autowired
    private DocService docService;

    /**
     * SMS_LOG_SUBMIT表
     * numSeqID 唯一ID
     * createDate 创建时间
     * longCode 发送的长号码
     * mobile 下发手机号
     * corpName 发送公司名称
     * smsContent 下发短信内容
     * state 短信下发状态  0 成功 1 失败
     * operatorId  '运营商编号  1 移动 2 联通 3 电信
     * province 省份
     * ipAddr 下发服务器IP地址
     * replyTotal 短信状态报告返回时长（秒）
     */
    @Test
    public void testAddData() throws IOException {
        int total = 10;
        SmsSendLog smsSendLog = new SmsSendLog();
        smsSendLog.setMobile("13800000000");
        smsSendLog.setCorpName("途虎养车");
        smsSendLog.setCreateDate(new Date());
        smsSendLog.setSendDate(new Date());
        smsSendLog.setIpAddr("10.126.2.9");
        smsSendLog.setLongCode("10690000988");
        smsSendLog.setReplyTotal(10);
        smsSendLog.setSmsContent("【途虎养车】亲爱的张三先生/女士，您在途虎购买的货品(单号TH123456)已到指定安装店多日，" +
                "现需与您确认订单的安装情况，请点击链接按实际情况选择（此链接有效期为72H）。您也可以登录途虎APP进入" +
                "“我的-待安装订单”进行预约安装。若您在服务过程中有任何疑问，请致电400-111-8868向途虎咨询。");
        smsSendLog.setProvince("北京");
        smsSendLog.setOperatorId(1);
        smsSendLog.setFee(3);
        docService.add(indexName,type, JSON.toJSONString(smsSendLog),"1");

        smsSendLog.setMobile("13700000001");
        smsSendLog.setProvince("上海");
        smsSendLog.setSmsContent("【途虎养车】亲爱的刘红先生/女士，您在途虎购买的货品(单号TH1234526)已到指定安装店多日，" +
                "现需与您确认订单的安装情况，请点击链接按实际情况选择（此链接有效期为72H）。您也可以登录途虎APP进入" +
                "“我的-待安装订单”进行预约安装。若您在服务过程中有任何疑问，请致电400-111-8868向途虎咨询。");
        docService.add(indexName,type, JSON.toJSONString(smsSendLog),"2");

        SmsSendLog smsSendLog1 = new SmsSendLog();
        smsSendLog1.setMobile("13100000000");
        smsSendLog1.setCorpName("盒马鲜生");
        smsSendLog1.setCreateDate(new Date());
        smsSendLog1.setSendDate(new Date());
        smsSendLog1.setIpAddr("10.126.2.9");
        smsSendLog1.setLongCode("10660000988");
        smsSendLog1.setReplyTotal(15);
        smsSendLog1.setSmsContent("【盒马】您尾号12345678的订单已开始配送，请在您指定的时间收货不要走开哦~配送员：" +
                "刘三，电话：13800000000");
        smsSendLog1.setProvince("北京");
        smsSendLog1.setOperatorId(2);
        smsSendLog1.setFee(5);
        docService.add(indexName,type, JSON.toJSONString(smsSendLog1),"3");

        smsSendLog1.setMobile("18600000001");
        smsSendLog1.setProvince("上海");
        smsSendLog1.setSmsContent("【盒马】您尾号7775678的订单已开始配送，请在您指定的时间收货不要走开哦~配送员：" +
                "王五，电话：13800000001");
        docService.add(indexName,type, JSON.toJSONString(smsSendLog1),"4");

        SmsSendLog smsSendLog2 = new SmsSendLog();
        smsSendLog2.setMobile("15300000000");
        smsSendLog2.setCorpName("滴滴打车");
        smsSendLog2.setCreateDate(new Date());
        smsSendLog2.setSendDate(new Date());
        smsSendLog2.setIpAddr("10.126.2.8");
        smsSendLog2.setLongCode("10660000988");
        smsSendLog2.setReplyTotal(50);
        smsSendLog2.setSmsContent("【滴滴单车平台】专属限时福利！青桔/小蓝月卡立享5折，特惠畅骑30天。" +
                "戳 https://xxxxxx退订TD");
        smsSendLog2.setProvince("上海");
        smsSendLog2.setOperatorId(3);
        smsSendLog2.setFee(7);
        docService.add(indexName,type, JSON.toJSONString(smsSendLog2),"5");

        smsSendLog2.setMobile("18000000001");
        smsSendLog2.setProvince("湖北省");
        smsSendLog2.setSmsContent("【滴滴单车平台】专属限时福利！青桔/小蓝月卡立享5折，特惠畅骑30天。" +
                "戳 https://xxxxxx退订TD");
        docService.add(indexName,type, JSON.toJSONString(smsSendLog2),"6");

        SmsSendLog smsSendLog3 = new SmsSendLog();
        smsSendLog3.setMobile("13900000000");
        smsSendLog3.setCorpName("招商银行");
        smsSendLog3.setCreateDate(new Date());
        smsSendLog3.setSendDate(new Date());
        smsSendLog3.setIpAddr("10.126.2.8");
        smsSendLog3.setLongCode("10690000988");
        smsSendLog3.setReplyTotal(50);
        smsSendLog3.setSmsContent("【招商银行】尊贵的李四先生,恭喜您获得华为P30 Pro抽奖资格,还可领100元打" +
                "车红包,仅限1天");
        smsSendLog3.setProvince("上海");
        smsSendLog3.setOperatorId(1);
        smsSendLog3.setFee(8);
        docService.add(indexName,type, JSON.toJSONString(smsSendLog3),"7");

        smsSendLog3.setMobile("13990000001");
        smsSendLog3.setProvince("湖北省");
        smsSendLog3.setSmsContent("【招商银行】尊贵的李四先生,恭喜您获得华为P30 Pro抽奖资格,还可领100元打" +
                "车红包,仅限1天");
        docService.add(indexName,type, JSON.toJSONString(smsSendLog3),"8");

        SmsSendLog smsSendLog4 = new SmsSendLog();
        smsSendLog4.setMobile("13700000000");
        smsSendLog4.setCorpName("中国平安保险有限公司");
        smsSendLog4.setCreateDate(new Date());
        smsSendLog4.setSendDate(new Date());
        smsSendLog4.setIpAddr("10.126.2.8");
        smsSendLog4.setLongCode("10690000998");
        smsSendLog4.setReplyTotal(18);
        smsSendLog4.setSmsContent("【中国平安】奋斗的时代，更需要健康的身体。中国平安为您提供多重健康保障，在奋斗之路上为您保驾护航。退订请回复TD");
        smsSendLog4.setProvince("湖北省");
        smsSendLog4.setOperatorId(1);
        smsSendLog4.setFee(5);
        docService.add(indexName,type, JSON.toJSONString(smsSendLog4),"9");

        SmsSendLog smsSendLog5 = new SmsSendLog();
        smsSendLog5.setMobile("13600000000");
        smsSendLog5.setCorpName("中国移动");
        smsSendLog5.setCreateDate(new Date());
        smsSendLog5.setSendDate(new Date());
        smsSendLog5.setIpAddr("10.126.2.8");
        smsSendLog5.setLongCode("10650000998");
        smsSendLog5.setReplyTotal(60);
        smsSendLog5.setSmsContent("【北京移动】尊敬的客户137****0000，5月话费账单已送达您的139邮箱，" +
                "点击查看账单详情 http://y.10086.cn/\n" +
                " 回Q关闭通知，关注“中国移动139邮箱”微信随时查账单【中国移动 139邮箱】");
        smsSendLog5.setProvince("北京");
        smsSendLog5.setOperatorId(1);
        smsSendLog5.setFee(4);

        docService.add(indexName,type, JSON.toJSONString(smsSendLog5),"10");

        SmsSendLog smsSendLog7 = new SmsSendLog();
        smsSendLog7.setMobile("13600000000");
        smsSendLog7.setCorpName("学而思");
        smsSendLog7.setCreateDate(new Date());
        smsSendLog7.setSendDate(new Date());
        smsSendLog7.setIpAddr("10.126.2.8");
        smsSendLog7.setLongCode("10650000998");
        smsSendLog7.setReplyTotal(60);
        smsSendLog7.setSmsContent("【学而思教育】学而思暑期英语班，培训英语CET课程。欢迎至电8899xxxxxxxxx");
        smsSendLog7.setProvince("湖北省");
        smsSendLog7.setOperatorId(1);
        smsSendLog7.setFee(4);
        docService.add(indexName,type, JSON.toJSONString(smsSendLog7),"11");

        SmsSendLog smsSendLog8 = new SmsSendLog();
        smsSendLog8.setMobile("13600000000");
        smsSendLog8.setCorpName("高思");
        smsSendLog8.setCreateDate(new Date());
        smsSendLog8.setSendDate(new Date());
        smsSendLog8.setIpAddr("10.126.2.8");
        smsSendLog8.setLongCode("10650000998");
        smsSendLog8.setReplyTotal(60);
        smsSendLog8.setSmsContent("【高思】高思教育培训根据中小学学生学习难点推出1对1课程 ，详情请见HTTP：//XXXXXXXXXXXXXXXXX");
        smsSendLog8.setProvince("湖北省");
        smsSendLog8.setOperatorId(1);
        smsSendLog8.setFee(4);
        docService.add(indexName,type, JSON.toJSONString(smsSendLog8),"12");
    }

    /**
     * 单个删除
     * @throws IOException
     */
    @Test
    public void testDelDoc() throws IOException {
        //DOCID从增加日志中取或从图形界面中找
        docService.deleteDocWithId(indexName,type,"10");
    }

    /**
     * 删除多个，
     * @throws IOException
     */
    @Test
    public void testBulkDeleteDoc() throws IOException {
        //DOCID从增加日志中取或从Kibana图形界面中找
        String[] docidArr =new String[]{"9","8"};
        docService.bulkDeleteDoc(indexName,type, docidArr);
    }

    /** 注意：
     * 虽然刷新比提交更轻量，但是它依然有消耗。人工刷新在测试写的时有用，但是不要在生产环境中每写一次就执行刷新，
     * 这会影响性能。
     * 相反，你的应用需要意识到ES近实时搜索的本质，并且容忍它。
     */
    /**
     * 不是所有的用户都需要每秒刷新一次。也许你使用ES索引百万日志文件，你更想要优化索引的速度，而不是进实时搜索。
     * 你可以通过修改配置项refresh_interval减少刷新的频率：1
     PUT /my_logs
     {
     "settings": {
     "refresh_interval": "30s" <1>
     }
     }
     */
    @Test
    public void testRefresh() throws IOException {
        long docSize1 = docService.countDoc(indexName, type);
        SmsSendLog smsSendLog5 = new SmsSendLog();
        smsSendLog5.setMobile("13600000000");
        smsSendLog5.setCorpName("中国移动");
        smsSendLog5.setCreateDate(new Date());
        smsSendLog5.setSendDate(new Date());
        smsSendLog5.setIpAddr("10.126.2.8");
        smsSendLog5.setLongCode("10690000998");
        smsSendLog5.setReplyTotal(30);
        smsSendLog5.setSmsContent("【中国移动】尊敬的客户137****0000，您的话费不足10元，为了不影响您的使用请尽快充值。");
        smsSendLog5.setProvince("湖北省");
        smsSendLog5.setOperatorId(1);
        smsSendLog5.setFee(5);
        docService.add(indexName,type, JSON.toJSONString(smsSendLog5),"8");
        long docSize2 = docService.countDoc(indexName, type);
        System.out.println("docSize1:" + docSize1 + " docSize2:" + docSize2 + " 因为没有刷新，docSize1 == docSize2");

        long docSize3 = docService.countDoc(indexName, type);
        SmsSendLog smsSendLog6 = new SmsSendLog();
        smsSendLog6.setMobile("13600000000");
        smsSendLog6.setCorpName("中国移动");
        smsSendLog6.setCreateDate(new Date());
        smsSendLog6.setSendDate(new Date());
        smsSendLog6.setIpAddr("10.126.2.8");
        smsSendLog6.setLongCode("10690000998");
        smsSendLog6.setReplyTotal(60);
        smsSendLog6.setSmsContent("【中国移动】为了答谢老用户，近期展开充话费，送流量的活动，充的越多，送的越多，详情请见http://xxxxxxxxxxxxxxxxxx");
        smsSendLog6.setProvince("湖北省");
        smsSendLog6.setOperatorId(1);
        smsSendLog6.setFee(9);
        docService.add(indexName,type, JSON.toJSONString(smsSendLog6),"9");
        //刷新
        docService.refresh(indexName);
        long docSize4 = docService.countDoc(indexName, type);
        System.out.println("docSize3:" + docSize3 + " docSize4:" + docSize4 + " 因为有刷新，docSize3 =! docSize4");
    }

    @Test
    public void testUpdateDoc() throws IOException, InterruptedException {
        //2.插入一个id为10的文档
        SmsSendLog smsSendLog6 = new SmsSendLog();
        smsSendLog6.setMobile("13200000000");
        smsSendLog6.setCorpName("泰康人寿");
        smsSendLog6.setCreateDate(new Date());
        smsSendLog6.setSendDate(new Date());
        smsSendLog6.setIpAddr("10.126.2.8");
        smsSendLog6.setLongCode("10690000998");
        smsSendLog6.setReplyTotal(10);
        smsSendLog6.setSmsContent("【泰康人寿】邀请函：公司举办客服节活动，免费赠送口腔检查券，以及价值2680元的指纹密码锁，高端候鸟医养结合社区体验券。了解回复Y，退订回T");
        smsSendLog6.setProvince("湖北省");
        smsSendLog6.setOperatorId(1);
        smsSendLog6.setFee(6);
        docService.add(indexName,type, JSON.toJSONString(smsSendLog6),"10");
        Thread.sleep(3000l);
        //3.更新这个文档 age改为18
        Map<String,Object> map =new HashMap<>();
        map.put("province","天津");
        map.put("replyTotal",100);
        UpdateResponse update = docService.update(indexName, type, map, "10");
    }


    //批量增加文档
    @Test
    public void testBuldInsertDoc() throws IOException {
        BulkRequest bulkRequestBuilder = new BulkRequest();
        SmsSendLog smsSendLog5 = new SmsSendLog();
        smsSendLog5.setMobile("13500000000");
        smsSendLog5.setCorpName("京东");
        smsSendLog5.setCreateDate(new Date());
        smsSendLog5.setSendDate(new Date());
        smsSendLog5.setIpAddr("10.126.2.8");
        smsSendLog5.setLongCode("10690000998");
        smsSendLog5.setReplyTotal(30);
        smsSendLog5.setSmsContent("【京东金融】尊敬的用户，您的20元信用卡还款券还未领取，为避免奖励过期造成" +
                "无法领取的情况，请及时领取  （已领请忽略）！回N退订");
        smsSendLog5.setProvince("北京");
        smsSendLog5.setOperatorId(1);
        smsSendLog5.setFee(6);

        SmsSendLog smsSendLog6 = new SmsSendLog();
        smsSendLog6.setMobile("13600000000");
        smsSendLog6.setCorpName("京东");
        smsSendLog6.setCreateDate(new Date());
        smsSendLog6.setSendDate(new Date());
        smsSendLog6.setIpAddr("10.126.2.8");
        smsSendLog6.setLongCode("10690000998");
        smsSendLog6.setReplyTotal(60);
        smsSendLog6.setSmsContent("【京东】PLUS会员上线京典卡、联名卡双卡种！开通京典卡，享10倍返利、" +
                "360元运费券/年等特权；开通联名卡，还可额外享爱奇艺VIP年卡、知乎读书会员。7月8号PLUS DAY，PLUS下单" +
                "还可赢免单，回复BK退订");
        smsSendLog6.setProvince("湖北省");
        smsSendLog6.setOperatorId(1);
        smsSendLog6.setFee(6);

        bulkRequestBuilder.add(new IndexRequest(indexName, type, String.valueOf(100)).source(JSON.toJSONString(smsSendLog5), XContentType.JSON));
        bulkRequestBuilder.add(new IndexRequest(indexName, type, String.valueOf(101)).source(JSON.toJSONString(smsSendLog6), XContentType.JSON));

        BulkResponse bulkResponse = docService.bulkUpdateOrInsertDoc(indexName, type, bulkRequestBuilder);
        BulkItemResponse[] bulkItemResponseArr = bulkResponse.getItems();
        for (BulkItemResponse bulkItemResponse :bulkItemResponseArr) {
            System.out.println("操作是否失败:"+ bulkItemResponse.isFailed() + " 文档版本：" + bulkItemResponse.getVersion());
        }
    }

    //批量更新这个文档
    @Test
    public void testBuldUpdateDoc() throws IOException {
        //5.批量更新这个文档
        BulkRequest bulkRequestBuilder = new BulkRequest();
        for (int i = 100;i <= 101;i++) {
            Map<String,Object> maps =new HashMap<>();
            maps.put("province","天津");
            maps.put("replyTotal",120);
            bulkRequestBuilder.add(new UpdateRequest(indexName, type, String.valueOf(i)).doc(maps));
        }
        BulkResponse bulkResponse = docService.bulkUpdateOrInsertDoc(indexName, type, bulkRequestBuilder);
        BulkItemResponse[] bulkItemResponseArr = bulkResponse.getItems();
        for (BulkItemResponse bulkItemResponse :bulkItemResponseArr) {
            System.out.println("操作是否失败:"+ bulkItemResponse.isFailed() + " 文档版本：" + bulkItemResponse.getVersion());
        }
    }
}
