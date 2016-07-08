package com.bestomb.common.util;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * yaml for map文件工具
 * Created by jason on 2016/1/9.
 */
public class YamlForMapHandleUtil {

    /**
     * 获取MAP副本，避免主版本MAP被修改数据
     *
     * @param map
     * @return
     */
    protected static Map<String, Object> transcript(Map map) {
        return new HashMap<String, Object>(map);
    }

    /**
     * 转换key，让key符合yaml的key格式
     *
     * @param key
     * @return
     */
    protected static String converKey(String key) {
        try {
            Double.parseDouble(key);
            return new StringBuilder("[").append(key).append("]").toString();
        } catch (NumberFormatException e) {
            return key;
        }
    }

    /**
     * 根据KEY获取VALUE
     *
     * @param map
     * @param msgKey
     * @return
     */
    public static Object getMapByKey(Map<String, Object> map, String msgKey) {
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }

        if (StringUtils.isEmpty(msgKey)) {
            return null;
        }

        return map.get(converKey(msgKey));
    }

    /**
     * 根据key获取消息内容
     *
     * @param messageCode 消息错误码
     * @param key         消息KEY
     * @return
     */
    public static String getValueBykey(Map map, String messageCode, String key) {
        Object obj = getMapByKey(map, converKey(messageCode));
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }

        Object mapByKey = getMapByKey((Map<String, Object>) obj, key);
        return ObjectUtils.isEmpty(mapByKey) ? null : mapByKey.toString();
    }
}
