package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.MasterBo;

public interface IMasterService {

    /**
     * 分页查询
     */
    PageResponse getList(int pageNo, int pageSize, Integer cemeteryId) throws EqianyuanException;

    /**
     * 详细信息查询
     * @param id
     * @return
     * @throws EqianyuanException
     */
    MasterBo queryById(String id)throws EqianyuanException;
}
