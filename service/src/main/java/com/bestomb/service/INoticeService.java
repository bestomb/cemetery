package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;

/**
 * 陵园公告业务逻辑
 * Created by jason on 2016-07-06.
 */
public interface INoticeService {

    /**
     * 根据序列编号删除数据
     *
     * @param id
     */
    void removeByIds(Integer memberId, String... id) throws EqianyuanException;

    /**
     * 插入对象数据
     *
     * @param content
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    void add(String content, String cemeteryId, Integer memberId) throws EqianyuanException;

    /**
     * 更新对象数据
     *
     * @param id
     * @param content
     * @throws EqianyuanException
     */
    void modify(String id, String content, Integer memberId) throws EqianyuanException;

    /**
     * 根据对象及分页条件获取分页数据集合
     *
     * @return
     */
    PageResponse queryByPagination(int pageNo, int pageSize, String cemeteryId);
}
