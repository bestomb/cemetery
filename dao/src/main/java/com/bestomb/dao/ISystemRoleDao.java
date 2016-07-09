package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.entity.SystemRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISystemRoleDao {
    /**
     * 插入对象数据
     *
     * @param record
     * @return
     */
    int insertSelective(SystemRole record);

    /**
     * 根据序列编号删除数据
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") String... id);

    /**
     * 更新对象数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SystemRole record);

    /**
     * 查询所有数据集合
     *
     * @return
     */
    List<SystemRole> selectByList();

    /**
     * 根据主键编号查询数据对象
     *
     * @param id
     * @return
     */
    SystemRole selectByPrimaryKey(String id);

    /**
     * 获取数据总条数
     *
     * @return
     */
    Long countByPagination();

    /**
     * 根据对象及分页条件获取分页数据集合
     *
     * @return
     */
    List<SystemRole> selectByPagination(@Param("page") Page page);
}