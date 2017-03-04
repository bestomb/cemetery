package com.bestomb.common.response.master;

public class MasterBo {
    private String id;

    private String name;

    private String portrait;

    private String tombstoneId;

    private String birthday;

    private String deathTime;

    private Integer sort;

    private String lifeIntroduce;

    private String lastWish;

    private Integer age;

    private String creater;

    private String createTime;

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getTombstoneId() {
        return tombstoneId;
    }

    public void setTombstoneId(String tombstoneId) {
        this.tombstoneId = tombstoneId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(String deathTime) {
        this.deathTime = deathTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLifeIntroduce() {
        return lifeIntroduce;
    }

    public void setLifeIntroduce(String lifeIntroduce) {
        this.lifeIntroduce = lifeIntroduce;
    }

    public String getLastWish() {
        return lastWish;
    }

    public void setLastWish(String lastWish) {
        this.lastWish = lastWish;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
