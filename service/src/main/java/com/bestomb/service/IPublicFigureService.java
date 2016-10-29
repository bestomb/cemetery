package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.publicFigure.PublicFigureRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.PublicFigure.PublicFigureBo;

public interface IPublicFigureService {

    /**
     * 分页查询
     */
    PageResponse getList(int pageNo, int pageSize, String status) throws EqianyuanException;

    /**
     * 根据ID查询数据
     */
    PublicFigureBo queryById(String id) throws EqianyuanException;

    /**
     * 修改数据
     */
    void modify(PublicFigureRequest publicFigureRequest) throws EqianyuanException;

}
