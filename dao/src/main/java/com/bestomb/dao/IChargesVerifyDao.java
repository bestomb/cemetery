package com.bestomb.dao;


import com.bestomb.entity.ChargesVerify;
import org.apache.ibatis.annotations.Param;

public interface IChargesVerifyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargesVerify record);

    int insertSelective(ChargesVerify record);

    ChargesVerify selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargesVerify record);

    int updateByPrimaryKey(ChargesVerify record);

    /**
     * 根据订单编号查询会员编号
     *
     * @param orderNo
     * @return
     */
    int selectMemberByOrderNo(@Param("orderNo") Long orderNo);

    /**
     * 根据订单编号删除数据
     *
     * @param orderNo
     * @return
     */
    int deleteByOrderNo(@Param("orderNo") Long orderNo);
}