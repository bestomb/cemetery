package com.bestomb.dao;

import com.bestomb.entity.CemeteryIdBuild;

public interface ICemeteryIdBuildDao {
    int insertSelective(CemeteryIdBuild record);

    int updateByPrimaryKeySelective(CemeteryIdBuild record);
}