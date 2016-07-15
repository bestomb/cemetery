package com.bestomb.common.util.yamlMapper;

import java.util.Map;

/**
 * 面向客户端YAML配置信息枚举类
 * 主要用于初始加载yaml内容并缓存，定义yaml配置文件key
 * Created by jason on 2016-06-01.
 */
public class ClientConf {
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
    public enum SMS {
        SMS("SMS"), CorpID("CorpID"), Pwd("Pwd"), BatchSend2("BatchSend2"), BatchSend2_message("BatchSend2_message");

        private String key;

        SMS(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return String.valueOf(this.key);
        }
    }

    /**
     * 利用枚举定义yaml配置文件中的key
     */
    public enum Cemetery {
        constructionCount("constructionCount"), renameCount("renameCount"), init_storage_size("init_storage_size");

        private String key;

        Cemetery(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return String.valueOf(this.key);
        }
    }

}
