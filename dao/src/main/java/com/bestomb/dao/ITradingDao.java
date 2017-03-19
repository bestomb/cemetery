package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.entity.TradingDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ITradingDao {
    int deleteByPrimaryKey(String id);

    int insert(TradingDetail record);

    int insertSelective(TradingDetail record);

    TradingDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TradingDetail record);

    int updateByPrimaryKey(TradingDetail record);

    int batchInsert(@Param("tradingDetails") List<TradingDetail> tTradingDetails);

    /**
     * 获取会员交易币明细数据总条数
     *
     * @param memberId 会员编号
     * @return
     */
    Long countByPagination(@Param("memberId") int memberId);

    /**
     * 根据会员编号及分页条件获取交易币明细分页数据集合
     *
     * @param page     分页对象
     * @param memberId 会员编号
     * @return
     */
    List<TradingDetail> selectByPagination(@Param("page") Page page, @Param("memberId") int memberId);
}