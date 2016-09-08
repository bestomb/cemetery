package com.bestomb.entity;

/***
 * 会员背包商品
 * @author qfzhang
 *
 */
public class BackpackGoods extends GoodsWithBLOBs {
	
	private Integer count; // 商品数量

	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
