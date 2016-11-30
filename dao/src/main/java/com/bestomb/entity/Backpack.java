package com.bestomb.entity;

public class Backpack {
    private String id;

    private Integer memberId;
    private String name;
    private String images;

    private Integer source; // {1：商城（官方）， 2：商城（个人）， 3：动植物园}
    private Integer count = 1;

    private String goodsId;

    public Backpack(){};
    public Backpack(Integer memberId, Integer source, String goodsId){
        this.memberId = memberId;
        this.source = source;
        this.goodsId = goodsId;
    }

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

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}