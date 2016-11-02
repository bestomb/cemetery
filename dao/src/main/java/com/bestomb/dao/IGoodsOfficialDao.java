package com.bestomb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bestomb.common.Pager;
import com.bestomb.entity.GoodsOfficial;
import com.bestomb.entity.GoodsOfficialWithBLOBs;
import com.bestomb.entity.Mall;

public interface IGoodsOfficialDao {
    
	int deleteByPrimaryKey(String id);

    int insert(GoodsOfficialWithBLOBs record);

    int insertSelective(GoodsOfficialWithBLOBs record);

    GoodsOfficialWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GoodsOfficialWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(GoodsOfficialWithBLOBs record);

    int updateByPrimaryKey(GoodsOfficial record);
    
    /***
	 * 根据条件查询分页列表总数
	 * @param goods
	 * @return
	 */
	int getPageListCount(@Param("mall") Mall mall);
	
	/***
	 * 根据条件查询商品分页列表
	 * @param goods
	 * @param page
	 * @return
	 */
	List<GoodsOfficialWithBLOBs> getPageList(@Param("mall") Mall mall, @Param("page") Pager page);
    
}