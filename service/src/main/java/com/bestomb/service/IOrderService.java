package com.bestomb.service;

import java.util.List;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.entity.OrderGoodsWithBLOBs;
import com.bestomb.entity.PurchaseOrder;

/***
 * 订单接口
 * @author qfzhang
 *
 */
public interface IOrderService {
	
	/***
	 * 查询订单分页列表
	 * @param order
	 * @param page
	 * @return
	 * @throws EqianyuanException 
	 */
	public PageResponse getPageList(PurchaseOrder order, Pager page) throws EqianyuanException;
	
	/***
	 * 根据订单编号查询订单商品详情
	 * @param orderId
	 * @return
	 * @throws EqianyuanException
	 */
	public List<OrderGoodsWithBLOBs> getOrderGoodsByOrderId(String orderId) throws EqianyuanException;
	
	/***
	 * 订单支付
	 * @param orderId
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean orderPay(String orderId) throws EqianyuanException;
	
}
