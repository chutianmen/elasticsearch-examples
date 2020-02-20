package com.javablog.elasticsearch.test.document;

import java.util.Date;

/**
 * SMS_LOG_SUBMIT表
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
 * fee  费用（分）
 */
public class SmsSendLog {
    private Date createDate;
    private Date sendDate;
    private String longCode;
    private String mobile;
    private String corpName;
    private String smsContent;
    private int state;
    private int operatorId;
    private String province;
    private String ipAddr;
    private Integer replyTotal;
    private Integer fee;

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLongCode() {
        return longCode;
    }

    public void setLongCode(String longCode) {
        this.longCode = longCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Integer getReplyTotal() {
        return replyTotal;
    }

    public void setReplyTotal(Integer replyTotal) {
        this.replyTotal = replyTotal;
    }
}
