package com.bestomb.dao;

import com.bestomb.entity.Biont;
import com.bestomb.entity.PlantsAndAnimals;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPlantsAndAnimalsDao {
    int deleteByPrimaryKey(String id);

    int insert(PlantsAndAnimals record);

    int insertSelective(PlantsAndAnimals record);

    PlantsAndAnimals selectByPrimaryKey(String id);

    /**
     * 根据商品编号集合查询商品集合信息
     *
     * @param goodsIds
     * @return
     */
    List<PlantsAndAnimals> selectByIds(@Param("goodsIds") List<String> goodsIds);
    
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