package com.bestomb.common.response.music;

public class MusicBo {
    private String id;

    private String name;

    private String fileAddress;

    private Integer fileSize;

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

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
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