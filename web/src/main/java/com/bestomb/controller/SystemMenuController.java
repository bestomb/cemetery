package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.systemMenu.SystemMenuBo;
import com.bestomb.service.ISystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统管理菜单控制器
 * Created by jason on 2016-07-08.
 */
@Controller
@RequestMapping("/system_menu")
public class SystemMenuController extends BaseController {

    @Autowired
    private ISystemMenuService systemMenuService;

    /**
     * 获取系统菜单集合
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/list")
    @ResponseBody
    public ServerResponse getList() throws EqianyuanException {
        List<SystemMenuBo> systemMenuByQueryResponses = systemMenuService.getList();
        return new ServerResponse.ResponseBuilder().data(systemMenuByQueryResponses).build();
    }
}
