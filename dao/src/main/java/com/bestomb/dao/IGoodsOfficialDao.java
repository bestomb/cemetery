package com.bestomb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bestomb.common.Pager;
import com.bestomb.entity.GoodsOfficial;
import com.bestomb.entity.GoodsOfficialWithBLOBs;
import com.bestomb.entity.Mall;

public interface IGoodsOfficialDao {

    int deleteByPrimaryKey(@Param("id") String... id);

    int insert(GoodsOfficialWithBLOBs record);

    int insertSelective(GoodsOfficialWithBLOBs record);

    GoodsOfficialWithBLOBs selectByPrimaryKey(String id);

    /**
     * 根据商品编号集合查询商品集合信息
     *
     * @param goodsIds
     * @return
     */
    List<GoodsOfficialWithBLOBs> selectByIds(@Param("goodsIds") List<String> goodsIds);

    int updateByPrimaryKeySelective(GoodsOfficialWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(GoodsOfficialWithBLOBs record);

    int updateByPrimaryKey(GoodsOfficial record);

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
    List<GoodsOfficialWithBLOBs> getPageList(@Param("mall") Mall mall, @Param("page") Pager page);

    /**
     * 根据二级商品分类查询商品总数
     *
     * @param secondClassify
     * @return
     */
    int selectCountBySecondClassify(@Param("second_classify") Integer secondClassify);

    /**
     * 根据二级商品分类及分页信息查询商品分页数据集合
     *
     * @param secondClassify
     * @param page
     * @return
     */
    List<GoodsOfficialWithBLOBs> selectBySecondClassify(@Param("second_classify") Integer secondClassify, @Param("page") Pager page);

}