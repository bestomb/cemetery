package com.bestomb.entity;

public class Master {
    private String id;

    private String name;

    private String portrait;

    private String tombstoneId;

    private String birthday;

    private String deathTime;

    private Integer sort;

    private Integer age;

    private String creater;

    private String createTime;

    private Integer dataTime;

    public Integer getDataTime() {
        return dataTime;
    }

    public void setDataTime(Integer dataTime) {
        this.dataTime = dataTime;
    }

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
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait == null ? null : portrait.trim();
    }

    public String getTombstoneId() {
        return tombstoneId;
    }

    public void setTombstoneId(String tombstoneId) {
        this.tombstoneId = tombstoneId == null ? null : tombstoneId.trim();
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}