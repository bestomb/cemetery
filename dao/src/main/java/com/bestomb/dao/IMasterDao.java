package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.entity.Master;
import com.bestomb.entity.MasterWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IMasterDao {
    int deleteByPrimaryKey(String id);

    int insert(MasterWithBLOBs record);

    int insertSelective(MasterWithBLOBs record);

    MasterWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MasterWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MasterWithBLOBs record);

    int updateByPrimaryKey(Master record);

    /**
     * 根据陵园编号及分页信息查询陵园纪念人分页集合
     */
    List<MasterWithBLOBs> selectByPagination(@Param("page") Page page, @Param("cemeteryId") Integer cemeteryId);

    /**
     * 根据陵园编号查询陵园纪念人总数
     *
     * @return
     */
    Long countByPagination(@Param("cemeteryId") Integer cemeteryId);

    /**
     * 根据陵园墓碑编号查询墓中纪念人集合
     *
     * @param tombstoneId
     * @return
     */
    List<Master> selectByTombstone(@Param("tombstoneId") String tombstoneId);

    /**
     * 根据纪念人编号查询贡品集合
     *
     * @param masterId
     * @return
     */
    List<Map<String, String>> getOblationByMasterId(@Param("masterIds") List<String> masterId);
}