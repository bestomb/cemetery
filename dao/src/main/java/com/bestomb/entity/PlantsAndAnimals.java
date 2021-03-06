package com.bestomb.entity;

import java.math.BigDecimal;

public class PlantsAndAnimals {
    private String id;

    private String goodsIdByPerson;

    private String name;

    private BigDecimal price;

    private String images;

    private String modelId;

    private Integer type;

    private Integer grouping;

    private Integer phase;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGoodsIdByPerson() {
        return goodsIdByPerson;
    }

    public void setGoodsIdByPerson(String goodsIdByPerson) {
        this.goodsIdByPerson = goodsIdByPerson;
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

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGrouping() {
        return grouping;
    }

    public void setGrouping(Integer grouping) {
        this.grouping = grouping;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}