package com.bestomb.common.response.member;

import java.math.BigDecimal;

/***
 * 会员帐号信息
 * @author qfzhang
 *
 */
public class MemberAccountVo {
	
	private Integer memberId; // 会员编号
    private Long mobileNumber; // 手机号
    private String nickName; // 昵称
    private Integer inviterId; // 邀请者编号
    private Integer integral; // 会员积分
    private BigDecimal tradingAmount; // 交易币
    private Integer constructionCount; // 可建设陵园总数
    private String createTime; // 注册时间
	
    
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public Integer getConstructionCount() {
		return constructionCount;
	}
	public void setConstructionCount(Integer constructionCount) {
		this.constructionCount = constructionCount;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
    
}