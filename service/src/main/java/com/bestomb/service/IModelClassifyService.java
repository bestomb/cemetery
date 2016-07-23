package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.model.ModelClassifyByEditRequest;
import com.bestomb.common.response.model.ModelClassifyBo;
import com.bestomb.entity.ModelClassify;

import java.util.List;

/**
 * 模型分类管理业务接口
 * Created by jason on 2016-07-19.
 */
public interface IModelClassifyService {

    /**
     * 根据序列编号删除数据
     *
     * @param id
     */
    void removeByIds(String... id) throws EqianyuanException;

    /**
     * 插入对象数据
     *
     * @param modelClassifyByEditRequest
     */
    ModelClassifyBo add(ModelClassifyByEditRequest modelClassifyByEditRequest) throws EqianyuanException;

    /**
     * 更新对象数据
     *
     * @param modelClassifyByEditRequest
     */
    void modify(ModelClassifyByEditRequest modelClassifyByEditRequest) throws EqianyuanException;

    /**
     * 根据父分类编号获取下一级子分类集合
     * @param parentId
     * @return
     */
    List<ModelClassifyBo> getLevelOneListByParentId(String parentId) throws EqianyuanException;

}
