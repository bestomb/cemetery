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

    /**
     * 根据陵园编号和墓碑种类（人物墓碑区、宠物墓碑区）查询墓区中，当前建设墓碑的位置上有没有数据
     * @param cemeteryId
     * @param species
     * @param sort
     * @return
     */
    int selectByPosition(@Param("cemeteryId") Integer cemeteryId, @Param("species") Integer species, @Param("sort") Integer sort);

    /**
     * 时间范围内新增了多少墓碑
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    long countByTime(@Param("beginTime") Integer beginTime, @Param("endTime") Integer endTime);
}