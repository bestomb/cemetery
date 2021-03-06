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

    int updateByPrimaryKeySelective(PurchaseOrder record);

    int updateByPrimaryKeyWithBLOBs(PurchaseOrder record);

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
    
}