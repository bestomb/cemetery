package com.bestomb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bestomb.common.Pager;
import com.bestomb.entity.Backpack;

public interface IBackpackDao {
    int deleteByPrimaryKey(String id);

    int insert(Backpack record);

    int insertSelective(Backpack record);

    Backpack selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Backpack record);

    int updateByPrimaryKey(Backpack record);
    
    /***
	 * 根据条件查询分页列表总数
	 * @param goods
	 * @return
	 */
	int getPageListCount(@Param("backpack") Backpack backpack);
	
	/***
	 * 根据条件查询背包分页列表
	 * @param goods
	 * @param page
	 * @return
	 */
	List<Backpack> getPageList(@Param("backpack") Backpack backpack, @Param("page") Pager page);
	
}