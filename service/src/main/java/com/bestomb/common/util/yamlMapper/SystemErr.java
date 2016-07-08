package com.bestomb.common.util.yamlMapper;

import java.util.Map;

/**
 * 系统异常错误YAML配置信息枚举类
 * 主要用于初始加载yaml内容并缓存，定义yaml配置文件key
 * Created by jason on 2016-06-01.
 */
public class SystemErr {
    private static Map<String, Object> map;

    public static Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    /**
     * 利用枚举定义yaml配置文件中的key
     */
    public enum Key {
        CODE("code"), CN("cn"), EN("en");

        private String key;

        Key(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return String.valueOf(this.key);
        }
    }
}
