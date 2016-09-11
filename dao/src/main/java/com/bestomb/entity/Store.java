package com.bestomb.entity;

public class Store {
	
    private Integer id;
    private Integer memberId;
    private String backpackGoodsId;
    private String goodsId;
    private Integer count;
    
    public Store(){}
    
    public Store(Integer memberId){
    	this.memberId = memberId;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getMemberId() {
        return memberId;
    }
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public String getBackpackGoodsId() {
        return backpackGoodsId;
    }
    public void setBackpackGoodsId(String backpackGoodsId) {
        this.backpackGoodsId = backpackGoodsId == null ? null : backpackGoodsId.trim();
    }
    public String getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
}