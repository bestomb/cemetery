package com.bestomb.entity;

public class StoreWithGoods extends Store {
	
	private String name ; // 发布商品名称
	private Double price ; // 发布商品价格

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
}
