package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.specialholiday.SpecialHolidayRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.specialholiday.SpecialHolidayBo;

public interface ISpecialHolidayService {

    /**
     * 分页查询
     */
    PageResponse getList(int pageNo, int pageSize) throws EqianyuanException;

    /**
     * 添加特殊节日
     */
    void add(SpecialHolidayRequest specialHolidayRequest) throws EqianyuanException;

    /**
     * 删除特殊节日
     */
    void removeByIds(String... id) throws EqianyuanException;
}
