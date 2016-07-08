package com.bestomb.service.impl;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.systemMenu.SystemMenuByQueryResponse;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.ISystemMenuDao;
import com.bestomb.entity.SystemMenu;
import com.bestomb.service.ISystemMenuService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    public List<SystemMenuByQueryResponse> getList() throws EqianyuanException {
        List<SystemMenu> systemMenus = systemMenuDao.selectByList();
        if (CollectionUtils.isEmpty(systemMenus)) {
            logger.info("get List is null");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_MENU_DATA_NOT_EXISTS);
        }

        List<SystemMenuByQueryResponse> systemMenuByQueryResponses = new ArrayList<SystemMenuByQueryResponse>();
        for (SystemMenu systemMenu : systemMenus) {
            SystemMenuByQueryResponse systemMenuByQueryResponse = new SystemMenuByQueryResponse();
            BeanUtils.copyProperties(systemMenu, systemMenuByQueryResponse);
            systemMenuByQueryResponses.add(systemMenuByQueryResponse);
        }
        return systemMenuByQueryResponses;
    }

//    /**
//     * 根据用户信息获取数据集合
//     *
//     * @return
//     */
//    public List<SystemMenuByQueryResponse> getListBySystemUser(SystemUser) {
//        SystemUserVo systemUserVo = (SystemUserVo) SessionUtil.getAttribute(SystemConf.SYSTEM_SESSION_USER.toString());
//        return null;
//    }
}
