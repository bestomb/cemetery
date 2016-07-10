package com.bestomb.dao;

import com.bestomb.entity.SystemRoleMenuRelate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    int deleteBySystemRole(@Param("role_id") String... systemRoleId);

    /**
     * 根据角色编号查询角色绑定的菜单编号
     *
     * @param systemRoleId
     * @return
     */
    List<SystemRoleMenuRelate> selectMenuIdBySystemRole(@Param("role_id") String systemRoleId);

}