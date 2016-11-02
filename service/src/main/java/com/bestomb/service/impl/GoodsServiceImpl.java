package com.bestomb.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.dao.IGoodsDao;
import com.bestomb.dao.IGoodsOfficialDao;
import com.bestomb.dao.IGoodsPersonageDao;
import com.bestomb.entity.GoodsWithBLOBs;
import com.bestomb.entity.Mall;
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
	@Autowired
	private IGoodsOfficialDao goodsOfficialDao;
	@Autowired
	private IGoodsPersonageDao goodsPersonageDao;
	
	/***
	 * 根据条件查询商品分页列表
	 * @param goods
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getGoodsPageList(Mall mall, Pager page) throws EqianyuanException {
		
		// 商城类型为空
		if (ObjectUtils.isEmpty(mall.getMallType())) {
            logger.warn("query fail , because mailType is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MALLTYPE_IS_EMPTY);
        }
		// 先查询总数
		int dataCount = 0;
		if ( mall.getMallType() == 1 ) { // 官方商城
			dataCount = goodsOfficialDao.getPageListCount(mall);
		}else if ( mall.getMallType() == 2 ) { // 个人商城
			dataCount = goodsPersonageDao.getPageListCount(mall);
		}else { // 动植物
			//TODO
		}
		if ( dataCount == 0 ) {
	        logger.info("根据条件查询商品分页列表无数据l");
	        return new PageResponse(page,  null);
	    }
		page.setTotalRow(dataCount);
		// 再查询分页数据
		List<?> entityList = null;
		if ( mall.getMallType() == 1 ) { // 官方商城
			entityList = goodsOfficialDao.getPageList(mall, page);
		}else if ( mall.getMallType() == 2 ) { // 个人商城
			entityList = goodsPersonageDao.getPageList(mall, page);
		}else{ // 动植物
			//TODO
		}
		return new PageResponse(page,  entityList);
	}
	
	/***
	 * 根据id查询商品详情
	 * @param id
	 * @return
	 * @throws EqianyuanException
	 */
	public Object getGoodsById(Mall mall) throws EqianyuanException {
		
		// 商城类型为空
		if (ObjectUtils.isEmpty(mall.getMallType())) {
            logger.warn("query fail , because mailType is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MALLTYPE_IS_EMPTY);
        }
		// 判断商品ID是否为空
		if (StringUtils.isEmpty(mall.getGoodsId())) {
            logger.warn("getGoodsById fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
		Object goods = null;
		if ( mall.getMallType() == 1 ) { // 官方商城
			goods = goodsOfficialDao.selectByPrimaryKey(mall.getGoodsId());
		}else if ( mall.getMallType() == 2 ) { // 个人商城
			goods = goodsPersonageDao.selectByPrimaryKey(mall.getGoodsId());
		}else { // 动植物
			//TODO
		}
		// 商品数据是否存在
		if (ObjectUtils.isEmpty(goods)) {
            logger.warn("getGoodsById fail , because data is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_DATA_NOT_EXISTS);
        }
		
		return goods;
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
	 * 删除商品
	 * @param id
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean delete(String id) throws EqianyuanException {
		// 判断商品ID是否为空
		if (StringUtils.isEmpty(id)) {
            logger.warn("getGoodsById fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
		return goodsDao.deleteByPrimaryKey(id)>0;
	}
	

}
