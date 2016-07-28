package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.entity.Model;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IModelDao {
    int deleteByPrimaryKey(@Param("id") String... id);

    int insertSelective(Model record);

    Model selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Model record);

    /**
     * 根据主键查询数据集合
     *
     * @param id
     * @return
     */
    List<Model> selectById(@Param("id") String... id);

    /**
     * 获取数据总条数
     *
     * @return
     */
    Long countByPagination(@Param("classify_id") String classifyId);

    /**
     * 根据对象及分页条件获取分页数据集合
     *
     * @return
     */
    List<Model> selectByPagination(@Param("page") Page page, @Param("classify_id") String classifyId);
}