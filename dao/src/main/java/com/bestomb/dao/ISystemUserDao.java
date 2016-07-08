package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.entity.SystemUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户管理dao
 */
public interface ISystemUserDao {
    /**
     * 根据序列编号删除数据
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") String... id);

    /**
     * 插入对象数据
     *
     * @param record
     * @return
     */
    int insertSelective(SystemUser record);

    /**
     * 根据主键编号查询数据对象
     *
     * @param id
     * @return
     */
    SystemUser selectByPrimaryKey(String id);

    /**
     * 更新对象数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SystemUser record);

    /**
     * 根据登录信息查找用户数据
     *
     * @param loginName
     * @param loginPassword
     * @return
     */
    SystemUser selectByLogin(@Param("login_name") String loginName, @Param("login_password") String loginPassword);

    /**
     * 获取数据总条数
     *
     * @return
     */
    Long countByPagination(@Param("role_id") String roleId);

    /**
     * 根据对象及分页条件获取分页数据集合
     *
     * @return
     */
    List<SystemUser> selectByPagination(@Param("page") Page page, @Param("role_id") String roleId);
}