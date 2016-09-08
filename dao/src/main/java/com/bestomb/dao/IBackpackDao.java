package com.bestomb.dao;

import com.bestomb.entity.Backpack;

public interface IBackpackDao {
    int deleteByPrimaryKey(String id);

    int insert(Backpack record);

    int insertSelective(Backpack record);

    Backpack selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Backpack record);

    int updateByPrimaryKey(Backpack record);
    
    /***
     * 会员店铺发布商品后，修正背包商品数量
     * @param record
     * @return
     */
    boolean modifyGoodsCount(Backpack record);
    
}