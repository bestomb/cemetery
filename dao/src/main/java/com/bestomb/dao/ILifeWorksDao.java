package com.bestomb.dao;


import com.bestomb.common.Pager;
import com.bestomb.entity.LifeWorks;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ILifeWorksDao {
    int deleteByPrimaryKey(String id);

    int insert(LifeWorks record);

    int insertSelective(LifeWorks record);

    LifeWorks selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LifeWorks record);

    int updateByPrimaryKeyWithBLOBs(LifeWorks record);

    int updateByPrimaryKey(LifeWorks record);

    int countByCondition(@Param("collectionId") String collectionId);

    List<LifeWorks> selectByCondition(@Param("collectionId") String collectionId, @Param("page") Pager page);
}