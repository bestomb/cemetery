package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.systemRole.SystemRoleByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.systemRole.SystemRoleBo;
import com.bestomb.service.ISystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统管理角色控制器
 * Created by jason on 2016-07-08.
 */
@Controller
@RequestMapping("/system_role")
public class SystemRoleController extends BaseController {

    @Autowired
    private ISystemRoleService systemRoleService;

    /**
     * 获取系统角色集合
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/list")
    @ResponseBody
    public ServerResponse getList() throws EqianyuanException {
        List<SystemRoleBo> systemRoleBos = systemRoleService.getList();
        return new ServerResponse.ResponseBuilder().data(systemRoleBos).build();
    }

    /**
     * 添加角色
     *
     * @param systemRoleByEditRequest
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(SystemRoleByEditRequest systemRoleByEditRequest) throws EqianyuanException {
        systemRoleService.add(systemRoleByEditRequest);
        return new ServerResponse();
    }

    /**
     * 角色信息删除
     *
     * @param id 角色编号
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(String... id) throws EqianyuanException {
        systemRoleService.removeByIds(id);
        return new ServerResponse();
    }

    /**
     * 角色信息修改
     *
     * @param systemRoleByEditRequest
     * @return
     */
    @RequestMapping("/modify")
    @ResponseBody
    public ServerResponse modify(SystemRoleByEditRequest systemRoleByEditRequest) throws EqianyuanException {
        systemRoleService.modify(systemRoleByEditRequest);
        return new ServerResponse();
    }

    /**
     * 分页查询角色数据集合
     *
     * @param pageNo   分页页码
     * @param pageSize 分页每页显示条数
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/paginationList")
    @ResponseBody
    public ServerResponse dataList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageResponse pageResponse = systemRoleService.queryByPagination(pageNo, pageSize);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 角色信息
     *
     * @param id 角色编号
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public ServerResponse queryById(String id) throws EqianyuanException {
        SystemRoleBo systemRoleBo = systemRoleService.queryById(id);
        return new ServerResponse.ResponseBuilder().data(systemRoleBo).build();
    }
}
