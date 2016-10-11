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
     * ��ϸ��Ϣ��ѯ
     * @param id
     * @return
     */
    MasterWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MasterWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MasterWithBLOBs record);

    int updateByPrimaryKey(Master record);

    /**
     * ��ҳ��ѯ
     */
    List<MasterWithBLOBs> selectByPagination(@Param("page") Page page, @Param("cemeteryId") Integer cemeteryId);

    /**
     * ��ȡ����������
     *
     * @return
     */
    Long countByPagination();
}