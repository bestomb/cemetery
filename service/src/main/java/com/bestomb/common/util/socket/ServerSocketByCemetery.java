package com.bestomb.common.util.socket;


import com.bestomb.common.util.YamlForMapHandleUtil;
import com.bestomb.common.util.yamlMapper.ClientConf;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.ICemeteryDao;
import com.bestomb.entity.Cemetery;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.ServletContextEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * 处理多人在线消息服务端通信套接字
 * Created by jason on 2016-08-01.
 */
public class ServerSocketByCemetery {

    private Logger logger = Logger.getLogger(this.getClass());

    private volatile static ServerSocketByCemetery serverSocketByCemetery;
    private ServerSocket serverSocket;
    //以陵园为单位，已连接通信的客户端集合
    private static ConcurrentMap<String, ConcurrentLinkedQueue<Socket>> clientMapByCemetery = new ConcurrentHashMap<String, ConcurrentLinkedQueue<Socket>>();

    private ICemeteryDao cemeteryDao;

    private ServerSocketByCemetery(ServletContextEvent servletContextEvent) {
        WebApplicationContext appctx = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        cemeteryDao = (ICemeteryDao) appctx.getBean("ICemeteryDao");
    }

    /**
     * 双重检查单利，获取实例对象
     *
     * @return
     */
    public static ServerSocketByCemetery getServerSocketByCemetery(ServletContextEvent servletContextEvent) {
        if (serverSocketByCemetery == null) {
            synchronized (ServerSocketByCemetery.class) {
                if (serverSocketByCemetery == null) {
                    serverSocketByCemetery = new ServerSocketByCemetery(servletContextEvent);
                }
            }
        }
        return serverSocketByCemetery;
    }

    /**
     * 启动服务端套接字
     */
    public void openServerSocket() {
        if (serverSocket == null) {
            synchronized (this.getClass()) {
                if (serverSocket == null) {
                    Yaml yaml = new Yaml();

                    /**
                     * 如果配置文件在jar包中，则需要使用流加载方式读取
                     */
                    Map<String, Object> map = yaml.loadAs(ServerSocketByCemetery.class.getResourceAsStream("/client-conf.yaml"), Map.class);

                    try {
                        String addressByIp = String.valueOf(YamlForMapHandleUtil.getMapByKey(map, ClientConf.SocketByMsg.serverSocketByMsgAddress.toString()));

                        byte[] ipBytes = new byte[4];
                        String[] addressByIpSplit = addressByIp.split(".");
                        for (int i = 0; i < addressByIpSplit.length; i++) {
                            ipBytes[i] = Byte.parseByte(addressByIpSplit[i]);
                        }

                        serverSocket = new ServerSocket(Integer.parseInt(String.valueOf(YamlForMapHandleUtil.getMapByKey(map, ClientConf.SocketByMsg.serverSocketByMsgPort.toString())))
                                , Integer.parseInt(String.valueOf(YamlForMapHandleUtil.getMapByKey(map, ClientConf.SocketByMsg.serverSocketByMsgBackLog.toString())))
                                , InetAddress.getByAddress(ipBytes));
                        logger.info("通信服务创建成功");
                        //开始监听客户端
                        clientAccept();
                    } catch (IOException e) {
                        logger.error(" 准备通信服务对象时失败", e);
                    }
                }
            }
        }
    }

    /**
     * 关闭服务端套接字
     */
    public void closeServerSocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.error("套接字服务端被关闭", e);
        }
    }

    /**
     * 监听客户端连接
     */
    public void clientAccept() {
        SystemConf.getPoolByThreadSize().execute(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        //从客户端通信列队中获取一个建立通信连接
                        Socket client = serverSocket.accept();
                        //将客户端加入到陵园组中
                        disposeMsg(client);
                    } catch (IOException e) {
                        logger.warn("监听获取客户端连接失败", e);
                    }
                }
            }
        });
    }

    /**
     * 消息处理
     */
    private void disposeMsg(final Socket client) {
        SystemConf.getPoolByThreadSize().execute(new Runnable() {
            public void run() {
                InputStreamReader isr = null;
                BufferedReader br = null;
                OutputStreamWriter osw = null;
                BufferedWriter bw = null;
                //从客户端请求中提取的陵园编号
                String cemeteryId = null;
                try {
                    //将客户端输入流转为字符流
                    isr = new InputStreamReader(client.getInputStream());
                    br = new BufferedReader(isr);

                    //客户端每一行请求内容
                    String strByline;
                    //获取客户端输入
                    strByline = br.readLine();
                    //提取陵园编号
                    if (!StringUtils.containsIgnoreCase(String.valueOf(strByline), "cibs")) {
                        logger.warn("通信连接建立失败，因为通信中没有认证信息【cibs（陵园编号开始位）】");
                        client.close();
                        return;
                    }

                    if (!StringUtils.containsIgnoreCase(String.valueOf(strByline), "cibe")) {
                        logger.warn("通信连接建立失败，因为通信中没有认证信息【cibe（陵园编号结束位）】");
                        client.close();
                        return;
                    }

                    cemeteryId = StringUtils.substring(String.valueOf(strByline), strByline.indexOf("cibs") + 4, strByline.indexOf("cibe"));

                    if (StringUtils.isEmpty(cemeteryId)) {
                        logger.warn("通信连接建立失败，因为通信中没有认证信息【陵园编号】");
                        client.close();
                        return;
                    }

                    osw = new OutputStreamWriter(client.getOutputStream());
                    bw = new BufferedWriter(osw);
                    PrintWriter pw = new PrintWriter(osw);
                    pw.println("协议连接建立成功");
                    pw.flush();

                    ConcurrentLinkedQueue<Socket> clients = clientMapByCemetery.get(cemeteryId);

                    //根据陵园编号检查客户端通信集合中，是否已经维护了当前陵园的通信组
                    if (CollectionUtils.isEmpty(clients)) {
                        //根据陵园编号获取陵园
                        Cemetery cemetery = cemeteryDao.selectByPrimaryKey(cemeteryId);
                        if (ObjectUtils.isEmpty(cemetery)
                                || ObjectUtils.isEmpty(cemetery.getId())) {
                            logger.info("客户端消息处理失败，因为找不到陵园");
                            return;
                        }

                        //将当前客户端加入到陵园组中
                        clients = new ConcurrentLinkedQueue<Socket>();
                        clients.add(client);

                        //开启并维护一个新的陵园通信组
                        clientMapByCemetery.put(cemeteryId, clients);
                    } else {
                        //已经存在当前陵园的通信组，加入客户端
                        clients.add(client);
                    }

                    while (!StringUtils.isEmpty(strByline = br.readLine())) {
                        //退出通信
                        if (StringUtils.containsIgnoreCase(strByline, "exit_dialogue")) {
                            break;
                        }

                        //获取最新的陵园通讯组客户端成员集合
                        clients = clientMapByCemetery.get(cemeteryId);

                        //遍历客户端，让每个客户端都接收一遍数据
                        for (Socket client : clients) {
                            osw = new OutputStreamWriter(client.getOutputStream());
                            bw = new BufferedWriter(osw);
                            pw = new PrintWriter(osw);
                            pw.println(String.valueOf(strByline));
                            pw.flush();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (!StringUtils.isEmpty(cemeteryId)) {
                        //将当前sokcet从客户端集合中移除
                        ConcurrentLinkedQueue<Socket> clients = clientMapByCemetery.get(cemeteryId);
                        clients.remove(client);

                        //当陵园中已经不存在任何通讯连接时，释放维护陵园资源
                        if (CollectionUtils.isEmpty(clients)) {
                            clientMapByCemetery.remove(cemeteryId);
                        }
                    }

                    closeInputStream(isr, br);
                    closeOutputStream(osw, bw);
                }
            }
        });
    }

    /**
     * 关闭输入流资源
     */
    private void closeInputStream(InputStreamReader isr, BufferedReader br) {
        if (!ObjectUtils.isEmpty(isr)) {
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                isr = null;
            }
        }

        if (!ObjectUtils.isEmpty(br)) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                br = null;
            }
        }
    }

    /**
     * 关闭输出流资源
     */
    private void closeOutputStream(OutputStreamWriter osw, BufferedWriter bw) {
        if (!ObjectUtils.isEmpty(osw)) {
            try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                osw = null;
            }
        }

        if (!ObjectUtils.isEmpty(bw)) {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                bw = null;
            }
        }
    }
}
