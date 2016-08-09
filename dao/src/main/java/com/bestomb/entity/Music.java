package com.bestomb.entity;

public class Music {
    private String id;

    private String name;

    private String fileAddress;

    private Long fileSize;

    private Integer type;

    private String cemeteryId;

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

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress == null ? null : fileAddress.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCemeteryId() {
        return cemeteryId;
    }

    public void setCemeteryId(String cemeteryId) {
        this.cemeteryId = cemeteryId == null ? null : cemeteryId.trim();
    }
}