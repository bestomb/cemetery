package com.bestomb.service;

import java.util.List;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.entity.ShoppingCart;

/***
 * 购物车接口
 * @author qfzhang
 *
 */
public interface IShoppingCartService {
	
	/***
	 * 加入购物车
	 * @param cart
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean join(ShoppingCart cart) throws EqianyuanException;
	
	/***
	 * 查询会员购物车
	 * @param memberId
	 * @return
	 * @throws EqianyuanException
	 */
	public List<ShoppingCart> query(Integer memberId) throws EqianyuanException ;
	
	/***
	 * 移出购物车
	 * @param cart
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean remove(ShoppingCart cart) throws EqianyuanException;

	/***
	 * 购物车转订单
	 * @param memberId
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean turnToOrder(Integer memberId) throws EqianyuanException;
}
