package com.bestomb.service;

import com.bestomb.common.response.PageResponse;

/**
 * Created by jason on 2017-03-19.
 */
public interface ITradingService {

    /**
     * 查询获取会员交易币明细
     *
     * @param pageNo
     * @param pageSize
     * @param memberId
     * @return
     */
    PageResponse listByTradingDetail(int pageNo, int pageSize, int memberId);
}
