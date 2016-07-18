package com.bestomb.common.response.cemetery;

/**
 * 根据会员编号查找陵园信息vo对象
 */
public class CemeteryByMineVo {
    private String id;

    private String name;

    private Integer isOpen;

    private String createTimeForStr;

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

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getCreateTimeForStr() {
        return createTimeForStr;
    }

    public void setCreateTimeForStr(String createTimeForStr) {
        this.createTimeForStr = createTimeForStr;
    }
}