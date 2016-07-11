package com.bestomb.service.impl;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.systemMenu.SystemMenuBo;
import com.bestomb.dao.ISystemMenuDao;
import com.bestomb.entity.SystemMenu;
import com.bestomb.service.ISystemMenuService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 系统菜单业务逻辑实现类
 * Created by jason on 2016-07-08.
 */
@Service
public class SystemMenuServiceImpl implements ISystemMenuService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ISystemMenuDao systemMenuDao;

    /**
     * 根获取数据集合
     *
     * @return
     */
    public List<SystemMenuBo> getList() throws EqianyuanException {
        List<SystemMenu> systemMenus = systemMenuDao.selectByList();
        if (CollectionUtils.isEmpty(systemMenus)) {
            logger.info("get List is null");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_MENU_DATA_NOT_EXISTS);
        }

        List<SystemMenuBo> systemMenuBos = new ArrayList<SystemMenuBo>();
        for (SystemMenu systemMenu : systemMenus) {
            SystemMenuBo systemMenuBo = new SystemMenuBo();
            BeanUtils.copyProperties(systemMenu, systemMenuBo);
            systemMenuBos.add(systemMenuBo);
        }
        return systemMenuBos;
    }

    /**
     * 根据角色信息获取菜单数据集合
     *
     * @param roleId
     * @return
     */
    public List<SystemMenuBo> getListBySystemRole(String... roleId) {
        if (ObjectUtils.isEmpty(roleId)) {
            return Collections.emptyList();
        }
        List<SystemMenu> systemMenus = systemMenuDao.selectByRoleId(roleId);

        List<SystemMenuBo> systemMenuBos = new ArrayList<SystemMenuBo>();
        for (SystemMenu systemMenu : systemMenus) {
            SystemMenuBo systemMenuBo = new SystemMenuBo();
            BeanUtils.copyProperties(systemMenu, systemMenuBo);
            systemMenuBos.add(systemMenuBo);
        }
        return systemMenuBos;
    }

}
