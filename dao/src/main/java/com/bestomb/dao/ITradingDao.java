package com.bestomb.dao;

import com.bestomb.entity.TradingDetail;

public interface ITradingDao {
    int deleteByPrimaryKey(String id);

    int insert(TradingDetail record);

    int insertSelective(TradingDetail record);

    TradingDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TradingDetail record);

    int updateByPrimaryKey(TradingDetail record);
}