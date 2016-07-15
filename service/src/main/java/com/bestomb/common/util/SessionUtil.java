package com.bestomb.common.util;

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

    public static Object getAttribute(HttpSession session, String name) {
        return session.getAttribute(name);
    }

    public static void setAttribute(String name, Object value) {
        getSession().setAttribute(name, value);
    }

    private static HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    public static void removeAttribute(String key) {
        getSession().removeAttribute(key);
    }
}
