package com.bestomb.entity;

import java.math.BigDecimal;

public class SellGoods {

    // 会员编号
    private Integer memberId;
    // 商品编号
    private String goodsId;
    // 商品发布价格
    private BigDecimal price;
    // 商品发布数量
    private Integer count;


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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}