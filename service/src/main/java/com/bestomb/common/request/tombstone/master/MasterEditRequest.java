package com.bestomb.common.request.tombstone.master;

import org.springframework.web.multipart.MultipartFile;

public class MasterEditRequest {
    //纪念人编号
    private String id;

    //纪念人姓名
    private String name;

    //墓碑编号
    private String tombstoneId;

    //纪念人生辰
    private String birthday;

    //纪念人逝世时间
    private String deathTime;

    //同墓碑纪念人排列次序（从小到大）
    private Integer sort;

    //纪念人生平介绍
    private String lifeIntroduce;

    //纪念人遗言遗愿
    private String lastWish;

    //操作会员编号
    private Integer operatorMemberId;

    //纪念人头像附件
    private MultipartFile portraitFile;

    private String portraitName;

    //墓碑所属陵园编号
    private Integer cemeteryId;

    //纪念人年龄
    private Integer age;

    //创建人
    private String creater;

    //创建时间
    private String createTime;

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

    public MultipartFile getPortraitFile() {
        return portraitFile;
    }

    public void setPortraitFile(MultipartFile portraitFile) {
        this.portraitFile = portraitFile;
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

    public Integer getOperatorMemberId() {
        return operatorMemberId;
    }

    public void setOperatorMemberId(Integer operatorMemberId) {
        this.operatorMemberId = operatorMemberId;
    }

    public Integer getCemeteryId() {
        return cemeteryId;
    }

    public void setCemeteryId(Integer cemeteryId) {
        this.cemeteryId = cemeteryId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPortraitName() {
        return portraitName;
    }

    public void setPortraitName(String portraitName) {
        this.portraitName = portraitName;
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
}