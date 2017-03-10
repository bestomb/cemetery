package com.bestomb.dao;

import com.bestomb.entity.GoodsUseRelat;
import org.apache.ibatis.annotations.Param;

public interface IGoodsUseRelatDao {
    int deleteByPrimaryKey(String id);

    int insert(GoodsUseRelat record);

    int insertSelective(GoodsUseRelat record);

    GoodsUseRelat selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GoodsUseRelat record);

    int updateByPrimaryKey(GoodsUseRelat record);

    /**
     * 根据objectId查询商品使用关联数据总数
     *
     * @param objectId
     * @return
     */
    int countByObjectId(@Param("objectId") String objectId);

    /**
     * 根据objectId删除数据
     *
     * @param objectId
     * @return
     */
    int deleteByObjectId(@Param("objectId") String objectId);
}