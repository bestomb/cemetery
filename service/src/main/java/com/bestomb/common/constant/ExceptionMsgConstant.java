package com.bestomb.common.constant;

/**
 * 自定义异常错误消息静态常量类
 * Created by jason on 2016-05-19.
 */
public class ExceptionMsgConstant {

/********** 管理系统 **********/
    /***** 系统 *****/
    //系统错误
    public static final String SYSTEM_ERROR = "10000001";
    //缺少请求参数
    public static final String SYSTEM_LACK_OF_REQUEST_PARAMETER = "10000002";
    //获取字符串字节错误
    public static final String SYSTEM_GET_BYTE_FAIL = "10000003";
    //验证码内容超长
    public static final String VALIDATA_CODE_CONTENT_LENGTH_TO0_LONG = "10000004";
    //验证码是空
    public static final String VALIDATA_CODE_IS_EMPTY = "10000005";
    //验证码校验错误
    public static final String VALIDATA_CODE_VALIDATION_ERROR = "10000006";
    //获取配置文件信息错误
    public static final String GET_CONFIGURATION_ERROR = "10000007";
    //验证码发送时间间隔小于60s
    public static final String VALIDATA_CODE_INTERVAL_INSUFFICIENT = "10000008";
    /***** 系统 *****/

    /***** 系统用户管理 *****/
    //系统管理用户编号为空
    public static final String SYSTEM_USER_ID_IS_EMPTY = "10010001";
    //系统管理登录用户名为空
    public static final String SYSTEM_USER_LOGIN_NAME_IS_EMPTY = "10010002";
    //系统管理密码为空
    public static final String SYSTEM_USER_LOGIN_PASSWORD_IS_EMPTY = "10010003";
    //系统管理员真实姓名为空
    public static final String SYSTEM_USER_REAL_NAME_IS_EMPTY = "10010004";
    //系统管理员手机号码为空
    public static final String SYSTEM_USER_MOBILE_IS_EMPTY = "10010005";
    //系统管理员登录用户名内容太长
    public static final String SYSTEM_USER_LOGIN_NAME_TO_LONG = "10010006";
    //系统管理员真实姓名内容太长
    public static final String SYSTEM_USER_REAL_NAME_TO_LONG = "10010007";
    //用户名或密码错误
    public static final String LOGIN_USER_NAME_OR_PASSWORD_ERROR = "10010008";
    //系统管理用户对象为空
    public static final String LOGIN_USER_OBJECT_IS_EMPTY = "10010009";
    /***** 系统用户管理 *****/

    /***** 系统菜单管理 *****/
    //系统菜单菜单数据不存在
    public static final String SYSTEM_MENU_DATA_NOT_EXISTS = "10020001";
    /***** 系统菜单管理 *****/

    /***** 系统角色管理 *****/
    //系统角色数据不存在
    public static final String SYSTEM_ROLE_DATA_NOT_EXISTS = "10030001";
    //系统角色名不存在
    public static final String SYSTEM_ROLE_NAME_IS_EMPTY = "10030002";
    //系统角色名称内容太长
    public static final String SYSTEM_ROLE_NAME_TO_LONG = "10030003";
    //系统角色备注内容太长
    public static final String SYSTEM_ROLE_REMARK_TO_LONG = "10030004";
    //系统角色编号为空
    public static final String SYSTEM_ROLE_ID_IS_EMPTY = "10030005";
    /***** 系统角色管理 *****/
/********** 管理系统 **********/

/********** 客户端 **********/
    /***** 会员 *****/
    //手机号码为空
    public static final String MOBILE_IS_EMPTY = "20000001";
    //手机号码不是正确号码
    public static final String MOBILE_IS_NOT_CORRECT = "20000002";
    //手机号码已经注册
    public static final String MOBILE_IS_ALREADY_REGISTER = "20000003";
    /***** 会员 *****/
/********** 客户端 **********/
}
