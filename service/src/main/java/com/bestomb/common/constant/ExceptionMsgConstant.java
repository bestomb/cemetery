package com.bestomb.common.constant;

/**
 * 自定义异常错误消息静态常量类
 * Created by jason on 2016-05-19.
 */
public class ExceptionMsgConstant {

/********** 管理系统 **********/
    /*****
     * 系统
     *****/
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
    //地区-省数据不存在
    public static final String AREA_PROVINCE_DATA_NO_EXISTS = "10000009";
    //地区-省数据编号为空
    public static final String AREA_PROVINCE_ID_IS_EMPTY = "10000010";
    //地区-市数据不存在
    public static final String AREA_CITY_DATA_NO_EXISTS = "10000011";
    //地区-市数据编号为空
    public static final String AREA_CITY_ID_IS_EMPTY = "10000012";
    //地区-区数据不存在
    public static final String AREA_COUNTY_DATA_NO_EXISTS = "10000013";
    //地区-乡数据不存在
    public static final String AREA_TOWN_DATA_NO_EXISTS = "10000014";
    //地区-村数据不存在
    public static final String AREA_VILLAGE_DATA_NO_EXISTS = "10000015";
    //地区-社数据不存在
    public static final String AREA_COMMUNITY_DATA_NO_EXISTS = "10000016";
    //分页-页码为空
    public static final String PAGINATION_PAGE_NO_IS_EMPTY = "10000017";
    //附件名称为空
    public static final String FILE_NAME_IS_EMPTY = "10000018";
    //附件移动错误
    public static final String FILE_MOVE_ERROR = "10000019";
    //附件不存在
    public static final String FILE_NO_EXISTS = "10000020";
    //附件删除错误
    public static final String FILE_DELETE_ERROR = "10000021";
    //附件上传失败
    public static final String FILE_UPDATE_ERROR = "10000022";
    /***** 系统 *****/

    /*****
     * 系统用户管理
     *****/
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

    /*****
     * 系统菜单管理
     *****/
    //系统菜单菜单数据不存在
    public static final String SYSTEM_MENU_DATA_NOT_EXISTS = "10020001";
    /***** 系统菜单管理 *****/

    /*****
     * 系统角色管理
     *****/
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
    /*****
     * 会员
     *****/
    //手机号码为空
    public static final String MOBILE_IS_EMPTY = "20000001";
    //手机号码不是正确号码
    public static final String MOBILE_IS_NOT_CORRECT = "20000002";
    //手机号码已经注册
    public static final String MOBILE_IS_ALREADY_REGISTER = "20000003";
    //登录密码为空
    public static final String LOGIN_PASSWORD_IS_EMPTY = "20000004";
    //确认密码为空
    public static final String CONFIRM_PASSWORD_IS_EMPTY = "20000005";
    //两次密码不一致
    public static final String TWO_PASSWORD_DO_NOT_MATCH = "20000006";
    //登录账号为空
    public static final String LOGIN_ACCOUNT_IS_EMPTY = "20000007";
    //账号或密码错误
    public static final String LOGIN_ACCOUNT_OR_PASSWORD_ERROR = "20000008";
    //会员未获得登录授权
    public static final String MEMBER_NO_AUTHORIZATION_BY_LOGIN = "20000009";
    //会员编码为空
    public static final String MEMBERSHIP_NUMBER_IS_EMPTY = "20000010";
    /***** 会员 *****/

    /*****
     * 陵园
     *****/
    //名称为空
    public static final String CEMETERY_NAME_IS_EMPTY = "20010001";
    //省为空
    public static final String CEMETERY_PROVINCE_IS_EMPTY = "20010002";
    //市为空
    public static final String CEMETERY_CITY_IS_EMPTY = "20010003";
    //区为空
    public static final String CEMETERY_COUNTY_IS_EMPTY = "20010004";
    //乡为空
    public static final String CEMETERY_TOWN_IS_EMPTY = "20010005";
    //村为空
    public static final String CEMETERY_VILLAGE_IS_EMPTY = "20010006";
    //社为空
    public static final String CEMETERY_COMMUNITY_IS_EMPTY = "20010007";
    //访问密码为空
    public static final String CEMETERY_PASSWORD_IS_EMPTY = "20010008";
    //陵园名称内容太长
    public static final String CEMETERY_NAME_TO_LONG = "20010009";
    //陵园密码内容太长
    public static final String CEMETERY_PASSWORD_TO_LONG = "20010010";
    //陵园乡地址内容太长
    public static final String CEMETERY_TOWN_NAME_TO_LONG = "20010011";
    //陵园村地址内容太长
    public static final String CEMETERY_VILLAGE_NAME_TO_LONG = "20010012";
    //陵园社地址内容太长
    public static final String CEMETERY_COMMUNITY_NAME_TO_LONG = "20010013";
    //陵园创建总数超出会员许可总数
    public static final String CEMETERY_CONSTRUCTION_COUNT_TO_LONG = "20010014";
    //陵园编号为空
    public static final String CEMETERY_ID_IS_EMPTY = "20010015";
    //陵园数据不存在
    public static final String CEMETERY_DATA_NOT_EXISTS = "20010016";
    //社区任意门行为方式为空
    public static final String CEMETERY_BEHAVIOR_IS_EMPTY = "20010017";
    /***** 陵园 *****/

    /*****
     * 模型
     *****/
    //模型分类编号为空
    public static final String MODEL_CLASSIFY_ID_IS_EMPTY = "20020001";
    //模型分类名不存在
    public static final String MODEL_CLASSIFY_NAME_IS_EMPTY = "20020002";
    //模型分类名称内容太长
    public static final String MODEL_CLASSIFY_NAME_TO_LONG = "20020003";
    //模型分类父分类编号为空
    public static final String MODEL_CLASSIFY_PARENT_ID_IS_EMPTY = "20020004";
    //模型分类数据不存在
    public static final String MODEL_CLASSIFY_DATA_NOT_EXISTS = "20020005";

    //模型编号为空
    public static final String MODEL_ID_IS_EMPTY = "20020006";
    //模型名不存在
    public static final String MODEL_NAME_IS_EMPTY = "20020007";
    //模型名称内容太长
    public static final String MODEL_NAME_TO_LONG = "20020008";
    //模型文件地址不存在
    public static final String MODEL_FILE_ADDRESS_IS_EMPTY = "20020009";
    //模型文件名称不存在
    public static final String MODEL_FILE_NAME_IS_EMPTY = "20020010";
    //模型数据不存在
    public static final String MODEL_DATA_NOT_EXIXTS = "20020011";
    /***** 模型 *****/
/********** 客户端 **********/
}
