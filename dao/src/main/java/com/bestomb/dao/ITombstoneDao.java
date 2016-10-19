package com.bestomb.dao;

import com.bestomb.common.Pager;
import com.bestomb.entity.Tombstone;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ITombstoneDao {

    int deleteByPrimaryKey(String id);

    int insert(Tombstone record);

    int insertSelective(Tombstone record);

    Tombstone selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Tombstone record);

    int updateByPrimaryKey(Tombstone record);

    int countByCondition(@Param("tombstone") Tombstone tombstone);

    List<Tombstone> selectByCondition(@Param("tombstone") Tombstone tombstone, @Param("page") Pager page);

}