package com.bestomb.dao;

import com.bestomb.entity.Biont;
import com.bestomb.entity.PlantsAndAnimals;

public interface IPlantsAndAnimalsDao {
    int deleteByPrimaryKey(String id);

    int insert(PlantsAndAnimals record);

    int insertSelective(PlantsAndAnimals record);

    PlantsAndAnimals selectByPrimaryKey(String id);
    
    /***
     * 查询动植物详情
     * @param biont
     * @return
     */
    Biont getDetail(String goodsId);

    int updateByPrimaryKeySelective(PlantsAndAnimals record);

    int updateByPrimaryKeyWithBLOBs(PlantsAndAnimals record);

    int updateByPrimaryKey(PlantsAndAnimals record);
}