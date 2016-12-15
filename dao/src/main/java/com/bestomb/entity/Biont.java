package com.bestomb.entity;

import java.math.BigDecimal;

public class Biont {
	
	// 主键
	private String id;
	// 名称
    private String name;
    // 售价
    private BigDecimal price;
    // 图片
    private String images;
    // 模型地址
    private String modelId;
    // 生物类型（1：动物；2：植物）
    private Integer type;
    // 分组
    private Integer grouping;
    // 成长阶段
    private Integer phase;
    // 详细描述
    private String description;
    // 商品编号
 	private String goodsId;
    // 陵园ID
    private String cemeteryId;
    // 成长天数
    private Integer growingDays;
    // 生命值
    private Integer lifeValue;
	//模型文件地址
	private String modelAddress;
	
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		this.images = images;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
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
		this.description = description;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getCemeteryId() {
		return cemeteryId;
	}
	public void setCemeteryId(String cemeteryId) {
		this.cemeteryId = cemeteryId;
	}
	public Integer getGrowingDays() {
		return growingDays;
	}
	public void setGrowingDays(Integer growingDays) {
		this.growingDays = growingDays;
	}
	public Integer getLifeValue() {
		return lifeValue;
	}
	public void setLifeValue(Integer lifeValue) {
		this.lifeValue = lifeValue;
	}

	public String getModelAddress() {
		return modelAddress;
	}

	public void setModelAddress(String modelAddress) {
		this.modelAddress = modelAddress;
	}
}