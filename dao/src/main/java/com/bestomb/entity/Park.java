package com.bestomb.entity;

public class Park {
    private String id;

    private String cemeteryId;

    private Integer growingDays;

    private Integer lifeValue;

    private String goodsId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCemeteryId() {
        return cemeteryId;
    }

    public void setCemeteryId(String cemeteryId) {
        this.cemeteryId = cemeteryId == null ? null : cemeteryId.trim();
    }

    public Integer getGrowingDays() {
        return growingDays;
    }

    public void setGrowingDays(Integer growingDays) {
        this.growingDays = growingDays;
    }

    public Integer getLifeValue() {
        return lifeValue;
    }

    public void setLifeValue(Integer lifeValue) {
        this.lifeValue = lifeValue;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }
}