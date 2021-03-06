package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.goods.GoodsEditRequest;
import com.bestomb.common.response.FileResponse;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.goods.GoodsOfficialBO;
import com.bestomb.common.util.FileUtilHandle;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.IGoodsOfficialDao;
import com.bestomb.dao.ISecondClassificationGoodsRelatDao;
import com.bestomb.entity.GoodsOfficialWithBLOBs;
import com.bestomb.entity.Mall;
import com.bestomb.entity.SecondClassificationGoodsRelat;
import com.bestomb.service.IGoodsOfficialService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsOfficialServiceImpl implements IGoodsOfficialService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IGoodsOfficialDao goodsOfficialDao;

    @Autowired
    private ISecondClassificationGoodsRelatDao secondClassificationGoodsRelatDao;

    /**
     * 分页获取官网商城商品集合
     *
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(Pager page) throws EqianyuanException {
        int dataCount = goodsOfficialDao.getPageListCount(new Mall());

        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询官网商城商品列表无数据");
            return new PageResponse(page, null);
        }
        List<GoodsOfficialWithBLOBs> goodsOfficialList = goodsOfficialDao.getPageList(new Mall(), page);
        if (CollectionUtils.isEmpty(goodsOfficialList)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询官网商城商品列表无数据");
            return new PageResponse(page, null);
        }
        List<GoodsOfficialBO> goodsBOList = new ArrayList<GoodsOfficialBO>();
        for (GoodsOfficialWithBLOBs m : goodsOfficialList) {
            GoodsOfficialBO goodsBo = new GoodsOfficialBO();
            BeanUtils.copyProperties(m, goodsBo);
            goodsBOList.add(goodsBo);
        }
        return new PageResponse(page, goodsBOList);
    }

    /**
     * 根据商品分类和分页信息查询商品数据
     *
     * @param firstClass
     * @param secondClass
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String firstClass, String secondClass, Pager page) throws EqianyuanException {
        List<GoodsOfficialWithBLOBs> goodsOfficialList = null;
        if(StringUtils.isEmpty(secondClass)){
            Mall mall = new Mall();
            if(!StringUtils.isEmpty(firstClass)){
                mall.setType(Integer.parseInt(firstClass));
            }

            int dataCount = goodsOfficialDao.getPageListCount(mall);

            page.setTotalRow(dataCount);
            if (dataCount <= 0) {
                logger.info("根据条件查询官网商城商品列表无数据");
                return new PageResponse(page, null);
            }
            goodsOfficialList = goodsOfficialDao.getPageList(mall, page);
            if (CollectionUtils.isEmpty(goodsOfficialList)) {
                logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询官网商城商品列表无数据");
                return new PageResponse(page, null);
            }
        }else{
            //根据二级商品分类查询商品总数
            int dataCount = goodsOfficialDao.selectCountBySecondClassify(Integer.parseInt(secondClass));

            page.setTotalRow(dataCount);
            if (dataCount <= 0) {
                logger.info("根据条件查询官网商城商品列表无数据");
                return new PageResponse(page, null);
            }
            goodsOfficialList = goodsOfficialDao.selectBySecondClassify(Integer.parseInt(secondClass), page);
            if (CollectionUtils.isEmpty(goodsOfficialList)) {
                logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询官网商城商品列表无数据");
                return new PageResponse(page, null);
            }
        }

        List<GoodsOfficialBO> goodsBOList = new ArrayList<GoodsOfficialBO>();
        for (GoodsOfficialWithBLOBs m : goodsOfficialList) {
            GoodsOfficialBO goodsBo = new GoodsOfficialBO();
            BeanUtils.copyProperties(m, goodsBo);
            goodsBOList.add(goodsBo);
        }
        return new PageResponse(page, goodsBOList);
    }

    /**
     * 根据商品编号查询商品信息
     *
     * @param goodsId
     * @return
     * @throws EqianyuanException
     */
    public GoodsOfficialBO getInfo(String goodsId) throws EqianyuanException {
        GoodsOfficialWithBLOBs goodsOfficialWithBLOBs = goodsOfficialDao.selectByPrimaryKey(goodsId);
        if (ObjectUtils.isEmpty(goodsOfficialWithBLOBs)
                || ObjectUtils.isEmpty(goodsOfficialWithBLOBs.getId())) {
            logger.info("根据商品编号【" + goodsId + "】查询数据为空");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_DATA_NOT_EXISTS);
        }

        GoodsOfficialBO goodsOfficialBO = new GoodsOfficialBO();
        BeanUtils.copyProperties(goodsOfficialWithBLOBs, goodsOfficialBO);
        return goodsOfficialBO;
    }

    /**
     * 添加官网商城商品
     *
     * @param goodsEditRequest
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(GoodsEditRequest goodsEditRequest) throws EqianyuanException {
        //商品图片上传
        FileResponse fileResponse = FileUtilHandle.upload(goodsEditRequest.getImageFile());

        /**
         * 构建商品图片文件持久化目录地址
         * 目录结构：持久化上传目录/goods_official/文件
         */
        String imagePath = SystemConf.FILE_UPLOAD_FIXED_DIRECTORY.toString() + File.separator + "goods_official";

        //构建持久化商城商品数据
        GoodsOfficialWithBLOBs goodsOfficialWithBLOBs = new GoodsOfficialWithBLOBs();
        goodsOfficialWithBLOBs.setName(goodsEditRequest.getName());
        goodsOfficialWithBLOBs.setPrice(goodsEditRequest.getPrice());
        goodsOfficialWithBLOBs.setDescription(goodsEditRequest.getDescription());
        goodsOfficialWithBLOBs.setType(Integer.parseInt(goodsEditRequest.getType()));
        if(!StringUtils.isEmpty(goodsEditRequest.getLifecycle())){
            goodsOfficialWithBLOBs.setLifecycle(Integer.parseInt(goodsEditRequest.getLifecycle()));
        }
        goodsOfficialWithBLOBs.setExtendAttribute(goodsEditRequest.getExtendAttribute());
        goodsOfficialWithBLOBs.setImages(imagePath + File.separator + fileResponse.getFileName());

        //持久化商品数据
        goodsOfficialDao.insertSelective(goodsOfficialWithBLOBs);

        //构建二级分类与商品的关系数据
        SecondClassificationGoodsRelat secondClassificationGoodsRelat = new SecondClassificationGoodsRelat();
        secondClassificationGoodsRelat.setGoodsId(goodsOfficialWithBLOBs.getId());
        secondClassificationGoodsRelat.setClassificationId(goodsEditRequest.getSecondClassifyId());

        //将商品与二级分类关系数据进行持久化
        secondClassificationGoodsRelatDao.insertSelective(secondClassificationGoodsRelat);

        //将商品图片文件从临时上传目录移动到持久目录
        FileUtilHandle.moveFile(fileResponse.getFilePath(), imagePath);
    }

    /**
     * 修改官网商城商品
     *
     * @param goodsEditRequest
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void modify(GoodsEditRequest goodsEditRequest) throws EqianyuanException {
        //根据商品编号查询商品信息
        GoodsOfficialWithBLOBs goodsOfficialWithBLOBs = goodsOfficialDao.selectByPrimaryKey(goodsEditRequest.getId());
        if (ObjectUtils.isEmpty(goodsOfficialWithBLOBs) || ObjectUtils.isEmpty(goodsOfficialWithBLOBs.getId())) {
            logger.info("modify fail , because goods id [" + goodsEditRequest.getId() + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_DATA_NOT_EXISTS);
        }

        /**
         * 构建商品图片文件持久化目录地址
         * 目录结构：持久化上传目录/goods_official/文件
         */
        String imagePath = SystemConf.FILE_UPLOAD_FIXED_DIRECTORY.toString() + File.separator + "goods_official";

        FileResponse fileResponse = null;
        try {
            //商品图片上传
            fileResponse = FileUtilHandle.upload(goodsEditRequest.getImageFile());
            goodsOfficialWithBLOBs.setImages(imagePath + File.separator + fileResponse.getFileName());
        } catch (EqianyuanException e) {
            logger.info("商品图片上传失败");
        }

        goodsOfficialWithBLOBs.setName(goodsEditRequest.getName());
        goodsOfficialWithBLOBs.setPrice(goodsEditRequest.getPrice());
        goodsOfficialWithBLOBs.setDescription(goodsEditRequest.getDescription());
        goodsOfficialWithBLOBs.setType(Integer.parseInt(goodsEditRequest.getType()));
        if(!StringUtils.isEmpty(goodsEditRequest.getLifecycle())){
            goodsOfficialWithBLOBs.setLifecycle(Integer.parseInt(goodsEditRequest.getLifecycle()));
        }
        goodsOfficialWithBLOBs.setExtendAttribute(goodsEditRequest.getExtendAttribute());
        //持久化商品数据
        goodsOfficialDao.updateByPrimaryKeySelective(goodsOfficialWithBLOBs);

        //构建二级分类与商品的关系数据
        SecondClassificationGoodsRelat secondClassificationGoodsRelat = new SecondClassificationGoodsRelat();
        secondClassificationGoodsRelat.setGoodsId(goodsEditRequest.getId());
        secondClassificationGoodsRelat.setClassificationId(goodsEditRequest.getSecondClassifyId());

        //根据商品编号删除与二级分类的关系数据
        secondClassificationGoodsRelatDao.deleteByGoodsId(goodsEditRequest.getId());

        //将商品与二级分类关系数据进行持久化
        secondClassificationGoodsRelatDao.insertSelective(secondClassificationGoodsRelat);

        if (fileResponse != null) {
            //将商品图片文件从临时上传目录移动到持久目录
            FileUtilHandle.moveFile(fileResponse.getFilePath(), imagePath);
        }
    }

    /**
     * 删除官网商城商品
     *
     * @param goodsId
     * @throws EqianyuanException
     */
    public void removeByIds(String... goodsId) throws EqianyuanException {
        if (ObjectUtils.isEmpty(goodsId)) {
            logger.warn("商品数据删除失败，因为商品编号不存在.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }

        goodsOfficialDao.deleteByPrimaryKey(goodsId);
    }

    /**
     * 根据二级分类及分页信息查询商品集合
     *
     * @param secondClassify
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getGoodsBySecondClassify(Integer secondClassify, Pager page) throws EqianyuanException {
        int dataCount = goodsOfficialDao.selectCountBySecondClassify(secondClassify);

        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询官网商城商品列表无数据");
            return new PageResponse(page, null);
        }
        List<GoodsOfficialWithBLOBs> goodsOfficialList = goodsOfficialDao.selectBySecondClassify(secondClassify, page);
        if (CollectionUtils.isEmpty(goodsOfficialList)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询官网商城商品列表无数据");
            return new PageResponse(page, null);
        }
        List<GoodsOfficialBO> goodsBOList = new ArrayList<GoodsOfficialBO>();
        for (GoodsOfficialWithBLOBs m : goodsOfficialList) {
            GoodsOfficialBO goodsBo = new GoodsOfficialBO();
            BeanUtils.copyProperties(m, goodsBo);
            goodsBOList.add(goodsBo);
        }
        return new PageResponse(page, goodsBOList);
    }
}
