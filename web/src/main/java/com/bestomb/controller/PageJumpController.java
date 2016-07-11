package com.bestomb.controller;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.systemMenu.SystemMenuBo;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.ISystemMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 页面跳转
 * 主要功能：
 * 通过后台方法跳转到界面
 * <p>
 * Created by jason on 2016-05-17.
 */
@Controller
@RequestMapping("/system-manage")
public class PageJumpController extends BaseController {

    @Autowired
    private ISystemMenuService systemMenuService;

    /**
     * 进入后台首页
     *
     * @return
     */
    @RequestMapping("/home")
    public String index() throws EqianyuanException {
        if (ObjectUtils.isEmpty(SessionUtil.getAttribute(SystemConf.SYSTEM_SESSION_USER.toString()))) {
            return SystemConf.SYSTEM_MANAGE_LOGIN_BY_PAGE.toString();
        }

        //获取系统菜单数据集合
        List<SystemMenuBo> systemMenuByQueryResponses = systemMenuService.getList();
        SessionUtil.setAttribute(SystemConf.SYSTEM_MENU_BY_USER.toString(), systemMenuByQueryResponses);
        return SystemConf.SYSTEM_MANAGE_HOME_BY_PAGE.toString();
    }

    /**
     * 公共页面跳转
     * 不带数据
     *
     * @param url 目的页面位置
     * @return
     * @throws Exception
     */
    @RequestMapping("/gotoPage")
    public String gotoPage(@RequestParam(name = "url") String url) throws Exception {
        if (StringUtils.isEmpty(url)) {
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
        }
        return url;
    }

//    /**
//     * 公共页面跳转
//     * 带数据
//     *
//     * @param url 目的页面位置
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/jumpPage")
//    public String gotoPage(@RequestParam(name = "url") String url,
//                           HttpServletRequest request) throws Exception {
//        if (StringUtils.isEmpty(url)) {
//            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
//        }
//
//        //获取请求中所有的key
//        Enumeration enumeration = request.getParameterNames();
//        Map<String, Object> reqMap = new HashMap<String, Object>();
//        while (enumeration.hasMoreElements()) {
//            String key = (String) enumeration.nextElement();
//            if (StringUtils.equals("url", key)) {
//                continue;
//            }
//
//            reqMap.put(key, request.getParameter(key));
//        }
//
//        request.setAttribute("reqMap", reqMap);
//        return url;
//    }

}
