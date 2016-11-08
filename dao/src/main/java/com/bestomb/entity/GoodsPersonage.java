package com.bestomb.entity;

import java.math.BigDecimal;

public class GoodsPersonage {
    private String id;

    private BigDecimal price;

    private Integer repertory;//库存

    private Integer memberId;

    private String plantsAndAnimalsId;

    public GoodsPersonage() {
    }

    public GoodsPersonage(SellGoods sellGoods) {
        this.memberId = sellGoods.getMemberId();
        this.plantsAndAnimalsId = sellGoods.getGoodsId();
        this.price = sellGoods.getPrice();
        this.repertory = sellGoods.getCount();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getRepertory() {
        return repertory;
    }

    public void setRepertory(Integer repertory) {
        this.repertory = repertory;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPlantsAndAnimalsId() {
        return plantsAndAnimalsId;
    }

    public void setPlantsAndAnimalsId(String plantsAndAnimalsId) {
        this.plantsAndAnimalsId = plantsAndAnimalsId == null ? null : plantsAndAnimalsId.trim();
    }
}