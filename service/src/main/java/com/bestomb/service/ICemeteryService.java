package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.cemetery.CemeteryByEditRequest;

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
}
