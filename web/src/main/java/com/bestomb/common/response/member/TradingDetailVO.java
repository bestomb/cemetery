package com.bestomb.common.response.member;

/**
 * Created by jason on 2017-03-19.
 */
public class TradingDetailVO {

    private String instructions;

    private Double trading;

    private String type;

    private String createTime;

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Double getTrading() {
        return trading;
    }

    public void setTrading(Double trading) {
        this.trading = trading;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
