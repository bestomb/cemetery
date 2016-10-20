package com.bestomb.common.response.master;

public class MasterBo {
    private String id;

    private String name;

    private String portrait;

    private String tombstoneId;

    private Integer birthday;

    private Integer deathTime;

    private Integer sort;

    private String lifeIntroduce;

    private String lastWish;

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

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public Integer getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(Integer deathTime) {
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
}
