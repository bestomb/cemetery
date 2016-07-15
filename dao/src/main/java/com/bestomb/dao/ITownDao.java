package com.bestomb.dao;

import com.bestomb.entity.Town;
import org.apache.ibatis.annotations.Param;

public interface ITownDao {
    int deleteByPrimaryKey(String id);

    int insert(Town record);

    int insertSelective(Town record);

    Town selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Town record);

    int updateByPrimaryKey(Town record);

    /**
     * 根据地区编号查询数据
     *
     * @param countyId 区编号
     * @param name     乡名称
     * @return
     */
    Town selectByName(@Param("county_id") String countyId, @Param("name") String name);
}