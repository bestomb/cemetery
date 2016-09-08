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
	 * 根据订单编号查询订单商品详情
	 * @param orderId
	 * @return
	 * @throws EqianyuanException
	 */
	public List<OrderGoodsWithBLOBs> getOrderGoodsByOrderId(String orderId) throws EqianyuanException {
		if (StringUtils.isEmpty(orderId)) {
			logger.warn("查询订单详情失败，订单编号为空");
			throw new EqianyuanException(ExceptionMsgConstant.ORDERID_IS_EMPTY); 
		}
		List<OrderGoodsWithBLOBs> resultList = orderGoodsDao.getOrderGoodsByOrderId(orderId);
		// 订单商品详情为空
		if (resultList.size() == 0) {
			logger.warn("查询订单详情失败，根据订单编号："+orderId+"查询商品详情无数据");
			throw new EqianyuanException(ExceptionMsgConstant.ORDER_GOODS_IS_NOT_EXISTS); 
		}
		
		return resultList;
	}
	
	/***
	 * 订单支付（修改订单状态为已完成）
	 * @param orderId
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean orderPay(String orderNumber) throws EqianyuanException {
		// 订单编号是否为空
		if (StringUtils.isEmpty(orderNumber)) {
			logger.warn("订单支付失败，订单编号为空");
			throw new EqianyuanException(ExceptionMsgConstant.ORDERID_IS_EMPTY); 
		}
		// 查询该条订单总金额
		PurchaseOrder record = purchaseOrderDao.selectByOrderNumber(orderNumber);
		// 订单数据是否存在
		if (ObjectUtils.isEmpty(record)) {
			logger.warn("订单支付失败，该订单数据不存在");
			throw new EqianyuanException(ExceptionMsgConstant.ORDER_IS_NOT_EXISTS); 
		}
		// 订单是否过期（status为-1表示已过期，或者当前时间距创建时间超过24小时，也表示已过期）
		if ("-1".equals(record.getStatus()) || CalendarUtil.getSystemSeconds()-record.getCreateTime() > 24*3600 ) {
			logger.warn("订单支付失败，该订单已过期");
			throw new EqianyuanException(ExceptionMsgConstant.ORDER_IS_OVERDUE); 
		}
		Double money = record.getPrice();
		//TODO 调用支付接口
		boolean flag = true;
		if (flag) {
			PurchaseOrder order = new PurchaseOrder();
			order.setOrderNumber(orderNumber);
			order.setStatus(2); // 已完成
			flag = purchaseOrderDao.updateByOrderNumber(order)>0 ;
			//TODO 将订单商品放入我的背包中
		}
		
		return flag;
	}

}
