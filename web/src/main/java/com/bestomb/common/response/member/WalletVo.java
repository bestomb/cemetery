package com.bestomb.common.response.member;

import java.math.BigDecimal;

/***
 * 钱包信息
 * @author qfzhang
 *
 */
public class WalletVo {
	
	private Integer integral; // 会员积分
    private BigDecimal tradingAmount; // 交易币
    
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
}