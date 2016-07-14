package com.bestomb.common.listener;

import com.bestomb.common.util.SessionContextUtil;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 系统session监听
 * Created by jason on 2016-07-14.
 */
public class WebsiteSessionListener implements HttpSessionListener {

    /**
     * 监听到session被创建
     *
     * @param httpSessionEvent
     */
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        SessionContextUtil.getInstance().addSession(httpSessionEvent.getSession());
    }

    /**
     * 监听到session被销毁
     *
     * @param httpSessionEvent
     */
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        SessionContextUtil.getInstance().delSession(httpSessionEvent.getSession());
    }
}
