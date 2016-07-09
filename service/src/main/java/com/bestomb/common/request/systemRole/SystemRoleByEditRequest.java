package com.bestomb.common.request.systemRole;

public class SystemRoleByEditRequest {

    private String id;

    private String name;

    private String remark;

    private String[] menuId;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String[] getMenuId() {
        return menuId;
    }

    public void setMenuId(String[] menuId) {
        this.menuId = menuId;
    }
}