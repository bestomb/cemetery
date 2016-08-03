package com.bestomb.common.listener;

import com.bestomb.common.util.socket.ServerSocketByCemetery;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 以陵园为分组单位的服务端通信监听
 * Created by jason on 2016-08-01.
 */
public class ServerSocketyByCemeteryListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //打开服务端通信
        ServerSocketByCemetery.getServerSocketByCemetery(servletContextEvent).openServerSocket();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //关闭服务端通信
        ServerSocketByCemetery.getServerSocketByCemetery(servletContextEvent).closeServerSocket();
    }
}
