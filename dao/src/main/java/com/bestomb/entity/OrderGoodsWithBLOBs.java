package com.bestomb.entity;

public class OrderGoodsWithBLOBs extends OrderGoods {
    
	private String images;
    private String description;

    public String getImages() {
        return images;
    }
    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}