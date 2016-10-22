package com.bestomb.dao;

import com.bestomb.common.Pager;
import com.bestomb.entity.Eulogy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IEulogyDao {
    int deleteByPrimaryKey(String id);

    int insert(Eulogy record);

    int insertSelective(Eulogy record);

    Eulogy selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Eulogy record);

    int updateByPrimaryKeyWithBLOBs(Eulogy record);

    int updateByPrimaryKey(Eulogy record);

    int countByCondition(@Param("masterId") String masterId);

    List<Eulogy> selectByCondition(@Param("masterId") String masterId, @Param("page") Pager page);
}