package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.cemetery.CemeteryByAreaListRequest;
import com.bestomb.common.request.cemetery.CemeteryByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.cemetery.CemeteryBo;

import java.util.List;

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
     * 修改陵园
     *
     * @param cemeteryByEditRequest
     * @throws EqianyuanException
     */
    void update(CemeteryByEditRequest cemeteryByEditRequest) throws EqianyuanException;

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

    /**
     * 获取我的陵园集合
     *
     * @param memberId 会员编号
     * @return
     */
    List<CemeteryBo> getListByMemberId(Integer memberId) throws EqianyuanException;

    /**
     * 分页获取我的陵园集合
     *
     * @param memberId 会员编号
     * @return
     */
    PageResponse pagingByMemberId(Integer memberId, String pageNo, int pageSize) throws EqianyuanException;

    /**
     * 根据陵园编号查找陵园信息
     *
     * @param cemeteryId
     * @return
     * @throws EqianyuanException
     */
    CemeteryBo getInfoById(String cemeteryId) throws EqianyuanException;

    /**
     * 根据陵园编号查找陵园基本信息
     *
     * @param cemeteryId
     * @return
     * @throws EqianyuanException
     */
    CemeteryBo getBasicInfoById(String cemeteryId) throws EqianyuanException;

    /**
     * 根据陵园编号及访问密码核对陵园是否允许访问
     *
     * @param cemeteryId
     * @param enterPwd
     * @return
     * @throws EqianyuanException
     */
    void checkAccessPassword(String cemeteryId, String enterPwd) throws EqianyuanException;

    /**
     * 获取陵园分页集合
     *
     * @param cemeteryByAreaListRequest
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    PageResponse getList(CemeteryByAreaListRequest cemeteryByAreaListRequest, int pageNo, int pageSize) throws EqianyuanException;

    /**
     * 删除陵园
     *
     * @param cemeteryId
     * @return
     * @throws EqianyuanException
     */
    boolean deleteCemetery(Integer memberId, String cemeteryId) throws EqianyuanException;
}
