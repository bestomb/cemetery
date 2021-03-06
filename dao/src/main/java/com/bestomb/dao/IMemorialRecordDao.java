package com.bestomb.dao;

import com.bestomb.common.Pager;
import com.bestomb.entity.MemorialRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMemorialRecordDao {
    int deleteByPrimaryKey(String id);

    int insert(MemorialRecord record);

    int insertSelective(MemorialRecord record);

    MemorialRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MemorialRecord record);

    int updateByPrimaryKey(MemorialRecord record);

    int countByCondition(@Param("masterId") String masterId);

    List<MemorialRecord> selectByCondition(@Param("masterId") String masterId, @Param("page") Pager page);

    /**
     * 时间范围内陵园参拜总数
     * @param beginTime
     * @param endTime
     * @return
     */
    long countByTime(@Param("beginTime") Integer beginTime, @Param("endTime") Integer endTime);
}