package com.bestomb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bestomb.common.Pager;
import com.bestomb.entity.Biont;
import com.bestomb.entity.Park;

public interface IParkDao {
    int deleteByPrimaryKey(String id);

    int insert(Park record);

    int insertSelective(Park record);

    Park selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Park record);

    int updateByPrimaryKey(Park record);
    
    /***
	 * 根据条件查询分页列表总数
	 * @param goods
	 * @return
	 */
	int getPageListCount(@Param("park") Biont park);
	
	/***
	 * 根据条件查询动植物分页列表
	 * @param goods
	 * @param page
	 * @return
	 */
	List<Park> getPageList(@Param("park") Biont park, @Param("page") Pager page);

	/***
	 * 动植物升级（动物喂养，植物施肥）
	 * @param biont
	 * @return
	 */
	int upgrade(Biont biont);

	/***
	 * 根据条件删除动植物
	 * @param biont
	 * @return
	 */
	int deleteByCondition(Biont biont);
    
}