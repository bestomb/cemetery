package com.bestomb.common.response.cemetery;

/**
 * Created by jason on 2016-07-19.
 */
public class CemeteryVo {

    private Integer id;

    private String name;

    private Integer isOpen;

    private Integer remainingStorageSize;

    private Integer storageSize;

    //省地址名称
    private String provinceName;

    //市地址名称
    private String cityName;

    //区地址名称
    private String countyName;

    //乡地址名称
    private String townName;

    //村地址名称
    private String villageName;

    //社地址名称
    private String communityName;

    private String createTimeForStr;

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

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getRemainingStorageSize() {
        return remainingStorageSize;
    }

    public void setRemainingStorageSize(Integer remainingStorageSize) {
        this.remainingStorageSize = remainingStorageSize;
    }

    public Integer getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(Integer storageSize) {
        this.storageSize = storageSize;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCreateTimeForStr() {
        return createTimeForStr;
    }

    public void setCreateTimeForStr(String createTimeForStr) {
        this.createTimeForStr = createTimeForStr;
    }
}
