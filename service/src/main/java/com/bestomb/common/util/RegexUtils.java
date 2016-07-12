package com.bestomb.common.util;

import java.util.regex.Pattern;

/**
 * 常用正则表达式工具类
 * Created by jason on 2016-07-11.
 */
public class RegexUtils {

    /**
     * 是否为手机号码
     *
     * @param mobileNumber 手机号码
     * @return
     */
    public static boolean isMobile(String mobileNumber) {
        Pattern pattern = Pattern.compile("(13|14|15|18){1}[\\d]{9}");
        return pattern.matcher(mobileNumber).matches();
    }
}
