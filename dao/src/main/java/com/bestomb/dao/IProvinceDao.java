package com.bestomb.dao;

import com.bestomb.entity.Province;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProvinceDao {
    /**
     * 查找数据集合
     *
     * @return
     */
    List<Province> selectByList();

    /**
     * 根据地区编号查询数据
     *
     * @param provinceId
     * @return
     */
    Province selectById(@Param("province_id") String provinceId);
}