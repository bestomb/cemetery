package com.bestomb.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.dao.ICommonDao;
import com.bestomb.dao.IOrderGoodsDao;
import com.bestomb.dao.IPurchaseOrderDao;
import com.bestomb.dao.IShoppingCartDao;
import com.bestomb.entity.PurchaseOrder;
import com.bestomb.entity.ShoppingCart;
import com.bestomb.service.IShoppingCartService;

/***
 * 购物车接口实现类
 * @author qfzhang
 *
 */
@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ICommonDao commonDao;
	@Autowired
	private IShoppingCartDao shoppingCartDao;
	@Autowired
	private IPurchaseOrderDao purchaseOrderDao;
	@Autowired
	private IOrderGoodsDao orderGoodsDao;
	
	/***
	 * 加入购物车
	 * @param cart
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean join(ShoppingCart cart) throws EqianyuanException {
		// 会员编号为空
		if (StringUtils.isEmpty(cart.getMemberId())) {
            logger.warn("join fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
		// 商品ID为空
		if (StringUtils.isEmpty(cart.getGoodsId())) {
			logger.warn("join fail , because goodsId is null.");
			throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
		}
		return shoppingCartDao.insertSelective(cart)>0;
	}
	
	/***
	 * 移出购物车
	 * @param cart
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean remove(ShoppingCart cart) throws EqianyuanException {
		// 购物车主键ID为空
		if (StringUtils.isEmpty(cart.getId())) {
			logger.warn("remove fail , because cartId is null.");
			throw new EqianyuanException(ExceptionMsgConstant.CARTID_IS_EMPTY);
		}
		return shoppingCartDao.deleteByPrimaryKey(cart.getId())>0;
	}

	/***
	 * 购物车转订单
	 * @param memberId
	 * @return
	 * @throws EqianyuanException
	 */
	@Transactional
	public boolean turnToOrder(Integer memberId) throws EqianyuanException {
		// 会员编号为空
		if (StringUtils.isEmpty(memberId)) {
            logger.warn("join fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
		// 获取会员购物车总金额
		Double totalPrice = shoppingCartDao.getTotalPriceByMemberId(memberId);
		if (totalPrice == 0) { // 通过总金额为0来判断购物车为空
			logger.warn("turnToOrder fail , because shoppingCart is empty.");
			throw new EqianyuanException(ExceptionMsgConstant.SHOPPINGCART_IS_EMPTY);
		}
		String orderNumber = commonDao.getUUID(); // 获取数据库UUID编号生成新的订单编号
		PurchaseOrder order = new PurchaseOrder(orderNumber, memberId, totalPrice);
		// 先插入会员订单表
		boolean flag = purchaseOrderDao.insert(order)>0;
		if (flag) {
			// 再插入订单商品表
			flag = orderGoodsDao.insertCartGoods(order)>0;
			if (flag) {
				// 最后清空会员购物车
				flag = shoppingCartDao.removeMemberCart(memberId);
				if (!flag) {
					logger.info("会员（"+memberId+"） 在购物车转订单时，会员订单对象："+order);
					logger.error("会员（"+memberId+"） 在购物车转订单时，清空会员购物车失败！");
				}
			}else{
				logger.info("会员（"+memberId+"） 在购物车转订单时，会员订单对象："+order);
				logger.error("会员（"+memberId+"） 在购物车转订单时，插入订单商品表失败！");
			}
		}else{
			logger.info("会员（"+memberId+"） 在购物车转订单时，会员订单对象："+order);
			logger.error("会员（"+memberId+"） 在购物车转订单时，插入会员订单表失败！");
		}
		
		return flag;
	}

}
