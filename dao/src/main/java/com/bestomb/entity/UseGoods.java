package com.bestomb.entity;

/***
 * 背包商品使用
 * @author qfzhang
 *
 */
public class UseGoods {
    
	// 会员编号
	private Integer memberId;
	// 商品编号
    private String goodsId;
    // 陵园编号
    private String cemeteryId;
//    // 墓碑编号
//    private String tombstoneId;
	//纪念人编号
	private String masterId;
	
    public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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
//	public String getTombstoneId() {
//		return tombstoneId;
//	}
//	public void setTombstoneId(String tombstoneId) {
//		this.tombstoneId = tombstoneId;
//	}

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
}