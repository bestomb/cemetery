package com.bestomb.dao;

import com.bestomb.entity.Cemetery;

public interface ICemeteryDao {
    int deleteByPrimaryKey(String id);

    int insert(Cemetery record);

    int insertSelective(Cemetery record);

    Cemetery selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Cemetery record);

    int updateByPrimaryKey(Cemetery record);
}