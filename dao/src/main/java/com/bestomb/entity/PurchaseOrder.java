package com.bestomb.entity;

/***
 * 会员订单表
 * @author qfzhang
 *
 */
public class PurchaseOrder {
    
	private String id;
    private String orderNumber;
    private Integer status;
    private Integer memberId;
    private Integer createTime;
    private Integer type;
    private Double price;
    
    public PurchaseOrder(){}
    
    public PurchaseOrder(String orderNumber, Integer memberId, Double price){
    	this.orderNumber = orderNumber;
    	this.status = 1; // 默认未支付
    	this.memberId = memberId;
    	this.createTime = (int) (System.currentTimeMillis() / 1000); // 当前系统时间绝对秒数
    	this.type = 1; // 默认会员购物订单
    	this.price = price;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getMemberId() {
        return memberId;
    }
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public Integer getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
}