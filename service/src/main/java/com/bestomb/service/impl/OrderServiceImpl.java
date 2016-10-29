package com.bestomb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.PurchaseOrderBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.IOrderGoodsDao;
import com.bestomb.dao.IPurchaseOrderDao;
import com.bestomb.entity.OrderGoodsWithBLOBs;
import com.bestomb.entity.PurchaseOrder;
import com.bestomb.service.IOrderService;

/***
 * 订单接口实现
 * @author Administrator
 *
 */
@Service
public class OrderServiceImpl implements IOrderService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IPurchaseOrderDao purchaseOrderDao;
	@Autowired
	private IOrderGoodsDao orderGoodsDao;
	
	/***
	 * 查询订单分页列表
	 * @param order
	 * @param page
	 * @return
	 */
	public PageResponse getPageList(PurchaseOrder order, Pager page) throws EqianyuanException{
		// 会员编号是否为空
		if (StringUtils.isEmpty(order.getMemberId())) {
            logger.warn("getPageList fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
		// 先查询总数
		int dataCount = purchaseOrderDao.getPageListCount(order);
		if ( dataCount == 0 ) {
            logger.info("根据条件查询订单列表无数据l");
            return new PageResponse(page,  null);
        }
		page.setTotalRow(dataCount);
		// 再查询数据
		List<PurchaseOrder> orders = purchaseOrderDao.getPageList(order, page);
		if ( CollectionUtils.isEmpty(orders) ) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询订单列表无数据l");
            return new PageResponse(page,  null);
        }
		// 使用BO返回
		List<PurchaseOrderBo> resultList = new ArrayList<PurchaseOrderBo>();
		for (PurchaseOrder o : orders) {
			PurchaseOrderBo bo = new PurchaseOrderBo();
			BeanUtils.copyProperties(o, bo);
			bo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(o.getCreateTime())); // 转化创建时间
			resultList.add(bo);
		}
		
		return new PageResponse(page,  resultList);
	}
	
	/***
	 * 订单支付（直接扣减会员交易币）
	 * @param orderId
	 * @return
	 * @throws EqianyuanException
	 */
	// TODO 需要重写！！！
	public boolean orderPay(String orderNumber) throws EqianyuanException {
		// 查询该条订单总金额（此处比较麻烦）
		// 查询会员交易币总额
		// 根据订单总金额扣减会员交易币（交易币足够直接扣减；否则抛出错误）
		// 将订单商品放入我的背包和我的订单中
		
		return true;
	}

}
