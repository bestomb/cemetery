package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.memorialRecord.MemorialRecordEditRequest;
import com.bestomb.common.response.PageResponse;

/**
 * 陵园纪念人纪念接口
 * Created by jason on 2016-10-18.
 */
public interface IMemorialRecordService {

    /**
     * 保存纪念记录
     *
     * @param memorialRecordEditRequest
     * @throws EqianyuanException
     */
    void add(MemorialRecordEditRequest memorialRecordEditRequest) throws EqianyuanException;

    /***
     * 根据纪念人编号分页查询纪念人纪念记录
     *
     * @param masterId 纪念人编号
     * @param page
     * @return
     * @throws EqianyuanException
     */
    PageResponse getList(String masterId, Pager page) throws EqianyuanException;
}
