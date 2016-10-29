package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.eulogy.EulogyEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.eulogy.EulogyBo;

/**
 * 陵园纪念人祭文悼词接口
 * Created by jason on 2016-10-18.
 */
public interface IEulogyService {

    /**
     * 添加祭文悼词
     *
     * @param eulogyEditRequest
     * @throws EqianyuanException
     */
    void add(EulogyEditRequest eulogyEditRequest) throws EqianyuanException;

    /***
     * 根据纪念人编号查询祭文悼词分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    PageResponse getList(String masterId, Pager page) throws EqianyuanException;

    /**
     * 根据祭文悼词编号查询详情
     *
     * @param eulogyId
     * @return
     * @throws EqianyuanException
     */
    EulogyBo getInfo(String eulogyId) throws EqianyuanException;


}
