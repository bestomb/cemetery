package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.goodsclass.GoodsClassifyRequest;
import com.bestomb.common.response.goodsclassify.GoodsClassifyBo;
import org.springframework.aop.target.LazyInitTargetSource;

import java.util.List;

public interface IGoodsClassifyService {

    /**
     * 添加数据
     */
    GoodsClassifyBo add(GoodsClassifyRequest goodsClassifyRequest) throws EqianyuanException;

    /**
     * 修改对象
     */
    void modify(GoodsClassifyRequest goodsClassifyRequest) throws EqianyuanException;

    /**
     * 根据序列号删除数据
     */
    void removeByIds(String... id) throws EqianyuanException;

    /**
     *根据父分类编号获取下一级子分类集合
     */
    List<GoodsClassifyBo> getLevelOneListByParentId (String parentId) throws EqianyuanException;

}
