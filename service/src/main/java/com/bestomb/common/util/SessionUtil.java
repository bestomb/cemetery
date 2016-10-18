package com.bestomb.common.util;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * session操作工具
 * Created by jason on 2016/1/11.
 */
public class SessionUtil {

    /**
     * 禁止多次构造对象
     */
    private SessionUtil() {
    }

    public static Object getAttribute(String name) {
        Object sessionObj = null;
        try {
            sessionObj = getSession().getAttribute(name);
        } catch (Exception e) {
            sessionObj = null;
        } finally {
            return sessionObj;
        }
    }

    /**
     * 从request请求header中获取JSESSIONID
     *
     * @return
     */
    public static String getSessionByHeader() {
        //从HEADER中获取jsessionid
        String sessionId = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("JSESSIONID");

        //如果header中没有sessionid，则从cookies中获取sessionid
        if (StringUtils.isEmpty(sessionId)) {
            //从cookies中获取jsessionid
            sessionId = getSessionByCookie();
        }

        if(StringUtils.isEmpty(sessionId)){
            return null;
        }

        //将sessionid写入到header中
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().addHeader("JSESSIONID", sessionId);
        return sessionId;
    }

    /**
     * 从cookie中获取sessionId
     *
     * @return
     */
    public static String getSessionByCookie() {
        Cookie[] cookies = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getCookies();

        if (ObjectUtils.isEmpty(cookies)) {
            return null;
        }

        //从httpRequest中获取sessionId
        String sessionId = null;
        for (Cookie cookie : cookies) {
            //判断请求header中cookie里是否存在JSESSIONID，如果不为空，则将JSESSIONID返回
            if (StringUtils.equals(cookie.getName(), "JSESSIONID")) {
                sessionId = cookie.getValue();
                //删除cookie
                cookie.setMaxAge(0);
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().addCookie(cookie);
                break;
            }
        }
        return sessionId;
    }

    /**
     * 获取客户端session
     *
     * @return
     * @throws EqianyuanException
     */
    public static HttpSession getClientSession() throws EqianyuanException {
        String sessionId = getSessionByHeader();
        if (StringUtils.isEmpty(sessionId)) {
            throw new EqianyuanException(ExceptionMsgConstant.WEB_SITE_SESSION_ID_IS_EMPTY);
        }

        //从session维护对象中获取当前sessionId所对应的session对象
        if (ObjectUtils.isEmpty(SessionContextUtil.getInstance().getSession(sessionId))) {
            //如果sessionId不存在session
            throw new EqianyuanException(ExceptionMsgConstant.MEMBER_NO_AUTHORIZATION_BY_LOGIN);
        }
        return SessionContextUtil.getInstance().getSession(sessionId);
    }

    public static Object getAttribute(HttpSession session, String name) {
        return session.getAttribute(name);
    }

    public static void setAttribute(String name, Object value) {
        setAttribute(getSession(), name, value);
    }

    public static void setAttribute(HttpSession session, String name, Object value) {
        session.setAttribute(name, value);
    }

    public static HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    public static void removeAttribute(String key) {
        removeAttribute(getSession(), key);
    }

    public static void removeAttribute(HttpSession session, String key) {
        session.removeAttribute(key);
    }
}
