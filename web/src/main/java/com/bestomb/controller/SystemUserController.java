package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.systemUser.SystemUserByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.systemUser.SystemUserBo;
import com.bestomb.service.ISystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统用户管理
 * Created by jason on 2016-07-08.
 */
@Controller
@RequestMapping("/system_user")
public class SystemUserController extends BaseController {

    @Autowired
    private ISystemUserService systemUserService;

    /**
     * 添加用户
     *
     * @param systemUserByEditRequest
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(SystemUserByEditRequest systemUserByEditRequest) throws EqianyuanException {
        systemUserService.add(systemUserByEditRequest);
        return new ServerResponse();
    }

    /**
     * 系统管理用户信息删除
     *
     * @param id 用户编号
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(String... id) throws EqianyuanException {
        systemUserService.removeByIds(id);
        return new ServerResponse();
    }

    /**
     * 系统管理用户信息修改
     *
     * @param systemUserByEditRequest
     * @return
     */
    @RequestMapping("/modify")
    @ResponseBody
    public ServerResponse modify(SystemUserByEditRequest systemUserByEditRequest) throws EqianyuanException {
        systemUserService.modify(systemUserByEditRequest);
        return new ServerResponse();
    }

    /**
     * 分页查询系统管理用户数据集合
     *
     * @param pageNo   分页页码
     * @param pageSize 分页每页显示条数
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/paginationList")
    @ResponseBody
    public ServerResponse dataList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                   String roleId) {
        PageResponse pageResponse = systemUserService.queryByPagination(pageNo, pageSize, roleId);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 管理员用户信息
     *
     * @param id 管理员用户序列编号
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public ServerResponse queryById(String id) throws EqianyuanException {
        SystemUserBo systemUserBo = systemUserService.queryById(id);
        return new ServerResponse.ResponseBuilder().data(systemUserBo).build();
    }
}
