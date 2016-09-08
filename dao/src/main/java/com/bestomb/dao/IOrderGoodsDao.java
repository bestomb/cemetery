package com.bestomb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bestomb.common.Pager;
import com.bestomb.entity.Goods;
import com.bestomb.entity.GoodsWithBLOBs;
import com.bestomb.entity.OrderGoods;
import com.bestomb.entity.OrderGoodsWithBLOBs;
import com.bestomb.entity.PurchaseOrder;

public interface IOrderGoodsDao {
    int deleteByPrimaryKey(String id);

    int insert(OrderGoodsWithBLOBs record);

    int insertSelective(OrderGoodsWithBLOBs record);
    
    /***
     * 购物车转订单插入订单商品表
     * @param order
     * @return
     */
    int insertCartGoods(@Param("order") PurchaseOrder order);

    OrderGoodsWithBLOBs selectByPrimaryKey(String id);
    
    /***
     * 根据条件查询订单商品详情
     * @param goods
     * @return
     */
    OrderGoodsWithBLOBs selectByCondition(OrderGoods goods);
    
    int updateByPrimaryKeySelective(OrderGoodsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(OrderGoodsWithBLOBs record);

    int updateByPrimaryKey(OrderGoods record);
    
    /***
     * 根据订单编号获取订单商品列表
     * @param OrderId
     * @return
     */
    List<OrderGoodsWithBLOBs> getOrderGoodsByOrderId(String orderId);
    
    /***
     * 查询会员店铺销售订单详情
     * @param orderId
     * @param memberId
     * @return
     */
    List<OrderGoodsWithBLOBs> getStoreOrdersDetail(String orderId, Integer memberId);
    
    /***
     * 根据查询条件查询会员背包商品分页列表总数
     * @param memberId
     * @param goods
     * @param page
     * @return
     */
	int getBackpackGoodsCount(Integer memberId, @Param("goods") Goods goods);
	
	/***
	 * 根据查询条件查询会员背包商品分页列表
	 * @param memberId
	 * @param goods
	 * @param page
	 * @return
	 */
	List<GoodsWithBLOBs> getBackpackGoods(Integer memberId, @Param("goods") Goods goods, @Param("page") Pager page);
    
}