package com.bestomb.service.impl;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.model.ModelClassifyByEditRequest;
import com.bestomb.common.response.model.ModelClassifyBo;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.IModelClassifyDao;
import com.bestomb.entity.ModelClassify;
import com.bestomb.service.IModelClassifyService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 模型管理业务接口实现类
 * Created by jason on 2016-07-21.
 */
@Service
public class ModelClassifyServiceImpl implements IModelClassifyService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IModelClassifyDao modelClassifyDao;

    //模型分类是否可编辑-允许用户编辑
    private static final int MODEL_CLASSIFY_CAN_EDIT_BY_TRUE = 2;
    //模型分类名称DB许可字节长度
    private static final int MODEL_CLASSIFY_NAME_MAX_BYTES_BY_DB = 60;

    /**
     * 根据序列编号删除数据
     *
     * @param id
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeByIds(String... id) throws EqianyuanException {
        if (ObjectUtils.isEmpty(id)) {
            logger.info("removeByIds fail , because id is null, a full table delete is prohibited");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_ID_IS_EMPTY);
        }

        //删除模型分类数据
        modelClassifyDao.deleteByPrimaryKey(id);

        //删除模型分类与模型关系
//        systemUserRoleRelateDao.deleteBySystemUser(id);
    }

    /**
     * 插入对象数据
     *
     * @param modelClassifyByEditRequest
     * @throws EqianyuanException
     */
    public ModelClassifyBo add(ModelClassifyByEditRequest modelClassifyByEditRequest) throws EqianyuanException {
        //名称是否为空
        if (StringUtils.isEmpty(modelClassifyByEditRequest.getName())) {
            logger.info("add fail , because name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_NAME_IS_EMPTY);
        }

        //父分类编号是否为空
        if (StringUtils.isEmpty(modelClassifyByEditRequest.getParentId())) {
            logger.info("add fail , because parent id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_PARENT_ID_IS_EMPTY);
        }

        //模型分类名称内容长度是否超出DB许可长度
        try {
            if (modelClassifyByEditRequest.getName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > MODEL_CLASSIFY_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because name [" + modelClassifyByEditRequest.getName()
                        + "] bytes greater than" + MODEL_CLASSIFY_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + modelClassifyByEditRequest.getName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //根据父分类编号查询父分类数据对象
        ModelClassify modelClassifyByParentId = modelClassifyDao.selectByPrimaryKey(modelClassifyByEditRequest.getParentId());
        if (ObjectUtils.isEmpty(modelClassifyByParentId)
                || StringUtils.isEmpty(modelClassifyByParentId.getId())) {
            logger.info("add fail , because parent id [" + modelClassifyByEditRequest.getParentId() + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_DATA_NOT_EXISTS);
        }

        //构建DB对象
        ModelClassify modelClassify = new ModelClassify();
        BeanUtils.copyProperties(modelClassifyByEditRequest, modelClassify);
        modelClassify.setCanEdit(MODEL_CLASSIFY_CAN_EDIT_BY_TRUE);
        modelClassifyDao.insertSelective(modelClassify);
        ModelClassifyBo modelClassifyBo = new ModelClassifyBo();
        BeanUtils.copyProperties(modelClassify, modelClassifyBo);
        return modelClassifyBo;
    }

    /**
     * 更新对象数据
     *
     * @param modelClassifyByEditRequest
     * @throws EqianyuanException
     */
    public void modify(ModelClassifyByEditRequest modelClassifyByEditRequest) throws EqianyuanException {
        //主键是否为空
        if (StringUtils.isEmpty(modelClassifyByEditRequest.getId())) {
            logger.info("modify fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_ID_IS_EMPTY);
        }

        //名称是否为空
        if (StringUtils.isEmpty(modelClassifyByEditRequest.getName())) {
            logger.info("modify fail , because name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_NAME_IS_EMPTY);
        }

        //父分类编号是否为空
        if (StringUtils.isEmpty(modelClassifyByEditRequest.getParentId())) {
            logger.info("modify fail , because parent id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_PARENT_ID_IS_EMPTY);
        }

        //模型分类名称内容长度是否超出DB许可长度
        try {
            if (modelClassifyByEditRequest.getName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > MODEL_CLASSIFY_NAME_MAX_BYTES_BY_DB) {
                logger.info("modify fail , because name [" + modelClassifyByEditRequest.getName()
                        + "] bytes greater than" + MODEL_CLASSIFY_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("modify fail , because name [" + modelClassifyByEditRequest.getName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //根据父分类编号查询父分类数据对象
        ModelClassify modelClassifyByParentId = modelClassifyDao.selectByPrimaryKey(modelClassifyByEditRequest.getParentId());
        if (ObjectUtils.isEmpty(modelClassifyByParentId)
                || StringUtils.isEmpty(modelClassifyByParentId.getId())) {
            logger.info("modify fail , because parent id [" + modelClassifyByEditRequest.getParentId() + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_DATA_NOT_EXISTS);
        }

        //构建DB对象
        ModelClassify modelClassify = new ModelClassify();
        BeanUtils.copyProperties(modelClassifyByEditRequest, modelClassify);
        modelClassifyDao.updateByPrimaryKeySelective(modelClassify);
    }

    /**
     * 根据父分类编号获取下一级子分类集合
     *
     * @param parentId
     * @return
     */
    public List<ModelClassifyBo> getLevelOneListByParentId(String parentId) throws EqianyuanException {
        //父分类编号是否为空
        if (StringUtils.isEmpty(parentId)) {
            logger.info("modify fail , because parent id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_PARENT_ID_IS_EMPTY);
        }

        List<ModelClassify> modelClassifies = modelClassifyDao.selectLevelOneListByParentId(parentId);
        if (CollectionUtils.isEmpty(modelClassifies)) {
            logger.info("get level one list by parent id [" + parentId + "] is null");
            throw new EqianyuanException(ExceptionMsgConstant.MODEL_CLASSIFY_DATA_NOT_EXISTS);
        }

        List<ModelClassifyBo> modelClassifyBos = new ArrayList<ModelClassifyBo>();
        for (ModelClassify modelClassify : modelClassifies) {
            ModelClassifyBo modelClassifyBo = new ModelClassifyBo();
            BeanUtils.copyProperties(modelClassify, modelClassifyBo);
            modelClassifyBos.add(modelClassifyBo);
        }

        return modelClassifyBos;
    }
}
