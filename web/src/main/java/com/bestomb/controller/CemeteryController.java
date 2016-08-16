package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.cemetery.CemeteryByAreaListRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.cemetery.CemeteryBo;
import com.bestomb.service.ICemeteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 陵园控制器
 * Created by jason on 2016-07-15.
 */
@Controller
@RequestMapping("/cemetery")
public class CemeteryController extends BaseController {

    @Autowired
    private ICemeteryService cemeteryService;

    /**
     * 根据地区信息查询陵园分页集合
     *
     * @param cemeteryByAreaListRequest 陵园地区查询对象
     * @param pageNo                    分页页码
     * @param pageSize                  分页条数    默认64条每页
     * @return
     */
    @RequestMapping("/getListByArea")
    @ResponseBody
    public ServerResponse getListByArea(CemeteryByAreaListRequest cemeteryByAreaListRequest,
                                        @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws EqianyuanException {
        PageResponse pageResponse = cemeteryService.getList(cemeteryByAreaListRequest, pageNo, pageSize);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 根据陵园编号获取陵园信息
     *
     * @param cemeteryId
     * @return
     */
    @RequestMapping("/getInfoById")
    @ResponseBody
    public ServerResponse getBasicInfoById(String cemeteryId) throws EqianyuanException {
        CemeteryBo cemeteryBo = cemeteryService.getInfoById(cemeteryId);
        return new ServerResponse.ResponseBuilder().data(cemeteryBo).build();
    }
}
