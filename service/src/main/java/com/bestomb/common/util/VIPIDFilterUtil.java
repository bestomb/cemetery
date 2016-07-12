package com.bestomb.common.util;


import org.springframework.util.ObjectUtils;

/**
 * 会员账号过滤器
 * Created by yoho on 2016/3/4.
 */
public class VIPIDFilterUtil {

    /**
     * 过滤当前编号是否为靓号
     *
     * @param id
     * @return
     */
    public static boolean doFilter(String id) {
        //升序顺序
        if (orderASCRule(id, 0, 0)) {
            return true;
        }
        //降序顺序
        if (orderDESCRule(id, 0, 0)) {
            return true;
        }
        //同号
        if (sameRule(id, 0, 0)) {
            return true;
        }
        return false;
    }

    /**
     * 升序顺序规则
     * 过滤：123456这类数据
     *
     * @param id          完整数据编号
     * @param index       递归时字节数据在ID中的位置
     * @param indexNumber 递归时字节编号数据
     * @return
     */
    public static boolean orderASCRule(String id, int index, int indexNumber) {
        if (ObjectUtils.isEmpty(id)) {
            return false;
        }

        if (id.length() <= index) {
            return true;
        }

        if (index > 0) {
            //升序递归检查
            if (Integer.parseInt(id.charAt(index) + "") - indexNumber != 1) {
                return false;
            }
        }

        return orderASCRule(id, index + 1, Integer.parseInt(id.charAt(index) + ""));
    }

    /**
     * 降序顺序规则
     * 过滤：654321这类数据
     *
     * @param id          完整数据编号
     * @param index       递归时字节数据在ID中的位置
     * @param indexNumber 递归时字节编号数据
     * @return
     */
    public static boolean orderDESCRule(String id, int index, int indexNumber) {
        if (ObjectUtils.isEmpty(id)) {
            return false;
        }

        if (id.length() <= index) {
            return true;
        }

        if (index > 0) {
            //升序递归检查
            if (indexNumber - Integer.parseInt(id.charAt(index) + "") != 1) {
                return false;
            }
        }

        return orderDESCRule(id, index + 1, Integer.parseInt(id.charAt(index) + ""));
    }

    /**
     * 同号规则
     * 过滤:111111、222222类的数据
     *
     * @param id
     * @param index
     * @param indexNumber
     * @return
     */
    public static boolean sameRule(String id, int index, int indexNumber) {
        if (ObjectUtils.isEmpty(id)) {
            return false;
        }

        if (id.length() <= index) {
            return true;
        }

        if (index > 0) {
            if (indexNumber != Integer.parseInt(id.charAt(index) + "")) {
                return false;
            }
        }

        return sameRule(id, index + 1, Integer.parseInt(id.charAt(index) + ""));
    }
}
