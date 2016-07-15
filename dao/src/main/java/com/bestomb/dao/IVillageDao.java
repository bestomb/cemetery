package com.bestomb.dao;

import com.bestomb.entity.Village;
import org.apache.ibatis.annotations.Param;

public interface IVillageDao {
    int deleteByPrimaryKey(String id);

    int insert(Village record);

    int insertSelective(Village record);

    Village selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Village record);

    int updateByPrimaryKey(Village record);

    /**
     * 根据地区编号查询数据
     *
     * @param townId 乡编号
     * @param name   村名称
     * @return
     */
    Village selectByName(@Param("town_id") String townId, @Param("name") String name);

}