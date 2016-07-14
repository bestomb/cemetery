package com.bestomb.common.response.systemUser;

import com.bestomb.common.response.systemMenu.SystemMenuBo;

import java.util.List;

/**
 * 面向视图层输出的系统用户对象
 * Created by jason on 2016-05-21.
 */
public class SystemUserVo {

    private String id;

    private String loginName;

    private String realName;

    private List<SystemMenuBo> systemMenuBos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<SystemMenuBo> getSystemMenuBos() {
        return systemMenuBos;
    }

    public void setSystemMenuBos(List<SystemMenuBo> systemMenuBos) {
        this.systemMenuBos = systemMenuBos;
    }
}
