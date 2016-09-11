package com.bestomb.common.response.goods;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bestomb.common.Enum.DictType;
import com.bestomb.common.exception.DictException;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.entity.GoodsWithBLOBs;
import com.bestomb.service.IDictService;

public class GoodsBo {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	protected IDictService dictService;
	
	public GoodsBo(IDictService dictService) {
		this.dictService = dictService;
	}
	
	@Autowired
	
	private String id; // 商品ID
    private String name;
    private Double price;
    private String belongs; // 商品所属 （1：会员商品、2：系统商品）
    private String type; // 商品类型 （1：陵园装饰主题、2：陵园、3：陵园存储、4：交易币、5：动物、6：植物）
    private String modelId; // 商品模型ID
    private String createTime;
    private String images; // 图片json信息
    private String description; // 详细描述
    
    // 转化商品数据
    public void convert(GoodsWithBLOBs entity){
		convertBelongs(entity); // 转化商品所属
		convertType(entity); // // 转化商品类型
		convertCreateTime(entity); // 转化创建时间
    }
    
    // 转化商品所属
    public void convertBelongs(GoodsWithBLOBs entity){
    	try {
    		this.belongs = dictService.getDictValue(DictType.GOODSBELONGS.getName(), entity.getBelongs());
		} catch (DictException e) {
			logger.warn("转化商品所属失败！");
		}
    }
    
    // 转化商品类型
    public void convertType(GoodsWithBLOBs entity){
    	try {
    		this.type = dictService.getDictValue(DictType.GOODSTYPE.getName(), entity.getType());
    	} catch (DictException e) {
    		logger.warn("转化商品类型失败！");
    	}
    }
    
    // 转化创建时间
    public void convertCreateTime(GoodsWithBLOBs entity){
    	try {
			this.createTime = CalendarUtil.secondsTimeToDateTimeString(entity.getCreateTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
