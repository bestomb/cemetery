package com.bestomb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bestomb.common.Pager;
import com.bestomb.entity.Goods;
import com.bestomb.entity.Store;
import com.bestomb.entity.StoreWithGoods;

public interface IStoreDao {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Store record);

    int insertSelective(Store record);

    Store selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Store record);

    int updateByPrimaryKey(Store record);
    
    /***
     * 根据条件查询会员店铺已发布商品分页列表总数
     * @param store
     * @param goods
     * @return
     */
    int getPageListCount(@Param("store") Store store, @Param("goods") Goods goods);
    
    /***
     * 根据条件查询会员店铺已发布商品分页列表
     * @param store
     * @param goods
     * @param page
     * @return
     */
    List<StoreWithGoods> getPageList(@Param("store") Store store, @Param("goods") Goods goods, @Param("page") Pager page);
    
    /***
     * 查询会员店铺订单销售总额
     * @param memberId
     * @return
     */
    public Double getStoreOrdersTotalPrice(Integer memberId);
    
}