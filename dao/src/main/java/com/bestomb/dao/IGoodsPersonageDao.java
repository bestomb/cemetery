package com.bestomb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bestomb.common.Pager;
import com.bestomb.entity.GoodsPersonage;
import com.bestomb.entity.Mall;

public interface IGoodsPersonageDao {
    
	int deleteByPrimaryKey(String id);

    int insert(GoodsPersonage record);

    int insertSelective(GoodsPersonage record);

    GoodsPersonage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GoodsPersonage record);

    int updateByPrimaryKey(GoodsPersonage record);
    
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
	List<GoodsPersonage> getPageList(@Param("mall") Mall mall, @Param("page") Pager page);
    
}