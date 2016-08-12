package com.bestomb.service.impl;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.goodsclass.GoodsClassifyRequest;
import com.bestomb.common.response.goodsclassify.GoodsClassifyBo;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.IGoodsClassifyDao;
import com.bestomb.entity.GoodsClassify;
import com.bestomb.service.IGoodsClassifyService;
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

@Service
public class GoodsClassifyServiceImpl implements IGoodsClassifyService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IGoodsClassifyDao goodsClassifyDao;

    //商品分类是否可编辑-允许用户编辑
    private static final int GOODS_CLASSIFY_CAN_EDIT_BY_TRUE = 2;
    //商品分类名称DB许可字节长度
    private static final int GOODS_CLASSIFY_NAME_MAX_BYTES_BY_DB = 60;


    /**
     * 增加数据
     * @param goodsClassifyRequest
     * @return
     * @throws EqianyuanException
     */
    public GoodsClassifyBo add(GoodsClassifyRequest goodsClassifyRequest) throws EqianyuanException {
        //判断名称是否为空
        if(StringUtils.isEmpty(goodsClassifyRequest.getName())){
            logger.info("add fail , because name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_CLASSIFY_NAME_IS_EMPTY);
        }
        //父分类编号是否为空
        if(StringUtils.isEmpty(goodsClassifyRequest.getParentId())){
            logger.info("add fail , because parent id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_CLASSIFY_PARENT_ID_IS_EMPTY);
        }

        //商品分类名称内容长度是否超出DB许可长度
        try {
            if (goodsClassifyRequest.getName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > GOODS_CLASSIFY_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because name [" + goodsClassifyRequest.getName()
                        + "] bytes greater than" + GOODS_CLASSIFY_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.GOODS_CLASSIFY_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + goodsClassifyRequest.getName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //根据父分类编号查询父分类数据对象
        GoodsClassify goodsClassifyByParentId = goodsClassifyDao.selectByPrimaryKey(goodsClassifyRequest.getParentId());
        if (ObjectUtils.isEmpty(goodsClassifyByParentId)
                || StringUtils.isEmpty(goodsClassifyByParentId.getId())) {
            logger.info("add fail , because parent id [" + goodsClassifyRequest.getParentId() + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_CLASSIFY_DATA_NOT_EXISTS);
        }

        //构建DB对象
        GoodsClassify goodsClassify = new GoodsClassify();
        BeanUtils.copyProperties(goodsClassifyRequest,goodsClassify);
        goodsClassify.setCanEdit(GOODS_CLASSIFY_CAN_EDIT_BY_TRUE);
        goodsClassifyDao.insertSelective(goodsClassify);
        GoodsClassifyBo goodsClassifyBo = new GoodsClassifyBo();
        BeanUtils.copyProperties(goodsClassify,goodsClassifyBo);
        return goodsClassifyBo;
    }

    /**
     * 修改数据
     * @param goodsClassifyRequest
     * @throws EqianyuanException
     */
    public void modify(GoodsClassifyRequest goodsClassifyRequest) throws EqianyuanException {
        //判断主键是否为空
        if(StringUtils.isEmpty(goodsClassifyRequest.getId())){
            logger.info("modify fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_CLASSIFY_PARENT_ID_IS_EMPTY);
        }
        //判断名称是否为空
        if(StringUtils.isEmpty(goodsClassifyRequest.getName())){
            logger.info("modify fail , because name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_CLASSIFY_DATA_NOT_EXISTS);
        }
        //判断分类编号是否为空
        if(StringUtils.isEmpty(goodsClassifyRequest.getParentId())){
            logger.info("modify fail , because parent id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_CLASSIFY_PARENT_ID_IS_EMPTY);
        }
        //商品分类名称内容长度是否超出DB许可长度
        try {
            if (goodsClassifyRequest.getName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > GOODS_CLASSIFY_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because name [" + goodsClassifyRequest.getName()
                        + "] bytes greater than" + GOODS_CLASSIFY_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.GOODS_CLASSIFY_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + goodsClassifyRequest.getName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //根据父分类编号查询父分类数据对象
        GoodsClassify goodsClassifyByParentId = goodsClassifyDao.selectByPrimaryKey(goodsClassifyRequest.getId());
        if (ObjectUtils.isEmpty(goodsClassifyByParentId)
                || StringUtils.isEmpty(goodsClassifyByParentId.getId())) {
            logger.info("add fail , because id [" + goodsClassifyRequest.getId() + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_CLASSIFY_DATA_NOT_EXISTS);
        }

        //构建DB对象
        GoodsClassify goodsClassify = new GoodsClassify();
        BeanUtils.copyProperties(goodsClassifyRequest,goodsClassify);
        goodsClassifyDao.updateByPrimaryKeySelective(goodsClassify);
    }

    /**
     * 删除
     * @param id
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeByIds(String... id) throws EqianyuanException {
        if(ObjectUtils.isEmpty(id)){
            logger.info("removeByIds fail , because id is null, a full table delete is prohibited");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_CLASSIFY_ID_IS_EMPTY);
        }
        //删除信息
        goodsClassifyDao.deleteByPrimaryKey(id);
    }

    /**
     * 根据父类编号获取子类集合
     * @param parentId
     * @return
     * @throws EqianyuanException
     */
    public List<GoodsClassifyBo> getLevelOneListByParentId(String parentId) throws EqianyuanException {
        //父分类编号是否为空
        if (StringUtils.isEmpty(parentId)) {
            logger.info("modify fail , because parent id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_CLASSIFY_PARENT_ID_IS_EMPTY);
        }
        List<GoodsClassify> goodsClassifies = goodsClassifyDao.selectLevelOneListByParentId(parentId);
        if(CollectionUtils.isEmpty(goodsClassifies)){
            logger.info("get level one list by parent id [" + parentId + "] is null");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_CLASSIFY_DATA_NOT_EXISTS);
        }
        List<GoodsClassifyBo> goodsClassifyBos = new ArrayList<GoodsClassifyBo>();
        for(GoodsClassify goodsClassify : goodsClassifies){
            GoodsClassifyBo goodsClassifyBo = new GoodsClassifyBo();
            BeanUtils.copyProperties(goodsClassify,goodsClassifyBo);
            goodsClassifyBos.add(goodsClassifyBo);
        }
        return goodsClassifyBos;
    }

}
