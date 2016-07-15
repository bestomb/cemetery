package com.bestomb.controller.api;

import com.bestomb.common.request.cemetery.CemeteryByEditRequest;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前台陵园接口控制器
 * Created by jason on 2016-07-15.
 */
@Controller
@RequestMapping("/website/cemetery_api")
public class WebsiteCemeteryController {

    /**
     * 创建陵园
     *
     * @param cemeteryByEditRequest
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public ServerResponse create(CemeteryByEditRequest cemeteryByEditRequest) {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByCookie()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        cemeteryByEditRequest.setMemberId(String.valueOf(memberLoginVo.getMemberId()));
        return null;
    }
}
