package com.bestomb.entity;

public class Tombstone {
    private String id;

    private Integer sort;

    private Integer cemeteryId;

    private String memberId;

    private Integer type;

    private Integer species;

    private Integer createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getCemeteryId() {
        return cemeteryId;
    }

    public void setCemeteryId(Integer cemeteryId) {
        this.cemeteryId = cemeteryId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSpecies() {
        return species;
    }

    public void setSpecies(Integer species) {
        this.species = species;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}