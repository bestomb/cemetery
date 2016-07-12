package com.bestomb.common.util;

import org.springframework.util.StringUtils;

/**
 * 字符串模板工具类
 * Created by jason on 2016-07-11.
 */
public class StringTemplateReplaceUtil {

    private static final String DEFAULT_PLACEHOLDER = "\\?";

    /**
     * 替换字符串模板占位符并返回最终内容
     *
     * @param templateStr    字符串模板
     * @param placeholder    占位符
     * @param replaceContent 待替换内容
     * @return
     */
    public static String getStr(String templateStr, String placeholder, Object... replaceContent) {
        if (StringUtils.isEmpty(templateStr)) {
            return null;
        }

        if (StringUtils.isEmpty(placeholder)) {
            placeholder = DEFAULT_PLACEHOLDER;
        }

        for (Object content : replaceContent) {
            templateStr = templateStr.replaceFirst(placeholder, String.valueOf(content));
        }

        return templateStr;
    }
}
