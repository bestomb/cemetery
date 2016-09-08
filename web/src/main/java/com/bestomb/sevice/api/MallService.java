package com.bestomb.sevice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.goods.GoodsBo;
import com.bestomb.entity.Goods;
import com.bestomb.entity.ShoppingCart;
import com.bestomb.service.IGoodsService;
import com.bestomb.service.IShoppingCartService;

/***
 * 商城api业务层调用
 * @author qfzhang
 *
 */
@Service
public class MallService {
	
    @Autowired
    private IGoodsService goodsService; // 商品接口
    @Autowired
    private IShoppingCartService cartService; // 购物车接口
	
    /***
     * 查询商城商品分页列表
     * @param goods
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getGoodsPageList(Goods goods, Pager page) throws EqianyuanException {
    	return goodsService.getGoodsPageList(goods, page);
    }
    
    /***
	 * 获取商品详情
	 * @param id
	 * @return
	 * @throws EqianyuanException
	 */
	public GoodsBo getGoodsDetail(String id) throws EqianyuanException{
		return goodsService.getGoodsById(id);
	}
    
	/***
	 * 加入购物车
	 * @param cart
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean joinShoppingCart(ShoppingCart cart) throws EqianyuanException{
		return cartService.join(cart);
	}
	
	/***
	 * 移出购物车
	 * @param cart
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean removeShoppingCart(ShoppingCart cart) throws EqianyuanException{
		return cartService.remove(cart);
	}
	
	/***
	 * 购物车转订单
	 * @param memberId
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean cartTurnToOrder(Integer memberId) throws EqianyuanException{
		return cartService.turnToOrder(memberId);
	}
	
}
