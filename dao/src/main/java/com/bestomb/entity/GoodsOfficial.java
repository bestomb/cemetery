package com.bestomb.entity;

import java.math.BigDecimal;

public class GoodsOfficial {
    private String id;

    private String name;

    private BigDecimal price;

    private String images;

    private Integer type;

    private Integer lifecycle;

    private Integer secondClassifyId;

    private String secondClassifyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(Integer lifecycle) {
        this.lifecycle = lifecycle;
    }

    public Integer getSecondClassifyId() {
        return secondClassifyId;
    }

    public void setSecondClassifyId(Integer secondClassifyId) {
        this.secondClassifyId = secondClassifyId;
    }

    public String getSecondClassifyName() {
        return secondClassifyName;
    }

    public void setSecondClassifyName(String secondClassifyName) {
        this.secondClassifyName = secondClassifyName;
    }
}