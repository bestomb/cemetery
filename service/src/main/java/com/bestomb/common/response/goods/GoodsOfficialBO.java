package com.bestomb.common.response.goods;

import java.math.BigDecimal;

public class GoodsOfficialBO {
    private String id;

    private String name;

    private BigDecimal price;

    private String images;

    private Integer type;

    private Integer lifecycle;

    private String description;

    private String extendAttribute;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtendAttribute() {
        return extendAttribute;
    }

    public void setExtendAttribute(String extendAttribute) {
        this.extendAttribute = extendAttribute;
    }

    public Integer getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(Integer lifecycle) {
        this.lifecycle = lifecycle;
    }
}