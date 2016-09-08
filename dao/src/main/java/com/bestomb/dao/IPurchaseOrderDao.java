package com.bestomb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bestomb.common.Pager;
import com.bestomb.entity.PurchaseOrder;

public interface IPurchaseOrderDao {
	
    int deleteByPrimaryKey(String id);

    int insert(PurchaseOrder record);

    int insertSelective(PurchaseOrder record);

    PurchaseOrder selectByPrimaryKey(String id);
    /***
     * 根据订单编号查询订单数据
     * @param orderNumber
     * @return
     */
    PurchaseOrder selectByOrderNumber(String orderNumber);

    int updateByPrimaryKeySelective(PurchaseOrder record);
    
    /***
     * 根据订单编号修改订单数据
     * @param record
     * @return
     */
    int updateByOrderNumber(PurchaseOrder record);

    int updateByPrimaryKey(PurchaseOrder record);
    
    /***
     * 获取订单分页列表总数
     * @param record
     * @param page
     * @return
     */
    int getPageListCount(@Param("order") PurchaseOrder record);
    
    /***
     * 获取订单分页列表
     * @param record
     * @param page
     * @return
     */
    List<PurchaseOrder> getPageList(@Param("order") PurchaseOrder record, @Param("page") Pager page );
    
    /***
     * 查询会员店铺销售订单分页列表总数
     * @param memberId
     * @return
     */
    int getStoreOrdersCount(Integer memberId);
    
    /***
     * 查询会员店铺销售订单分页列表
     * @param memberId
     * @param page
     * @return
     */
    List<PurchaseOrder> getStoreOrders(Integer memberId, @Param("page") Pager page );
    
}