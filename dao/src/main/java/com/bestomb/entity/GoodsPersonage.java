package com.bestomb.entity;

public class GoodsPersonage {
    private String id;

    private Double price;

    private Integer repertory;

    private Integer memberId;

    private String plantsAndAnimalsId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getRepertory() {
        return repertory;
    }

    public void setRepertory(Integer repertory) {
        this.repertory = repertory;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPlantsAndAnimalsId() {
        return plantsAndAnimalsId;
    }

    public void setPlantsAndAnimalsId(String plantsAndAnimalsId) {
        this.plantsAndAnimalsId = plantsAndAnimalsId == null ? null : plantsAndAnimalsId.trim();
    }
}