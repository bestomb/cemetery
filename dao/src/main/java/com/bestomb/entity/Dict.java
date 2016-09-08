package com.bestomb.entity;

public class Dict extends DictKey {
    private String dictDesc;

    private String dictValue;

    private Integer pDictCode;

    private Integer sort;

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc == null ? null : dictDesc.trim();
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
    }

    public Integer getpDictCode() {
        return pDictCode;
    }

    public void setpDictCode(Integer pDictCode) {
        this.pDictCode = pDictCode;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}