package com.bestomb.common.response.cemetery;

/**
 * 根据地区查找陵园信息bo对象
 */
public class CemeteryByAreaBo {
    private String id;

    private String name;

    private String memberId;

    private Integer isOpen;

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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
}