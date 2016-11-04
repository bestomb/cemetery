package com.bestomb.dao;

import com.bestomb.common.Pager;
import com.bestomb.entity.GoodsPersonage;
import com.bestomb.entity.GoodsPersonageInfo;
import com.bestomb.entity.Mall;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IGoodsPersonageDao {

    int deleteByPrimaryKey(String id);

    int insert(GoodsPersonage record);

    int insertSelective(GoodsPersonage record);

    GoodsPersonage selectByPrimaryKey(String id);

    /**
     * 根据商品编号集合查询商品集合信息
     *
     * @param goodsIds
     * @return
     */
    List<GoodsPersonageInfo> selectByIds(@Param("goodsIds") List<String> goodsIds);

    int updateByPrimaryKeySelective(GoodsPersonage record);

    int updateByPrimaryKey(GoodsPersonage record);

    /***
     * 根据条件查询分页列表总数
     *
     * @param goods
     * @return
     */
    int getPageListCount(@Param("mall") Mall mall);

    /***
     * 根据条件查询商品分页列表
     *
     * @param goods
     * @param page
     * @return
     */
    List<GoodsPersonage> getPageList(@Param("mall") Mall mall, @Param("page") Pager page);

    /**
     * 批量更新商品库存
     *
     * @param repertoryList
     * @return
     */
    int batchUpdateByRepertory(@Param("repertoryList") List<Map<String, String>> repertoryList);

}