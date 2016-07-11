package com.bestomb.dao;

import com.bestomb.entity.SystemUserRoleRelate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISystemUserRoleRelateDao {
    /**
     * 插入用户与角色关系
     *
     * @param systemUserId
     * @param roleId
     * @return
     */
    int insert(@Param("user_id") String systemUserId, @Param("role_id") String... roleId);

    /**
     * 根据用户序列编号删除数据
     *
     * @param systemUserId
     * @return
     */
    int deleteBySystemUser(@Param("user_id") String... systemUserId);

    /**
     * 根据角色序列编号删除数据
     *
     * @param systemRoleId
     * @return
     */
    int deleteBySystemRole(@Param("role_id") String... systemRoleId);

    /**
     * 根据用户编号查询用户绑定的角色编号
     *
     * @param systemUserId
     * @return
     */
    List<SystemUserRoleRelate> selectMenuIdBySystemUser(@Param("user_id") String systemUserId);
}