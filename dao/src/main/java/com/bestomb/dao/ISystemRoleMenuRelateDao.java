package com.bestomb.dao;

import org.apache.ibatis.annotations.Param;

public interface ISystemRoleMenuRelateDao {
    /**
     * 插入角色与菜单关系
     *
     * @param systemRoleId
     * @param menuId
     * @return
     */
    int insert(@Param("role_id") String systemRoleId, @Param("menu_id") String... menuId);

    /**
     * 根据角色序列编号删除数据
     *
     * @param systemRoleId
     * @return
     */
    int deleteBySystemUser(@Param("role_id") String... systemRoleId);
}