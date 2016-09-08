package com.bestomb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bestomb.common.Pager;
import com.bestomb.entity.Goods;
import com.bestomb.entity.GoodsWithBLOBs;

public interface IGoodsDao {
    int deleteByPrimaryKey(String id);

    int insert(GoodsWithBLOBs record);

    int insertSelective(GoodsWithBLOBs record);

    GoodsWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GoodsWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(GoodsWithBLOBs record);

    int updateByPrimaryKey(Goods record);
    
	/***
	 * 根据条件查询商品分页列表总数
	 * @param goods
	 * @return
	 */
	int getGoodsPageListCount(@Param("goods") Goods goods);
	
	/***
	 * 根据条件查询商品分页列表
	 * @param goods
	 * @param page
	 * @return
	 */
	List<GoodsWithBLOBs> getGoodsPageList(@Param("goods") Goods goods, @Param("page") Pager page);
	
}