package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.goods.SecondClassificationEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.goods.SecondClassificationBO;
import com.bestomb.dao.ISecondClassificationDao;
import com.bestomb.entity.SecondClassification;
import com.bestomb.service.ISecondClassifyService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 商品二级分类管理业务接口实现类
 * Created by jason on 2016-07-21.
 */
@Service
public class SecondClassifyServiceImpl implements ISecondClassifyService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ISecondClassificationDao secondClassificationDao;

    /**
     * 根据条件获取二级分类分页数据集合
     *
     * @param firstClassify 一级分类
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getPageList(Integer firstClassify, Pager page) throws EqianyuanException {
        // 根据条件查询二级分类列表
        int dataCount = secondClassificationDao.countByPagination(firstClassify);
        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询二级分类列表无数据");
            return new PageResponse(page, null);
        }
        List<SecondClassification> secondClassifications = secondClassificationDao.selectByPagination(page, firstClassify);
        if (CollectionUtils.isEmpty(secondClassifications)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询二级分类列表无数据");
            return new PageResponse(page, null);
        }
        List<SecondClassificationBO> secondClassificationBOs = new ArrayList<SecondClassificationBO>();
        for (SecondClassification m : secondClassifications) {
            SecondClassificationBO secondClassificationBO = new SecondClassificationBO();
            BeanUtils.copyProperties(m, secondClassificationBO);
            secondClassificationBOs.add(secondClassificationBO);
        }
        return new PageResponse(page, secondClassificationBOs);
    }

    /**
     * 根据商品一级分类查询二级分类集合
     *
     * @param firstClassify
     * @return
     * @throws EqianyuanException
     */
    public List<SecondClassificationBO> getList(Integer firstClassify) throws EqianyuanException {
        List<SecondClassification> secondClassifications = secondClassificationDao.selectByFirstClassify(firstClassify);
        if (CollectionUtils.isEmpty(secondClassifications)) {
            logger.info("根据条件查询二级分类列表无数据");
            return Collections.EMPTY_LIST;
        }
        List<SecondClassificationBO> secondClassificationBOs = new ArrayList<SecondClassificationBO>();
        for (SecondClassification m : secondClassifications) {
            SecondClassificationBO secondClassificationBO = new SecondClassificationBO();
            BeanUtils.copyProperties(m, secondClassificationBO);
            secondClassificationBOs.add(secondClassificationBO);
        }
        return secondClassificationBOs;
    }

    /**
     * 根据序列编号删除数据
     *
     * @param id
     * @throws EqianyuanException
     */
    public void removeByIds(String... id) throws EqianyuanException {
        if (ObjectUtils.isEmpty(id)) {
            logger.info("removeByIds fail , because id is null, a full table delete is prohibited");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_ID_IS_EMPTY);
        }

        secondClassificationDao.deleteByPrimaryKey(id);
    }

    /**
     * 插入对象数据
     *
     * @param secondClassificationEditRequest
     * @throws EqianyuanException
     */
    public void add(SecondClassificationEditRequest secondClassificationEditRequest) throws EqianyuanException {
        //构建DB对象
        SecondClassification secondClassification = new SecondClassification();
        BeanUtils.copyProperties(secondClassificationEditRequest, secondClassification);
        secondClassificationDao.insertSelective(secondClassification);
    }

    /**
     * 更新对象数据
     *
     * @param secondClassificationEditRequest
     * @throws EqianyuanException
     */
    public void modify(SecondClassificationEditRequest secondClassificationEditRequest) throws EqianyuanException {
        //构建DB对象
        SecondClassification secondClassification = new SecondClassification();
        BeanUtils.copyProperties(secondClassificationEditRequest, secondClassification);
        secondClassificationDao.updateByPrimaryKeySelective(secondClassification);
    }

    /**
     * 根据编号查询信息
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    public SecondClassificationBO getInfo(String id) throws EqianyuanException {

        SecondClassification secondClassification = secondClassificationDao.selectByPrimaryKey(Integer.parseInt(id));
        if (ObjectUtils.isEmpty(secondClassification)
                || ObjectUtils.isEmpty(secondClassification.getId())) {
            logger.info("根据二级分类编号【" + id + "】查询数据为空");
            throw new EqianyuanException(ExceptionMsgConstant.CUSTOM_CLASSIFY_DATA_NOT_EXISTS);
        }

        SecondClassificationBO secondClassificationBO = new SecondClassificationBO();
        BeanUtils.copyProperties(secondClassification, secondClassificationBO);
        return secondClassificationBO;
    }
}
