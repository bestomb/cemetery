package com.bestomb.dao;

import com.bestomb.entity.GoodsUseRelat;

public interface IGoodsUseRelatDao {
    int deleteByPrimaryKey(String id);

    int insert(GoodsUseRelat record);

    int insertSelective(GoodsUseRelat record);

    GoodsUseRelat selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GoodsUseRelat record);

    int updateByPrimaryKey(GoodsUseRelat record);
}