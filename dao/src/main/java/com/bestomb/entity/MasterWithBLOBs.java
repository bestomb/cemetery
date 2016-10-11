package com.bestomb.entity;

public class MasterWithBLOBs extends Master {
    private String lifeIntroduce;

    private String lastWish;

    public String getLifeIntroduce() {
        return lifeIntroduce;
    }

    public void setLifeIntroduce(String lifeIntroduce) {
        this.lifeIntroduce = lifeIntroduce == null ? null : lifeIntroduce.trim();
    }

    public String getLastWish() {
        return lastWish;
    }

    public void setLastWish(String lastWish) {
        this.lastWish = lastWish == null ? null : lastWish.trim();
    }
}