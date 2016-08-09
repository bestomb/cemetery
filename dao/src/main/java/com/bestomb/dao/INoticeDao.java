package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.entity.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface INoticeDao {
    int deleteByPrimaryKey(String id);

    int insert(Notice record);

    int insertSelective(Notice record);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKeyWithBLOBs(Notice record);

    int updateByPrimaryKey(Notice record);

    Notice selectByPrimaryKey(String id);

    List<Notice> selectByPrimaryKeys(@Param("ids") String... id);

    /**
     * 获取数据总条数
     *
     * @return
     */
    Long countByPagination(@Param("cemetery_id") String cemeteryId);

    /**
     * 根据对象及分页条件获取分页数据集合
     *
     * @return
     */
    List<Notice> selectByPagination(@Param("page") Page page, @Param("cemetery_id") String cemeteryId);
}