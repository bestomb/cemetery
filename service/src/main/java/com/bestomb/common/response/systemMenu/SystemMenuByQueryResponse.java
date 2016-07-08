package com.bestomb.common.response.systemMenu;

/**
 * 系统菜单查询结果返回对象
 * Created by jason on 2016-07-08.
 */
public class SystemMenuByQueryResponse {
    /**
     * 菜单编号
     */
    private String id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单跳转地址
     */
    private String url;

    /**
     * 父菜单编号
     */
    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
