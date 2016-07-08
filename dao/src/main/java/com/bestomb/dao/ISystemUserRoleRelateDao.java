package com.bestomb.dao;

import org.apache.ibatis.annotations.Param;

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
    int deleteBySystemUser(@Param("user_id") String ... systemUserId);
}