package com.bestomb.entity;

public class Backpack {
	
    private String id;
    private Integer memberId;
    private String goodsId;
    private Integer count;

    public Backpack(){};
    
    public Backpack(Store store){
    	this.memberId = store.getMemberId();
    	this.goodsId = store.getBackpackGoodsId();
    	this.count = store.getCount();
    };
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
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
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
}