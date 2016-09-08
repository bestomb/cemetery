package com.bestomb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.goods.GoodsBo;
import com.bestomb.dao.IGoodsDao;
import com.bestomb.entity.Goods;
import com.bestomb.entity.GoodsWithBLOBs;
import com.bestomb.service.IGoodsService;

/***
 * 商品接口实现
 * @author qfzhang
 *
 */
@Service
public class GoodsServiceImpl implements IGoodsService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IGoodsDao goodsDao;
	
	/***
	 * 根据id查询商品详情
	 * @param id
	 * @return
	 * @throws EqianyuanException
	 */
	public GoodsBo getGoodsById(String id) throws EqianyuanException {
		// 判断商品ID是否为空
		if (StringUtils.isEmpty(id)) {
            logger.warn("getGoodsById fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
		GoodsWithBLOBs entity = goodsDao.selectByPrimaryKey(id);
		// 商品数据是否存在
		if (ObjectUtils.isEmpty(entity)) {
            logger.warn("getGoodsById fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
		GoodsBo bo = new GoodsBo();
		BeanUtils.copyProperties(entity, bo);
		bo.convert(entity); // 转化商品数据
		return bo;
	}
	
	/***
	 * 根据id查询商品详情
	 * @param id
	 * @return
	 * @throws EqianyuanException
	 */
	public GoodsWithBLOBs getEntityGoodsById(String id) throws EqianyuanException {
		// 判断商品ID是否为空
		if (StringUtils.isEmpty(id)) {
            logger.warn("getGoodsById fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
		GoodsWithBLOBs entity = goodsDao.selectByPrimaryKey(id);
		// 商品数据是否存在
		if (ObjectUtils.isEmpty(entity)) {
            logger.warn("getGoodsById fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
		return entity;
	}
	
	/***
	 * 添加商品
	 * @param record
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean insert(GoodsWithBLOBs record) throws EqianyuanException {
		// 商品名称为空
		if (StringUtils.isEmpty(record.getName())) {
            logger.warn("insert fail , because goodsName is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_NAME_IS_EMPTY);
        }
		// 商品价格为空
		if (ObjectUtils.isEmpty(record.getPrice())) {
			logger.warn("insert fail , because goodsPrice is null.");
			throw new EqianyuanException(ExceptionMsgConstant.GOODS_PRICE_IS_EMPTY);
		}
		return goodsDao.insertSelective(record)>0 ;
	}

	/***
	 * 根据条件查询商品分页列表
	 * @param goods
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getGoodsPageList(Goods goods, Pager page) throws EqianyuanException {
		// 先查询总数
		int dataCount = goodsDao.getGoodsPageListCount(goods);
		if ( dataCount == 0 ) {
	        logger.info("根据条件查询商品分页列表无数据l");
	        return new PageResponse(page,  null);
	    }
		page.setTotalRow(dataCount);
		// 再查询分页数据
		List<GoodsWithBLOBs> list = goodsDao.getGoodsPageList(goods, page);
		List<GoodsBo> resultList = new ArrayList<GoodsBo>();
		for (GoodsWithBLOBs entity : list) {
			GoodsBo bo = new GoodsBo();
			BeanUtils.copyProperties(entity, bo);
			bo.convert(entity); // 转化商品数据
			resultList.add(bo);
		}
		
		return new PageResponse(page,  resultList);
	}
	

}
