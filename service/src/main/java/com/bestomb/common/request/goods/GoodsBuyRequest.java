package com.bestomb.common.request.goods;

/**
 * 商品购买请求对象
 * Created by jason on 2016-11-01.
 */
public class GoodsBuyRequest {

    //商品来源（官网商铺、会员商铺、动植物园店铺）
    private Integer source;

    //商品编号
    private String goodsId;

    //商品数量
    private Integer count;

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
