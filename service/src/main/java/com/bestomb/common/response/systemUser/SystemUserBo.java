package com.bestomb.common.response.systemUser;

public class SystemUserBo {
    private String id;

    private String loginName;

    private String realName;

    private Long mobile;

    private String createTimeForStr;

    /**
     * 系统角色
     */
    private String[] roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getCreateTimeForStr() {
        return createTimeForStr;
    }

    public void setCreateTimeForStr(String createTimeForStr) {
        this.createTimeForStr = createTimeForStr;
    }

    public String[] getRoleId() {
        return roleId;
    }

    public void setRoleId(String[] roleId) {
        this.roleId = roleId;
    }
}