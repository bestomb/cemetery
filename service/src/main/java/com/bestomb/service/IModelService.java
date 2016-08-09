package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.model.ModelByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.model.ModelBo;

import javax.servlet.http.HttpServletRequest;

/**
 * 模型业务逻辑接口
 * Created by jason on 2016-07-24.
 */
public interface IModelService {

    /**
     * 根据序列编号删除数据
     *
     * @param id
     */
    void removeByIds(String... id) throws EqianyuanException;

    /**
     * 插入对象数据
     *
     * @param modelByEditRequest
     */
    void add(HttpServletRequest request, ModelByEditRequest modelByEditRequest, String fileType) throws EqianyuanException;

    /**
     * 更新对象数据
     *
     * @param modelByEditRequest
     */
    void modify(HttpServletRequest request, ModelByEditRequest modelByEditRequest, String fileType) throws EqianyuanException;

    /**
     * 根据主键编号查询数据对象
     *
     * @param id
     * @return
     */
    ModelBo queryById(String id) throws EqianyuanException;

    /**
     * 根据对象及分页条件获取分页数据集合
     *
     * @return
     */
    PageResponse queryByPagination(int pageNo, int pageSize, String roleId);
}
