package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.area.*;
import com.bestomb.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 地区控制器
 */
@Controller
public class AreaController extends BaseController {

    @Autowired
    private IAreaService areaService;

    /**
     * 获取省数据集合
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getProvince")
    @ResponseBody
    public ServerResponse getProvince() throws EqianyuanException {
        List<ProvinceBo> provinceVoList = areaService.getProvince();
        return new ServerResponse.ResponseBuilder().data(provinceVoList).build();
    }

    /**
     * 根据省信息获取下级市数据集合
     *
     * @param provinceId 省级编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getCity")
    @ResponseBody
    public ServerResponse getCity(String provinceId) throws EqianyuanException {
        List<CityBo> cityVos = areaService.getCity(provinceId);
        return new ServerResponse.ResponseBuilder().data(cityVos).build();
    }

    /**
     * 根据市信息获取下级区数据集合
     *
     * @param cityId 市级编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getCounty")
    @ResponseBody
    public ServerResponse getCounty(String cityId) throws EqianyuanException {
        List<CountyBo> countyVos = areaService.getCounty(cityId);
        return new ServerResponse.ResponseBuilder().data(countyVos).build();
    }
}
