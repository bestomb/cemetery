package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.goods.SecondClassificationEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.goods.SecondClassificationBO;

import java.util.List;

/**
 * 商品二级分类管理业务接口
 * Created by jason on 2016-07-19.
 */
public interface ISecondClassifyService {

    /***
     * 根据条件查询二级分类分页列表
     *
     * @param firstClassify 一级分类
     * @param page
     * @return
     * @throws EqianyuanException
     */
    PageResponse getPageList(Integer firstClassify, Pager page) throws EqianyuanException;

    /**
     * 根据一级商品分类获取二级分类数据集合
     *
     * @param firstClassify
     * @return
     * @throws EqianyuanException
     */
    List<SecondClassificationBO> getList(Integer firstClassify) throws EqianyuanException;

    /**
     * 根据编号查询详情
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    SecondClassificationBO getInfo(String id) throws EqianyuanException;

    /**
     * 根据序列编号删除数据
     *
     * @param id
     */
    void removeByIds(String... id) throws EqianyuanException;

    /**
     * 插入对象数据
     *
     * @param secondClassificationEditRequest
     */
    void add(SecondClassificationEditRequest secondClassificationEditRequest) throws EqianyuanException;

    /**
     * 更新对象数据
     *
     * @param secondClassificationEditRequest
     */
    void modify(SecondClassificationEditRequest secondClassificationEditRequest) throws EqianyuanException;

}
