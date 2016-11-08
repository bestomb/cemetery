package com.bestomb.dao;

import com.bestomb.common.Pager;
import com.bestomb.entity.Backpack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IBackpackDao {
    int deleteByPrimaryKey(String id);

    int insert(Backpack record);

    int insertSelective(Backpack record);

    /**
     * 根据购物清单将商品加入到会员背包
     *
     * @param backpackList
     * @return
     */
    int insertByGoodsBuy(@Param("backpackList") List<Backpack> backpackList);

    Backpack selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Backpack record);

    int updateByPrimaryKey(Backpack record);

    /***
     * 根据条件查询分页列表总数
     *
     * @param goods
     * @return
     */
    int getPageListCount(@Param("backpack") Backpack backpack);

    /***
     * 根据条件查询背包分页列表
     *
     * @param goods
     * @param page
     * @return
     */
    List<Backpack> getPageList(@Param("backpack") Backpack backpack, @Param("page") Pager page);

    /***
     * 根据会员编号和商品编号查询背包商品
     * @param memberId
     * @param goodsId
     * @return
     */
    Backpack selectByCondition(@Param("memberId") Integer memberId, @Param("goodsId") String goodsId);

}