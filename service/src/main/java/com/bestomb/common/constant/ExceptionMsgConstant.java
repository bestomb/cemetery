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
    //面向前端接口，sessionId为空
    public static final String WEB_SITE_SESSION_ID_IS_EMPTY = "10000023";
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
    
    /***** 字典 *****/
    // 字典类型为空
    public static final String DICTTYPE_IS_EMPTY = "10040001";
    // 字典代码为空
    public static final String DICTCODE_IS_EMPTY = "10040002";
    // 字典数据不存在
    public static final String DICTDATA_IS_NOT_EXISTS= "10040003";
    
    /***** 字典 *****/
    
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
    // 会员交易币为空
    public static final String TRADING_IS_EMPTY = "20000011";
    /***** 会员 *****/
    
    /***** 订单 *****/
    // 订单编号为空
    public static final String ORDERID_IS_EMPTY = "20001001";
    // 订单已过期
    public static final String ORDER_IS_OVERDUE = "20001002";
    // 订单详情商品数据不存在
    public static final String ORDER_GOODS_IS_NOT_EXISTS = "20001003";
    // 订单数据不存在
    public static final String ORDER_IS_NOT_EXISTS = "20001004";
    /***** 订单 *****/
    
    
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
    //陵园访问密码错误
    public static final String CEMETERY_ACCESS_PASSWORD_ERROR = "20010018";
    //陵园公告内容为空
    public static final String CEMETERY_NOTICE_CONTENT_IS_EMPTY = "20010019";
    //不是园主或未获得园主授权
    public static final String IS_NO_MASTER_BY_CEMETERY = "20010020";
    //陵园公告编号为空
    public static final String CEMETERY_NOTICE_ID_IS_EMPTY = "20010021";
    //陵园公告数据不存在
    public static final String CEMETERY_NOTICE_DATA_NOT_EXISTS = "20010022";
    //陵园墓碑信息ID为空
    public static final String CEMETERY_TOMBSTONE_INFORMATION_ID_IS_EMPTY = "20010023";
    //陵园可用空间不足
    public static final String CEMETERY_STORAGE_SIZE_INSUFFICIENT = "20010024";
    //陵园墓碑不存在
    public static final String CEMETERY_TOMBSTONE_DATA_NOT_EXISTS = "20010025";
    //陵园墓碑修改-减少纪念空间是被禁止的行为
    public static final String CEMETERY_TOMBSTONE_REDUCE_SIZE_IS_PROHIBITED = "20010026";
    //墓碑纪念人不存在
    public static final String CEMETERY_MASTER_DATA_NOT_EXISTS = "20010027";
    //墓碑纪念人祭文悼词不存在
    public static final String CEMETERY_EULOGY_DATA_NOT_EXISTS = "20010028";
    //墓碑纪念人相片不存在
    public static final String CEMETERY_PHOTO_DATA_NOT_EXISTS = "20010029";
    //墓碑纪念人视频不存在
    public static final String CEMETERY_VIDEO_DATA_NOT_EXISTS = "20010030";
    //墓碑纪念人作品不存在
    public static final String CEMETERY_LIFE_WORKS_DATA_NOT_EXISTS = "20010031";
    /***** 陵园 *****/
    
    /***** 留言 *****/
    // 留言ID为空
    public static final String MESSAGEID_IS_EMPTY = "20011001";
    // 该留言ID数据不存在
    public static final String MESSAGEID_IS_NOT_EXSITS = "20011002";
    /***** 留言 *****/
    
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
    
    /*****
     * 商城
     */
    // 商城类型不能为空（1：官方；2：个人）
    public static final String MALLTYPE_IS_EMPTY = "30000001";
    
    /******
     * 商品
     */
    // 商品ID为空
    public static final String GOODSID_IS_EMPTY = "30001001";
    // 商品数据不存在
    public static final String GOODS_DATA_NOT_EXISTS = "30001002";
    // 发布的商品不在背包中
    public static final String GOODS_IS_NOTIN_BACKPACK = "30001003";
    // 发布的商品数量为空
    public static final String GOODS_COUNT_IS_EMPTY = "30001004";
    // 发布的商品名称为空
    public static final String GOODS_NAME_IS_EMPTY = "30001005";
    // 发布的商品价格为空
    public static final String GOODS_PRICE_IS_EMPTY = "30001006";
    // 发布的商品数量超过背包商品数量
    public static final String GOODS_COUNT_IS_NOT_ENOUGH = "30001007";
    //商品发布失败
    public static final String GOODS_PUBLISH_FAIL = "30001008";
    //自定义分类数据不存在
    public static final String CUSTOM_CLASSIFY_DATA_NOT_EXISTS = "30001009";
    /***
     * 动植物
     */
    // 生物类型为空
    public static final String BIONTTYPE_IS_EMPTY = "30002001";
    // 动植物不存在
    public static final String BIONT_DATA_NOT_EXISTS = "30002002";
    
    /***
     * 购物车
     */
    // 购物车主键ID为空
    public static final String CARTID_IS_EMPTY = "30003001";
    // 购物车为空
    public static final String SHOPPINGCART_IS_EMPTY = "30003002";
    // 加入购物车时商品数量为空
    public static final String COUNT_IS_EMPTY = "30003003";
    // 加入购物车时商品总价格为空
    public static final String PRICE_IS_EMPTY = "30003004";
    
    /*****
     * 商品分类
     *****/
    //商品分类父分类编号为空
    public static final String GOODS_CLASSIFY_PARENT_ID_IS_EMPTY = "30010001";
    //商品分类数据不存在
    public static final String GOODS_CLASSIFY_DATA_NOT_EXISTS = "30010002";
    //商品分类名不存在
    public static final String GOODS_CLASSIFY_NAME_IS_EMPTY = "30010003";
    //商品分类名称内容太长
    public static final String GOODS_CLASSIFY_NAME_TO_LONG = "30010004";
    //商品分类编号为空
    public static final String GOODS_CLASSIFY_ID_IS_EMPTY = "30010005";
    

    /*****
     * 特殊节日
     *****/
    //特殊节日ID为空
    public static final String SPECIAL_HOLIDAY_ID_IS_EMPTY = "40010001";
    //特殊节日推送语内容太长
    public static final String HOLIDAY_PUSH_LANGUAGE_CONTENT_IS_TOO_LONG = "40010002";

    /*****
     * 公众人物管理
     *****/
    //公众人物信息ID为空
    public static final String PUBLIC_FIGURES_INFORMATION_ID_IS_EMPTY = "50010001";
    //公众人物状态为空
    public static final String PUBLIC_FIGURES_INFORMATION_Status_IS_EMPTY = "50010002";

/********** 客户端 **********/
}
