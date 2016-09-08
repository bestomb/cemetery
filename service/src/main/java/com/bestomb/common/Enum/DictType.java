package com.bestomb.common.Enum;

/***
 * 字典类型枚举
 * @author qfzhang
 *
 */
public enum DictType {
	
	AUDITRESULT("auditResult"), // 审核结果
	CEMETERYISOPEN("cemeteryIsOpen"), // 陵园是否公开
	GOODSTYPE("goodsType"), // 商品类型
	GOODSBELONGS("goodsBelongs"), // 商品所属
	MUSICTYPE("musicType"), // 音乐类型
	
	ORDERSTATUS("orderStatus"), // 订单状态
	ORDERTYPE("orderType"), // 订单类型
	SEX("sex"), // 性别
	SPECIES("species"), // 墓碑种类
	TOMBSTONETYPE("tombstoneType"); // 墓碑类型
	
	String name;
	
	DictType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public String toString(){
		return name;
	}
}
