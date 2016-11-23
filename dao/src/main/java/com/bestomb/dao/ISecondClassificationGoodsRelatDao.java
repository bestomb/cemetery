package com.bestomb.dao;

import com.bestomb.entity.SecondClassificationGoodsRelat;
import org.apache.ibatis.annotations.Param;

public interface ISecondClassificationGoodsRelatDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SecondClassificationGoodsRelat record);

    int insertSelective(SecondClassificationGoodsRelat record);

    SecondClassificationGoodsRelat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SecondClassificationGoodsRelat record);

    int updateByPrimaryKey(SecondClassificationGoodsRelat record);

    int deleteByGoodsId(@Param("goods_id") String goodsId);
}