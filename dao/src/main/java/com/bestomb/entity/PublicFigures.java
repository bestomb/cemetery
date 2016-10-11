package com.bestomb.entity;

public class PublicFigures {
    private String id;

    private Integer memberId;

    private String auditOpinion;

    private Integer status;

    private String systemUserId;

    private Integer createTime;

    private Integer disposeTime;

    private String bewrite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion == null ? null : auditOpinion.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSystemUser() {
        return systemUserId;
    }

    public void setSystemUser(String systemUser) {
        this.systemUserId = systemUser == null ? null : systemUser.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getDisposeTime() {
        return disposeTime;
    }

    public void setDisposeTime(Integer disposeTime) {
        this.disposeTime = disposeTime;
    }

    public String getBewrite() {
        return bewrite;
    }

    public void setBewrite(String bewrite) {
        this.bewrite = bewrite == null ? null : bewrite.trim();
    }
}