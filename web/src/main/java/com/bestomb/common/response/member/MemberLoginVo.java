package com.bestomb.common.response.member;

public class MemberLoginVo {
    private Integer memberId;

    private Long mobileNumber;

    private String nickName;

    private String createTimeForStr;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreateTimeForStr() {
        return createTimeForStr;
    }

    public void setCreateTimeForStr(String createTimeForStr) {
        this.createTimeForStr = createTimeForStr;
    }
}