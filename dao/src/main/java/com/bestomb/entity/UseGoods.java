package com.bestomb.entity;

/***
 * 背包商品使用
 * @author qfzhang
 *
 */
public class UseGoods {
    
	// 会员编号
	private Integer memberId;
	// 商品编号
    private String goodsId;
    // 陵园编号
    private String cemeteryId;
	
    public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getCemeteryId() {
		return cemeteryId;
	}
	public void setCemeteryId(String cemeteryId) {
		this.cemeteryId = cemeteryId;
	}
    
}