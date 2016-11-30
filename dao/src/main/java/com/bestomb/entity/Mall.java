package com.bestomb.entity;

/***
 * 商城 传输对象
 * @author admin
 *
 */
public class Mall {
    
    // 商城类型（1：官方； 2：个人；3：动植物商城）
	private Integer mallType = 1;
	// 商品编号
	private String goodsId;
	// 会员编号（个人商城）
	private Integer memberId;
	// 商品名称
	private String name;
	// 商品类型
	private Integer type;
	
	public Mall(){}
	public Mall(Integer mallType){
		this.mallType = mallType;
	}
	public Mall(Integer mallType, String goodsId){
		this.mallType = mallType;
		this.goodsId = goodsId;
	}
	
	public Integer getMallType() {
		return mallType;
	}
	public void setMallType(Integer mallType) {
		this.mallType = mallType;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}