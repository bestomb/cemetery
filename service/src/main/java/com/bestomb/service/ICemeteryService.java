package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.cemetery.CemeteryByAreaListRequest;
import com.bestomb.common.request.cemetery.CemeteryByEditRequest;
import com.bestomb.common.response.PageResponse;

/**
 * 陵园业务接口
 * Created by jason on 2016-07-15.
 */
public interface ICemeteryService {

    /**
     * 添加陵园
     *
     * @param cemeteryByEditRequest
     * @throws EqianyuanException
     */
    void add(CemeteryByEditRequest cemeteryByEditRequest) throws EqianyuanException;

    /**
     * 根据地区信息和分页信息查询陵园集合
     *
     * @param cemeteryByAreaListRequest
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    PageResponse getListByArea(CemeteryByAreaListRequest cemeteryByAreaListRequest, int pageNo, int pageSize) throws EqianyuanException;

    /**
     * 根据陵园编号查询陵园归属地陵园分页集合
     *
     * @param cemeteryId
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResponse getListByCemeteryId(String cemeteryId, int pageNo, int pageSize) throws EqianyuanException;

    /**
     * 任意门
     *
     * @param cemeteryByAreaListRequest
     * @param behavior
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    PageResponse getListByArbitraryDoor(CemeteryByAreaListRequest cemeteryByAreaListRequest, String behavior, String pageNo, int pageSize) throws EqianyuanException;
}
