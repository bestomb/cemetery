package com.bestomb.entity;

public class Community {
    private String id;

    private String name;

    private String villageId;

    private Integer renameCount;

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

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId == null ? null : villageId.trim();
    }

    public Integer getRenameCount() {
        return renameCount;
    }

    public void setRenameCount(Integer renameCount) {
        this.renameCount = renameCount;
    }
}