package com.bestomb.entity;

public class MemberAccount {
    private Integer memberId;

    private Long mobileNumber;

    private String loginPassword;

    private String nickName;

    private Integer inviterId;

    private Integer integral;

    private Double tradingAmount;

    private String tradingPassword;

    private Integer constructionCount;

    private Integer createTime;

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

    public Double getTradingAmount() {
        return tradingAmount;
    }

    public void setTradingAmount(Double tradingAmount) {
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

    public Integer getConstructionCount() {
        return constructionCount;
    }

    public void setConstructionCount(Integer constructionCount) {
        this.constructionCount = constructionCount;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}