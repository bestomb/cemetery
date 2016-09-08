package com.bestomb.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.IBackpackDao;
import com.bestomb.dao.IOrderGoodsDao;
import com.bestomb.dao.IPurchaseOrderDao;
import com.bestomb.dao.IStoreDao;
import com.bestomb.entity.Backpack;
import com.bestomb.entity.Goods;
import com.bestomb.entity.GoodsWithBLOBs;
import com.bestomb.entity.OrderGoodsWithBLOBs;
import com.bestomb.entity.PurchaseOrder;
import com.bestomb.entity.Store;
import com.bestomb.entity.StoreWithGoods;
import com.bestomb.service.IGoodsService;
import com.bestomb.service.IStoreService;

/***
 * 会员店铺接口实现类
 * @author qfzhang
 *
 */
@Service
public class StoreServiceImpl implements IStoreService{
	
private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IGoodsService goodsService; // 商品接口
	@Autowired
	private IStoreDao storeDao;
	@Autowired
	private IBackpackDao backpackDao;
	@Autowired
	private IPurchaseOrderDao purchaseorderDao;
	@Autowired
    private IOrderGoodsDao orderGoodsDao;
	
	/***
	 * 根据查询条件查询会员店铺已发布商品分页列表
	 * @param memberId
	 * @param goods
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getPageList(Integer memberId, Goods goods, Pager page) throws EqianyuanException {
		// 会员编号是否为空
		if (ObjectUtils.isEmpty(memberId)) {
            logger.warn("getPageList fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
		Store store = new Store(memberId);
		// 先查询总数
		int dataCount = storeDao.getPageListCount(store, goods);
		if ( dataCount == 0 ) {
            logger.info("根据条件查询会员店铺已发布商品分页列表无数据l");
            return new PageResponse(page,  null);
        }
		page.setTotalRow(dataCount);
		// 再查询分页数据
		List<StoreWithGoods> storeWithGoods = storeDao.getPageList(store, goods, page);
		
		return new PageResponse(page,  storeWithGoods);
	}

	/***
	 * 店铺发布商品
	 * @param store
	 * @return
	 * @throws EqianyuanException
	 */
	@Transactional
	public boolean sellGoods(Goods goods, Store store) throws EqianyuanException {
		// 会员编号是否为空
		if (ObjectUtils.isEmpty(store.getMemberId())) {
			logger.warn("insert fail , because memberId is null");
			throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
		}
		// 背包商品是否为空
		if (StringUtils.isEmpty(store.getBackpackGoodsId())) {
            logger.warn("insert fail , because backpackGoodsId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_IS_NOTIN_BACKPACK);
        }
		// 商品ID是否为空
		if (StringUtils.isEmpty(store.getGoodsId())) {
            logger.warn("insert fail , because backpackGoodsId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
		// 商品发布名称为空
		if (StringUtils.isEmpty(goods.getName())) {
            logger.warn("sellGoods fail , because goodsName is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_NAME_IS_EMPTY);
        }
		// 商品发布价格为空
		if (ObjectUtils.isEmpty(goods.getPrice())) {
			logger.warn("sellGoods fail , because goodsPrice is null.");
			throw new EqianyuanException(ExceptionMsgConstant.GOODS_PRICE_IS_EMPTY);
		}
		// 查询背包商品详情
		GoodsWithBLOBs goodsWithBLOBs = goodsService.getEntityGoodsById(store.getBackpackGoodsId());
		if (ObjectUtils.isEmpty(goodsWithBLOBs)) {
			logger.warn("sellGoods fail , because backpackGoods is not exists.");
			throw new EqianyuanException(ExceptionMsgConstant.GOODS_DATA_NOT_EXISTS);
		}
		GoodsWithBLOBs newGoods = goodsWithBLOBs.clone();
		newGoods.setId(null); // 重置为null
		newGoods.setName(goods.getName());
		newGoods.setPrice(goods.getPrice());
		newGoods.setCreateTime(CalendarUtil.getSystemSeconds());
		// 先插入商品表
		boolean flag = goodsService.insert(newGoods);
		if (flag) {
			store.setGoodsId(newGoods.getId());
			// 再插入会员店铺表
			flag = storeDao.insert(store)>0;
			if (flag) {
				// 再修正背包中该商品数量
				store.setCount( 0-store.getCount() ); // 店铺发布商品后，背包数量应减少，将数量变为负值
				Backpack backpack = new Backpack(store);
				flag = backpackDao.modifyGoodsCount(backpack);
				if (!flag) {
					logger.info("会员（"+store.getMemberId()+"） 在店铺发布商品修正背包表backpack商品数量时，商品对象："+newGoods+"； 店铺对象："+store+"； 背包对象："+backpack);
					logger.error("会员（"+store.getMemberId()+"） 在店铺发布商品时，修正背包表backpack中商品数量失败！");
				}
			}else{
				logger.info("会员（"+store.getMemberId()+"） 在店铺发布商品插入会员店铺表时，商品对象："+newGoods+"； 店铺对象："+store);
				logger.error("会员（"+store.getMemberId()+"） 在店铺发布商品时，插入会员店铺表store失败！");
			}
		}else{
			logger.info("会员（"+store.getMemberId()+"） 在店铺发布商品插入商品表时，商品对象："+newGoods);
			logger.error("会员（"+store.getMemberId()+"） 在店铺发布商品时，插入商品表goods失败！");
		}
		return flag;
	}

	/***
     * 下架商品
     * @param id
     * @return
     * @throws EqianyuanException
     */
	@Transactional
	public boolean takebackGoods(Integer id) throws EqianyuanException {
		// 店铺主键ID是否为空
		if (ObjectUtils.isEmpty(id)) {
            logger.warn("takebackGoods fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.STOREID_IS_EMPTY);
        }
		Store store = storeDao.selectByPrimaryKey(id);
		Backpack backpack = new Backpack(store);
		// 先删除店铺表商品发布记录
		boolean flag = storeDao.deleteByPrimaryKey(id)>0;
		if (flag) {
			// 再修正背包表商品数量
			flag = backpackDao.modifyGoodsCount(backpack);
			if (!flag) {
				logger.info("会员（"+store.getMemberId()+"） 在店铺下架商品时，店铺对象："+store+"；背包对象："+backpack);
				logger.error("会员（"+store.getMemberId()+"） 在店铺下架商品时，修正背包表backpack商品数量失败！");
			}
		}else {
			logger.info("会员（"+store.getMemberId()+"） 在店铺下架商品时，店铺对象："+store);
			logger.error("会员（"+store.getMemberId()+"） 在店铺下架商品时，删除店铺表store商品发布记录失败！");
		}
		return flag;
	}

	/***
	 * 查询会员店铺销售订单分页列表
	 * @param memberId
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getStoreOrdersPageList(Integer memberId, Pager page) throws EqianyuanException {
		// 会员编号是否为空
		if (ObjectUtils.isEmpty(memberId)) {
            logger.warn("takebackGoods fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
		// 先查询总数
		int dataCount = purchaseorderDao.getStoreOrdersCount(memberId);
		if ( dataCount == 0 ) {
            logger.info("查询会员店铺销售订单分页列表无数据l");
            return new PageResponse(page,  null);
        }
		page.setTotalRow(dataCount);
		// 再查询分页数据
		List<PurchaseOrder> resultList = purchaseorderDao.getStoreOrders(memberId, page);
		
		return new PageResponse(page,  resultList);
	}
	
	/***
	 * 查询会员店铺销售订单详情
	 * @param orderId
	 * @param memberId
	 * @return
	 * @throws EqianyuanException 
	 */
	public List<OrderGoodsWithBLOBs> getStoreOrdersDetail(String orderId, Integer memberId) throws EqianyuanException {
		// 订单编号是否为空
		if (StringUtils.isEmpty(orderId)) {
            logger.warn("getStoreOrdersDetail fail , because orderId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.ORDERID_IS_EMPTY);
        }
		// 会员编号是否为空
		if (ObjectUtils.isEmpty(memberId)) {
            logger.warn("takebackGoods fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
		
		return orderGoodsDao.getStoreOrdersDetail(orderId, memberId);
	}

	/***
	 * 查询会员店铺订单销售总额
	 * @param memberId
	 * @return
	 * @throws EqianyuanException 
	 */
	public Double getStoreOrdersTotalPrice(Integer memberId) throws EqianyuanException {
		// 会员编号是否为空
		if (ObjectUtils.isEmpty(memberId)) {
            logger.warn("takebackGoods fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
		
		return storeDao.getStoreOrdersTotalPrice(memberId);
	}

}