package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.publicFigure.PublicFigureRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.PublicFigure.PublicFigureBo;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.systemUser.SystemUserVo;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.IPublicFigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys_publfigures")
public class PublicFiguresController extends BaseController {

    @Autowired
    private IPublicFigureService publicFigureService;

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/publifigureslist")
    @ResponseBody
    public ServerResponse getList(String status,
                                  @RequestParam(value = "pageNo" , required = false, defaultValue = "1") int pageNo,
                                  @RequestParam(value = "pageSize",required = false, defaultValue = "10") int pageSize) throws EqianyuanException {
        PageResponse pageResponse = publicFigureService.getList(pageNo,pageSize,status);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 详细查询
     * @param id
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/info")
    @ResponseBody
    public ServerResponse queryById(String id) throws EqianyuanException {
        PublicFigureBo publicFigureBo = publicFigureService.queryById(id);
        return new ServerResponse.ResponseBuilder().data(publicFigureBo).build();
    }

    /**
     * 修改
     * @param publicFigureRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/modify")
    @ResponseBody
    public ServerResponse modify(PublicFigureRequest publicFigureRequest) throws EqianyuanException {
        /**
         * 从会话中获取系统用户信息
         * 如果用户信息为空，则返回登录页面，否则继续向下执行
         */
        SystemUserVo systemUserVo = (SystemUserVo) SessionUtil.getAttribute(SystemConf.SYSTEM_SESSION_USER.toString());

        publicFigureRequest.setSystemUser(systemUserVo.getId());
        publicFigureService.modify(publicFigureRequest);
        return new ServerResponse();
    }

}
