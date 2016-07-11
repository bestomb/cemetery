package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.systemMenu.SystemMenuBo;

import java.util.List;

/**
 * 系统菜单业务接口
 * Created by jason on 2016-07-08.
 */
public interface ISystemMenuService {

    /**
     * 获取数据集合
     *
     * @return
     */
    List<SystemMenuBo> getList() throws EqianyuanException;

    /**
     * 根据角色信息获取数据集合
     *
     * @param roleId
     * @return
     */
    List<SystemMenuBo> getListBySystemRole(String ... roleId);
}
