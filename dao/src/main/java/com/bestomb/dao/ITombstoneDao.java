package com.bestomb.dao;

import com.bestomb.entity.Tombstone;

public interface ITombstoneDao {

    int deleteByPrimaryKey(String id);

    int insert(Tombstone record);

    int insertSelective(Tombstone record);

    Tombstone selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Tombstone record);

    int updateByPrimaryKey(Tombstone record);

}