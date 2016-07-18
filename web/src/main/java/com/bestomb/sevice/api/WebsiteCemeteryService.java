package com.bestomb.sevice.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.cemetery.CemeteryByAreaListRequest;
import com.bestomb.common.request.cemetery.CemeteryByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.cemetery.CemeteryBo;
import com.bestomb.common.response.cemetery.CemeteryByAreaBo;
import com.bestomb.service.ICemeteryService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 网站陵园业务调用类
 * Created by jason on 2016-07-15.
 */
@Service
public class WebsiteCemeteryService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ICemeteryService cemeteryService;

    /**
     * 创建陵园
     *
     * @param cemeteryByEditRequest
     */
    public void create(CemeteryByEditRequest cemeteryByEditRequest) throws EqianyuanException {
        cemeteryService.add(cemeteryByEditRequest);
    }

    /**
     * 根据地区信息及分页信息查找陵园集合
     *
     * @param cemeteryByAreaListRequest 地区信息
     * @param pageNo                    分页页码
     * @param pageSize                  每页显示条数
     * @return
     */
    public PageResponse getListByArea(CemeteryByAreaListRequest cemeteryByAreaListRequest, int pageNo, int pageSize) throws EqianyuanException {
        PageResponse pageResponse = cemeteryService.getListByArea(cemeteryByAreaListRequest, pageNo, pageSize);
        List<CemeteryBo> cemeteryBos = (List<CemeteryBo>) pageResponse.getList();
        if (!CollectionUtils.isEmpty(cemeteryBos)) {
            setListByPageResponse(pageResponse, cemeteryBos);
        }
        return pageResponse;
    }

    /**
     * 根据陵园编号查询陵园归属地陵园分页集合
     *
     * @param cemeteryId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageResponse getListByCemetery(String cemeteryId, int pageNo, int pageSize) throws EqianyuanException {
        PageResponse pageResponse = cemeteryService.getListByCemeteryId(cemeteryId, pageNo, pageSize);
        List<CemeteryBo> cemeteryBos = (List<CemeteryBo>) pageResponse.getList();
        if (!CollectionUtils.isEmpty(cemeteryBos)) {
            setListByPageResponse(pageResponse, cemeteryBos);
        }
        return pageResponse;
    }

    /**
     * 设置分页返回对象数据
     * @param pageResponse
     * @param cemeteryBos
     */
    private void setListByPageResponse(PageResponse pageResponse, List<CemeteryBo> cemeteryBos) {
        List<CemeteryByAreaBo> cemeteryByAreaBos = new ArrayList<CemeteryByAreaBo>();
        for (CemeteryBo cemeteryBo : cemeteryBos) {
            CemeteryByAreaBo cemeteryByAreaBo = new CemeteryByAreaBo();
            BeanUtils.copyProperties(cemeteryBo, cemeteryByAreaBo);
            cemeteryByAreaBos.add(cemeteryByAreaBo);
        }

        pageResponse.setList(cemeteryByAreaBos);
    }
}
