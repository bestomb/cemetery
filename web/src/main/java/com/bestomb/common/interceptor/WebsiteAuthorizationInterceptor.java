package com.bestomb.common.interceptor;

import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 网站会员授权校验
 * Created by jason on 2016/1/9.
 */
public class WebsiteAuthorizationInterceptor implements HandlerInterceptor {

    /**
     * 校验当前会话用户是否已经登录并且存在有效session
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String sessionId = SessionUtil.getSessionByCookie();
        if (StringUtils.isEmpty(sessionId)) {
            return falseByInterceptor(httpServletRequest, httpServletResponse);
        }

        //从session维护对象中获取当前sessionId所对应的session对象
        if (ObjectUtils.isEmpty(SessionContextUtil.getInstance().getSession(sessionId))) {
            //如果sessionId不存在session
            return falseByInterceptor(httpServletRequest, httpServletResponse);
        }

        /**
         * 从会话中获取系统用户信息
         * 如果用户信息为空，则返回异常状态，否则继续向下执行
         */
        if (ObjectUtils.isEmpty(SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(sessionId), SystemConf.WEBSITE_SESSION_MEMBER.toString()))) {
            return falseByInterceptor(httpServletRequest, httpServletResponse);
        }

        return true;
    }

    /**
     * 拦截结果为false时的结果响应
     *
     * @param request
     * @param response
     * @return
     */
    private boolean falseByInterceptor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + SystemConf.WEBSITE_UN_LOGIN.toString());
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
