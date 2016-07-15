package com.bestomb.service;


import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.area.CityBo;
import com.bestomb.common.response.area.CountyBo;
import com.bestomb.common.response.area.ProvinceBo;

import java.util.List;

/**
 * 地区信息接口
 */
public interface IAreaService {

    /**
     * 获取省数据集合
     *
     * @return
     */
    List<ProvinceBo> getProvince() throws EqianyuanException;

    /**
     * 根据省编号获取对应市数据集合
     *
     * @param provinceId 省编号
     * @return
     */
    List<CityBo> getCity(String provinceId) throws EqianyuanException;

    /**
     * 根据省编号获取对应市数据集合
     *
     * @param cityId 市编号
     * @return
     */
    List<CountyBo> getCounty(String cityId) throws EqianyuanException;
}
