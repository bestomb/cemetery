package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.entity.Backpack;

/***
 * 背包接口
 * @author qfzhang
 *
 */
public interface IBackpackService {
	
	/***
	 * 根据查询条件查询会员背包商品分页列表
	 * @param backpack
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getGoodsPageList(Backpack backpack, Pager page) throws EqianyuanException;
	
	/***
	 * 查看背包商品详情
	 * @param backpack
	 * @return
	 */
	public Object getGoodsDetail(Backpack backpack) throws EqianyuanException;
	
}
