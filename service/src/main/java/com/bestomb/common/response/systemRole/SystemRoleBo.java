package com.bestomb.common.response.systemRole;

/**
 * 系统角色查询结果返回对象
 * Created by jason on 2016-07-08.
 */
public class SystemRoleBo {
    /**
     * 编号
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单
     */
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
