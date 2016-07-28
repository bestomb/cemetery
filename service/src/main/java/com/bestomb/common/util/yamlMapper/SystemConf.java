package com.bestomb.common.util.yamlMapper;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 系统静态配置信息枚举类
 * <p>
 * Created by jason on 2016-06-02.
 */
public enum SystemConf {

    /**
     * 系统维护的线程池
     * 计算公式：单个CPU处理的线程数 * 设备可用有效CPU个数
     */
    POOL_BY_THREAD_SIZE(Executors.newFixedThreadPool(10 * Runtime.getRuntime().availableProcessors())),
    /**
     * 系统用户session对象
     */
    SYSTEM_SESSION_USER("systemUser"),
    /**
     * 系统用户菜单
     */
    SYSTEM_MENU_BY_USER("systemMenuByUser"),
    /**
     * 网站会员session对象
     */
    WEBSITE_SESSION_MEMBER("websiteMember"),
    /**
     * 验证码sessionkey
     */
    VERIFY_CODE("verifyCode"),
    /**
     * 成功发送短信验证码信息
     */
    SEND_SMS_BY_BATCHSEND2("sendSMSByBatchSend2"),
    /**
     * 平台字符集
     */
    PLATFORM_CHARSET("UTF-8"),

    /**
     * 系统登录页面位置
     */
    SYSTEM_MANAGE_LOGIN_BY_PAGE("login"),
    SYSTEM_MANAGE_HOME_BY_PAGE("home"),
    /**
     * 网站未登录时响应请求地址
     */
    WEBSITE_UN_LOGIN("/website/authorization"),
    /**
     * 模型附件上传临时目录
     */
    MODEL_FILE_UPLOAD_TEMP_DIRECTORY(File.separator + "upload" + File.separator + "temp"),
    /**
     * 模型附件上传最终持久目录
     */
    MODEL_FILE_UPLOAD_FIXED_DIRECTORY(File.separator + "upload" + File.separator + "fixed");

    private Object value;

    SystemConf(Object value) {
        this.value = value;
    }

    /**
     * 获取系统维护的线程池对象
     *
     * @return
     */
    public static ExecutorService getPoolByThreadSize() {
        return (ExecutorService) SystemConf.POOL_BY_THREAD_SIZE.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
