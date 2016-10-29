package com.bestomb.common.request.tombstone.master.leaveMessage;

public class LeaveMessageEditRequest {
    //留言编号
    private String id;

    //会员编号
    private Integer memberId;

    //墓主纪念人编号
    private String masterId;

    //陵园编号
    private String cemeteryId;

    //被回复的留言编号
    private String replyId;

    //留言创建时间
    private Integer createTime;

    //留言内容
    private String content;

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

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId == null ? null : masterId.trim();
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId == null ? null : replyId.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCemeteryId() {
        return cemeteryId;
    }

    public void setCemeteryId(String cemeteryId) {
        this.cemeteryId = cemeteryId;
    }
}