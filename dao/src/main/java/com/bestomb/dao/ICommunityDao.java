package com.bestomb.dao;

import com.bestomb.entity.Community;
import org.apache.ibatis.annotations.Param;

public interface ICommunityDao {
    int deleteByPrimaryKey(String id);

    int insert(Community record);

    int insertSelective(Community record);

    Community selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Community record);

    int updateByPrimaryKey(Community record);

    /**
     * 根据地区编号查询数据
     *
     * @param villageId 村编号
     * @param name      社名称
     * @return
     */
    Community selectByName(@Param("village_id") String villageId, @Param("name") String name);
}