package com.bestomb.entity;

public class GoodsUseRelat {
    
	private String id;
    private Integer type; // 1：大门、2：墓碑、3：祭品（香）、4：祭品（蜡烛）、5：祭品（花）、6：普通祭品、7：扩展陵园存储容量、8：增加可建陵园数、9：动物饲料、10：植物肥料
    private String objectId; // 如果对象是陵园，则该数据为陵园编号，如果对象是墓碑，则该数据为墓碑编号，如果对象为纪念人，则数据为纪念人编号
    private String goodsId;
    private Integer lifecycle; // 生命周期
    private Integer memberId;
    private Integer createTime;

    public GoodsUseRelat(){}
    public GoodsUseRelat(Integer type, Integer memberId, String goodsId, String objectId, Integer createTime, Integer lifecycle){
    	this.type = type;
    	this.memberId = memberId;
    	this.goodsId = goodsId;
    	this.objectId = objectId;
    	this.createTime = createTime;
    	this.lifecycle = lifecycle;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getObjectId() {
        return objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId == null ? null : objectId.trim();
    }
    public String getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }
    public Integer getLifecycle() {
		return lifecycle;
	}
	public void setLifecycle(Integer lifecycle) {
		this.lifecycle = lifecycle;
	}
	public Integer getMemberId() {
        return memberId;
    }
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public Integer getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Integer createTime) {
    	this.createTime = createTime;
    }
}