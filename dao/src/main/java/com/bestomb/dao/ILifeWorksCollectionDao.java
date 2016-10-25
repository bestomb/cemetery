package com.bestomb.dao;

import com.bestomb.common.Pager;
import com.bestomb.entity.LifeWorksCollection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ILifeWorksCollectionDao {
    int deleteByPrimaryKey(String id);

    int insert(LifeWorksCollection record);

    int insertSelective(LifeWorksCollection record);

    LifeWorksCollection selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LifeWorksCollection record);

    int updateByPrimaryKey(LifeWorksCollection record);

    int countByCondition(@Param("masterId") String masterId);

    List<LifeWorksCollection> selectByCondition(@Param("masterId") String masterId, @Param("page") Pager page);
}