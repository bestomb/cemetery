package com.bestomb.controller.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.cemetery.CemeteryByEditRequest;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsiteCemeteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前台陵园接口控制器
 * Created by jason on 2016-07-15.
 */
@Controller
@RequestMapping("/website/cemetery_api")
public class WebsiteCemeteryController extends BaseController {

    @Autowired
    private WebsiteCemeteryService websiteCemeteryService;

    /**
     * 创建陵园
     *
     * @param cemeteryByEditRequest
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public ServerResponse create(CemeteryByEditRequest cemeteryByEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByCookie()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        cemeteryByEditRequest.setMemberId(String.valueOf(memberLoginVo.getMemberId()));
        websiteCemeteryService.create(cemeteryByEditRequest);
        return new ServerResponse();
    }
}
