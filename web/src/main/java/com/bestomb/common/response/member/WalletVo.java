package com.bestomb.common.response.member;

/***
 * 钱包信息
 * @author qfzhang
 *
 */
public class WalletVo {
	
	private Integer integral; // 会员积分
    private Double tradingAmount; // 交易币
    
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
}