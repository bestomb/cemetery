package com.bestomb.entity;

public class GoodsWithBLOBs extends Goods{
	
    private String images;
    private String description;
    
    public GoodsWithBLOBs clone(){
    	GoodsWithBLOBs o = null;
    	try {
    		o = (GoodsWithBLOBs)super.clone();
    		o.images = this.images;
    		o.description = this.description;
		} catch (Exception e) {
		}
    	return o;
    }
    
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