package com.bestomb.entity;

public class GoodsOfficialWithBLOBs extends GoodsOfficial {
    private String description;

    private String extendAttribute;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getExtendAttribute() {
        return extendAttribute;
    }

    public void setExtendAttribute(String extendAttribute) {
        this.extendAttribute = extendAttribute == null ? null : extendAttribute.trim();
    }
}