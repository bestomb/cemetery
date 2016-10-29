package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.entity.PublicFigures;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPublicFiguresDao {
    int deleteByPrimaryKey(String id);

    int insert(PublicFigures record);

    int insertSelective(PublicFigures record);

    /**
     * 根据数据ID查询数据
     * @param id
     * @return
     */
    PublicFigures selectByPrimaryKey(String id);

    /**
     * 修改数据
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PublicFigures record);

    int updateByPrimaryKeyWithBLOBs(PublicFigures record);

    int updateByPrimaryKey(PublicFigures record);

    /**
     * 分页查询
     */
    List<PublicFigures> selectByPagination(@Param("page") Page page, @Param("status") String status);

    /**
     * 根据数据对象获取总条数
     */
    Long countByPagination(@Param("status") String status);

}