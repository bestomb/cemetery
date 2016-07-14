package com.bestomb.common.util;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 维护session信息
 * Created by jason on 2016-07-14.
 */
public class SessionContextUtil {

    /**
     * 私有化默认构造方法，禁止外部构建对象
     */
    private SessionContextUtil() {
        sessionMap = new HashMap<String, HttpSession>();
    }

    /**
     * 通过static构建类对象
     */
    private static SessionContextUtil instance;

    /**
     * 定义session缓存对象池
     * 所有对象均以K=V结构进行存储
     * K = sessionId
     * V = session对象
     */
    public Map<String, HttpSession> sessionMap;

    /**
     * 获取实例
     *
     * @return
     */
    public static SessionContextUtil getInstance() {
        if (instance == null) {
            instance = new SessionContextUtil();
        }
        return instance;
    }

    /**
     * 添加session
     *
     * @param session
     */
    public synchronized void addSession(HttpSession session) {
        if (!ObjectUtils.isEmpty(session)) {
            sessionMap.put(session.getId(), session);
        }
    }

    /**
     * 删除session
     *
     * @param session
     */
    public synchronized void delSession(HttpSession session) {
        if (!ObjectUtils.isEmpty(session)) {
            sessionMap.remove(session.getId());
        }
    }

    /**
     * 获取session
     *
     * @param sessionId
     * @return
     */
    public synchronized HttpSession getSession(String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return null;
        }
        return sessionMap.get(sessionId);
    }
}
