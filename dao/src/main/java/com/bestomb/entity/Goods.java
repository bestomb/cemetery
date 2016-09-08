package com.bestomb.entity;

public class Goods implements Cloneable
{
    private String id;
    private String name;
    private Double price;
    private Integer belongs;
    private Integer type;
    private String modelId;
    private Integer createTime;
    
    public Goods clone(){
    	Goods o = null;
        try{
            o = (Goods)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return o;
    }
    
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
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getBelongs() {
        return belongs;
    }
    public void setBelongs(Integer belongs) {
        this.belongs = belongs;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getModelId() {
        return modelId;
    }
    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }
    public Integer getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}