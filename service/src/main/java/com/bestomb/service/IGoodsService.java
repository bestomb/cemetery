package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.goods.GoodsBo;
import com.bestomb.entity.Goods;
import com.bestomb.entity.GoodsWithBLOBs;

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
	public GoodsBo getGoodsById(String id) throws EqianyuanException;
	
	/***
	 * 根据id查询商品详情
	 * @param id
	 * @return
	 * @throws EqianyuanException
	 */
	public GoodsWithBLOBs getEntityGoodsById(String id) throws EqianyuanException;
	
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
	public PageResponse getGoodsPageList(Goods goods, Pager page) throws EqianyuanException;
	
}
