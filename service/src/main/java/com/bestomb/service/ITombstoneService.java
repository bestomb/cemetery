package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.TombstoneEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.entity.Tombstone;

/**
 * 陵园墓碑接口
 * Created by jason on 2016-10-18.
 */
public interface ITombstoneService {

    /**
     * 陵园创建墓碑
     *
     * @param tombstoneEditRequest
     * @throws EqianyuanException
     */
    void addTombstone(TombstoneEditRequest tombstoneEditRequest) throws EqianyuanException;

    /**
     * 陵园墓碑修改
     *
     * @param tombstoneEditRequest
     * @throws EqianyuanException
     */
    void modifyTombstone(TombstoneEditRequest tombstoneEditRequest) throws EqianyuanException;

    /**
     * 删除陵园墓碑
     *
     * @param tombstoneId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    void delTombstone(String tombstoneId, String cemeteryId, Integer memberId) throws EqianyuanException;

    /***
     * 根据条件查询陵园墓碑分页集合
     *
     * @param tombstone
     * @param page
     * @return
     * @throws EqianyuanException
     */
    PageResponse getListByCondition(Tombstone tombstone, Pager page) throws EqianyuanException;
}
