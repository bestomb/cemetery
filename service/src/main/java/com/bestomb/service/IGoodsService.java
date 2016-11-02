package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.entity.GoodsWithBLOBs;
import com.bestomb.entity.Mall;

/***
 * 商品接口
 * @author qfzhang
 *
 */
public interface IGoodsService {
	
	/***
	 * 根据id查询商品详情
	 * @param id
	 * @return
	 * @throws EqianyuanException
	 */
	public Object getGoodsById(Mall mall) throws EqianyuanException;
	
	/***
	 * 添加商品
	 * @param record
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean insert(GoodsWithBLOBs record) throws EqianyuanException;
	
	/***
	 * 根据条件查询商品分页列表
	 * @param goods
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getGoodsPageList(Mall mall, Pager page) throws EqianyuanException;
	
	/***
	 * 删除商品
	 * @param id
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean delete(String id) throws EqianyuanException;
	
}
