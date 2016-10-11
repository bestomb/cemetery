package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.entity.Master;
import com.bestomb.entity.MasterWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMasterDao {
    int deleteByPrimaryKey(String id);

    int insert(MasterWithBLOBs record);

    int insertSelective(MasterWithBLOBs record);

    /**
     * 详细信息查询
     * @param id
     * @return
     */
    MasterWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MasterWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MasterWithBLOBs record);

    int updateByPrimaryKey(Master record);

    /**
     * 分页查询
     */
    List<MasterWithBLOBs> selectByPagination(@Param("page") Page page, @Param("cemeteryId") Integer cemeteryId);

    /**
     * 获取数据总条数
     *
     * @return
     */
    Long countByPagination();
}