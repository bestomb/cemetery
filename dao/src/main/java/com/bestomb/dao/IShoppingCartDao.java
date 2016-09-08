package com.bestomb.dao;

import com.bestomb.entity.ShoppingCart;

public interface IShoppingCartDao {
    int deleteByPrimaryKey(String id);

    int insert(ShoppingCart record);

    int insertSelective(ShoppingCart record);

    ShoppingCart selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);
    
    /***
     * 获取会员购物车总金额
     * @param memberId
     * @return
     */
    Double getTotalPriceByMemberId(Integer memberId);

    /***
     * 清空会员购物车
     * @param memberId
     * @return
     */
	boolean removeMemberCart(Integer memberId);
    
}