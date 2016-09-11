package com.bestomb.service;

import java.util.List;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.entity.Goods;
import com.bestomb.entity.OrderGoodsWithBLOBs;
import com.bestomb.entity.StoreWithGoods;

/***
 * 会员店铺接口
 * @author qfzhang
 *
 */
public interface IStoreService {
	
	/***
	 * 根据查询条件查询会员店铺已发布商品分页列表
	 * @param memberId
	 * @param goods
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getPageList(Integer memberId, Goods goods, Pager page) throws EqianyuanException;

	/***
	 * 店铺发布商品
	 * @param storeWithGoods
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean sellGoods(StoreWithGoods storeWithGoods) throws EqianyuanException;

	/***
     * 下架商品
     * @param id
     * @return
     * @throws EqianyuanException
     */
	public boolean takebackGoods(Integer id) throws EqianyuanException;
	
	/***
	 * 查询会员店铺销售订单分页列表
	 * @param memberId
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getStoreOrdersPageList(Integer memberId, Pager page)throws EqianyuanException;
	
	/***
	 * 查询会员店铺销售订单详情
	 * @param orderId
	 * @return
	 * @throws EqianyuanException 
	 */
	public List<OrderGoodsWithBLOBs> getStoreOrdersDetail(String orderId, Integer memberId) throws EqianyuanException;

	/***
	 * 查询会员店铺订单销售总额
	 * @param memberId
	 * @return
	 * @throws EqianyuanException 
	 */
	public Double getStoreOrdersTotalPrice(Integer memberId) throws EqianyuanException;
	
}
