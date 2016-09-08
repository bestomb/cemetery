package com.bestomb.entity;

public class OrderGoods {
    
	private String id;
    private String goodsId;
    private String name;
    private Double price;
    private String orderId;
    private Integer belongs;
    private Integer type;
    private String modelId;
    private Integer count;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    public String getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getModelId() {
        return modelId;
    }
    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public Integer getBelongs() {
		return belongs;
	}
	public void setBelongs(Integer belongs) {
		this.belongs = belongs;
	}
	public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
}