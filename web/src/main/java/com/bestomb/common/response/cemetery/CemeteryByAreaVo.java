package com.bestomb.common.response.cemetery;

/**
 * 根据地区查找陵园信息vo对象
 */
public class CemeteryByAreaVo {
    private Integer id;

    private String name;

    private String memberId;

    private Integer isOpen;

    private String modelAddress;

    public String getModelAddress() {
        return modelAddress;
    }

    public void setModelAddress(String modelAddress) {
        this.modelAddress = modelAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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