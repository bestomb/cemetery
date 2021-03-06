package com.bestomb.common.request.tombstone;

public class TombstoneEditRequest {
    private String id;

    private Integer sort;

    private Integer cemeteryId;

    private Integer memberId;

    private Integer type;

    private Integer species;

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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

}