package com.bestomb.common.response.goods;

import org.apache.log4j.Logger;

import com.bestomb.common.Enum.DictType;
import com.bestomb.common.exception.DictException;
import com.bestomb.entity.OrderGoodsWithBLOBs;

public class GoodsBoWithCount extends GoodsBo {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private Integer count;
	
    // 转化商品数据
    public void convert(OrderGoodsWithBLOBs entity){
		convertBelongs(entity); // 转化商品所属
		convertType(entity); // // 转化商品类型
    }
    
    // 转化商品所属
    public void convertBelongs(OrderGoodsWithBLOBs entity){
    	try {
    		String belongs = dictService.getDictValue(DictType.GOODSBELONGS.getName(), entity.getBelongs()+"");
    		setBelongs(belongs);
		} catch (DictException e) {
			logger.warn("转化商品所属失败！");
		}
    }
    
    // 转化商品类型
    public void convertType(OrderGoodsWithBLOBs entity){
    	try {
    		String type =dictService.getDictValue(DictType.GOODSTYPE.getName(), entity.getType()+"");
    		setType(type);
    	} catch (DictException e) {
    		logger.warn("转化商品类型失败！");
    	}
    }
    
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
