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
import com.bestomb.common.response.goods.GoodsBoWithCount;
import com.bestomb.dao.IOrderGoodsDao;
import com.bestomb.entity.Goods;
import com.bestomb.entity.GoodsWithBLOBs;
import com.bestomb.entity.OrderGoods;
import com.bestomb.entity.OrderGoodsWithBLOBs;
import com.bestomb.service.IBackpackService;

/***
 * 背包接口实现类
 * @author qfzhang
 *
 */
@Service
public class BackpackImpl implements IBackpackService{

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IOrderGoodsDao orderGoodsDao;
	
	/***
	 * 根据查询条件查询会员背包商品分页列表
	 * @param memberId
	 * @param goods
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getGoodsPageList(Integer memberId, Goods goods, Pager page) throws EqianyuanException {
		// 会员编号是否为空
		if (ObjectUtils.isEmpty(memberId)) {
            logger.warn("takebackGoods fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
		// 先查询总数
		int dataCount = orderGoodsDao.getBackpackGoodsCount(memberId, goods);
		if ( dataCount == 0 ) {
            logger.info("根据查询条件查询会员背包商品分页列表无数据l");
            return new PageResponse(page,  null);
        }
		page.setTotalRow(dataCount);
		// 再查询分页数据
		List<GoodsWithBLOBs> list = orderGoodsDao.getBackpackGoods(memberId, goods, page);
		List<GoodsBo> resultList = new ArrayList<GoodsBo>();
		for (GoodsWithBLOBs entity : list) {
			GoodsBo bo = new GoodsBo();
			BeanUtils.copyProperties(entity, bo);
			bo.convert(entity); // 转化商品数据
			resultList.add(bo);
		}
		
		return new PageResponse(page,  resultList);
	}

	/***
	 * 查看背包商品详情
	 * @param id
	 * @return
	 */
	public GoodsBoWithCount getGoodsById(String id) throws EqianyuanException {
		// 判断商品ID是否为空
		if (StringUtils.isEmpty(id)) {
            logger.warn("getGoodsById fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
		OrderGoods goods = new OrderGoods();
		goods.setGoodsId(id);
		OrderGoodsWithBLOBs entity = orderGoodsDao.selectByCondition(goods);
		// 商品数据是否存在
		if (ObjectUtils.isEmpty(entity)) {
            logger.warn("getGoodsById fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
		GoodsBoWithCount bo = new GoodsBoWithCount();
		BeanUtils.copyProperties(entity, bo);
		bo.convert(entity); // 转化商品数据
		return bo;
	}

}
