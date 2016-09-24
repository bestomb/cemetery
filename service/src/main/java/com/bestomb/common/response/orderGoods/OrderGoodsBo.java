package com.bestomb.common.response.orderGoods;

import org.apache.log4j.Logger;

import com.bestomb.common.Enum.DictType;
import com.bestomb.common.exception.DictException;
import com.bestomb.entity.OrderGoodsWithBLOBs;
import com.bestomb.service.IDictService;

public class OrderGoodsBo {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	protected IDictService dictService;
	
	// 必须通过构造函数将dictService对象初始化
	public OrderGoodsBo(IDictService dictService) {
		this.dictService = dictService;
	}
	
	private String id; // 主键ID
	private String goodsId; // 商品编号
	private String orderId;  // 订单编号
    private String name; // 商品名称
    private Double price; // 商品价格
    private String belongs; // 商品所属 （1：会员商品、2：系统商品）
    private String type; // 商品类型 （1：陵园装饰主题、2：陵园、3：陵园存储、4：交易币、5：动物、6：植物）
    private String modelId; // 商品模型ID
    private Integer count; // 商品数量
    private String images; // 图片json信息
    private String description; // 详细描述
    
    
    // 转化商品数据
    public void convert(OrderGoodsWithBLOBs entity){
		convertBelongs(entity); // 转化商品所属
		convertType(entity); // // 转化商品类型
    }
    
    // 转化商品所属
    public void convertBelongs(OrderGoodsWithBLOBs entity){
    	try {
    		this.belongs = dictService.getDictValue(DictType.GOODSBELONGS.getName(), entity.getBelongs());
		} catch (DictException e) {
			logger.warn("转化商品所属失败！");
		}
    }
    
    // 转化商品类型
    public void convertType(OrderGoodsWithBLOBs entity){
    	try {
    		this.type = dictService.getDictValue(DictType.GOODSTYPE.getName(), entity.getType());
    	} catch (DictException e) {
    		logger.warn("转化商品类型失败！");
    	}
    }
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
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
	public String getBelongs() {
		return belongs;
	}
	public void setBelongs(String belongs) {
		this.belongs = belongs;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
