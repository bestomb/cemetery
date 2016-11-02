package com.bestomb.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.dao.IBackpackDao;
import com.bestomb.entity.Backpack;
import com.bestomb.entity.Mall;
import com.bestomb.service.IBackpackService;
import com.bestomb.service.IGoodsService;

/***
 * 背包接口实现类
 * @author qfzhang
 *
 */
@Service
public class BackpackImpl implements IBackpackService{

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IBackpackDao backpackDao;
	@Autowired
	private IGoodsService goodsService;
	
	/***
	 * 根据查询条件查询会员背包商品分页列表
	 * @param backpack
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getGoodsPageList(Backpack backpack, Pager page) throws EqianyuanException {
		
		// 会员编号是否为空
		if (ObjectUtils.isEmpty(backpack.getMemberId())) {
            logger.warn("query fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
		// 背包来源（商城类型）为空
		if (ObjectUtils.isEmpty(backpack.getSource())) {
            logger.warn("query fail , because source is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MALLTYPE_IS_EMPTY);
        }
		// 先查询总数
		int dataCount = backpackDao.getPageListCount(backpack);
		if ( dataCount == 0 ) {
	        logger.info("根据条件查询背包分页列表无数据l");
	        return new PageResponse(page,  null);
	    }
		page.setTotalRow(dataCount);
		// 再查询分页数据
		List<Backpack> entityList = backpackDao.getPageList(backpack, page);
		return new PageResponse(page,  entityList);
		
	}

	/***
	 * 查看背包商品详情
	 * @param backpack
	 * @return
	 */
	public Object getGoodsDetail(Backpack backpack) throws EqianyuanException {
		
		Mall mall = new Mall(backpack.getSource(), backpack.getGoodsId());
		return goodsService.getGoodsById(mall);
	}

}
