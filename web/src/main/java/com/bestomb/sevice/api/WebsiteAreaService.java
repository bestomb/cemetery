package com.bestomb.sevice.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.area.*;
import com.bestomb.service.IAreaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 网站地区业务调用类
 * Created by jason on 2016-07-15.
 */
@Service
public class WebsiteAreaService {

    @Autowired
    private IAreaService areaService;

    /**
     * 获取省数据集合
     *
     * @return
     */
    public List<ProvinceVo> getProvince() throws EqianyuanException {
        List<ProvinceBo> provinceBos = areaService.getProvince();

        List<ProvinceVo> provinceVos = new ArrayList<ProvinceVo>();
        for (ProvinceBo provinceBo : provinceBos) {
            ProvinceVo provinceVo = new ProvinceVo();
            BeanUtils.copyProperties(provinceBo, provinceVo);

            provinceVos.add(provinceVo);
        }
        return provinceVos;
    }

    /**
     * 根据省信息获取下级市数据集合
     *
     * @param provinceId
     * @return
     */
    public List<CityVo> getCity(String provinceId) throws EqianyuanException {
        List<CityBo> cityBos = areaService.getCity(provinceId);

        List<CityVo> cityVos = new ArrayList<CityVo>();
        for (CityBo cityBo : cityBos) {
            CityVo cityVo = new CityVo();
            BeanUtils.copyProperties(cityBo, cityVo);

            cityVos.add(cityVo);
        }
        return cityVos;
    }

    /**
     * 根据市信息获取下级区数据集合
     *
     * @param cityId
     * @return
     */
    public List<CountyVo> getCounty(String cityId) throws EqianyuanException {
        List<CountyBo> countyBos = areaService.getCounty(cityId);

        List<CountyVo> countyVos = new ArrayList<CountyVo>();
        for (CountyBo countyBo : countyBos) {
            CountyVo countyVo = new CountyVo();
            BeanUtils.copyProperties(countyBo, countyVo);

            countyVos.add(countyVo);
        }
        return countyVos;
    }
}
