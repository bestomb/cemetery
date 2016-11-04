package com.bestomb.entity;

import java.math.BigDecimal;

public class MemberAccount {
    
	private Integer memberId; // 会员ID

    private Long mobileNumber; // 手机号

    private String loginPassword; // 密码

    private String nickName; // 昵称

    private Integer inviterId; // 邀请者编号

    private Integer integral; // 会员积分

    private BigDecimal tradingAmount; // 交易币

    private String tradingPassword; // 支付密码

    private Integer constructionCount; // 可建设陵园总数

    private Integer createTime; // 创建时间

    /***
     * 判断编辑会员资料的请求参数是否为空
     * @return
     */
    public boolean isEmptyEditRequest(){
    	if (mobileNumber!=null) { return false; }
    	if (loginPassword!=null) { return false; }
    	if (nickName!=null) { return false; }
    	if (inviterId!=null) { return false; }
    	if (tradingPassword!=null) { return false; }
    	return true;
    }
    
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Integer getInviterId() {
        return inviterId;
    }

    public void setInviterId(Integer inviterId) {
        this.inviterId = inviterId;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public BigDecimal getTradingAmount() {
        return tradingAmount;
    }

    public void setTradingAmount(BigDecimal tradingAmount) {
        this.tradingAmount = tradingAmount;
    }

    public String getTradingPassword() {
        return tradingPassword;
    }

    public void setTradingPassword(String tradingPassword) {
        this.tradingPassword = tradingPassword == null ? null : tradingPassword.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getConstructionCount() {
        return constructionCount;
    }

    public void setConstructionCount(Integer constructionCount) {
        this.constructionCount = constructionCount;
    }
}