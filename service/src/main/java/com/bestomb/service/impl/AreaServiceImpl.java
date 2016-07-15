package com.bestomb.service.impl;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.area.CityBo;
import com.bestomb.common.response.area.CountyBo;
import com.bestomb.common.response.area.ProvinceBo;
import com.bestomb.dao.ICityDao;
import com.bestomb.dao.ICountyDao;
import com.bestomb.dao.IProvinceDao;
import com.bestomb.entity.City;
import com.bestomb.entity.County;
import com.bestomb.entity.Province;
import com.bestomb.service.IAreaService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 地区信息接口实现类
 */
@Service
public class AreaServiceImpl implements IAreaService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IProvinceDao provinceDao;

    @Autowired
    private ICityDao cityDao;

    @Autowired
    private ICountyDao countyDao;

    /**
     * 获取省数据集合
     *
     * @return
     */
    public List<ProvinceBo> getProvince() throws EqianyuanException {
        List<Province> provinces = provinceDao.selectByList();
        if (CollectionUtils.isEmpty(provinces)) {
            logger.info("get province , result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_PROVINCE_DATA_NO_EXISTS);
        }

        List<ProvinceBo> provinceBos = new ArrayList<ProvinceBo>();
        for (Province province : provinces) {
            ProvinceBo provinceBo = new ProvinceBo();
            BeanUtils.copyProperties(province, provinceBo);

            provinceBos.add(provinceBo);
        }
        return provinceBos;
    }

    /**
     * 根据省编号获取市数据集合
     *
     * @param provinceId 省编号
     * @return
     * @throws EqianyuanException
     */
    public List<CityBo> getCity(String provinceId) throws EqianyuanException {
        if (StringUtils.isEmpty(provinceId)) {
            logger.info("getCity fail , because query param provinceId is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_PROVINCE_ID_IS_EMPTY);
        }

        List<City> cities = cityDao.selectByList(provinceId);
        if (CollectionUtils.isEmpty(cities)) {
            logger.info("get city by province id [" + provinceId + "] , result is empty");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_CITY_DATA_NO_EXISTS);
        }

        List<CityBo> cityBos = new ArrayList<CityBo>();
        for (City city : cities) {
            CityBo cityBo = new CityBo();
            BeanUtils.copyProperties(city, cityBo);

            cityBos.add(cityBo);
        }

        return cityBos;
    }

    /**
     * 根据市级编号获取下级区数据集合
     *
     * @param cityId 市编号
     * @return
     * @throws EqianyuanException
     */
    public List<CountyBo> getCounty(String cityId) throws EqianyuanException {
        if (StringUtils.isEmpty(cityId)) {
            logger.info("getCounty fail , because query param cityId is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_CITY_ID_IS_EMPTY);
        }

        List<County> counties = countyDao.selectByList(cityId);
        if (CollectionUtils.isEmpty(counties)) {
            logger.info("get county by city id [" + cityId + "] , result is empty");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_COUNTY_DATA_NO_EXISTS);
        }

        List<CountyBo> countyBos = new ArrayList<CountyBo>();
        for (County county : counties) {
            CountyBo countyBo = new CountyBo();
            BeanUtils.copyProperties(county, countyBo);

            countyBos.add(countyBo);
        }

        return countyBos;
    }
}
