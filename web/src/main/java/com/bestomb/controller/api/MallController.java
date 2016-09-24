package com.bestomb.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.goods.GoodsBo;
import com.bestomb.controller.BaseController;
import com.bestomb.entity.Goods;
import com.bestomb.entity.ShoppingCart;
import com.bestomb.sevice.api.MallService;

/***
 * 商城
 * @author admin
 *
 */
@Controller
@RequestMapping("/website/mall_api")
public class MallController extends BaseController {
	
	@Autowired
	private MallService mallService ;
	
	/***
     * 查询商城商品分页列表
     * @param goods
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value="/goods", method=RequestMethod.GET)
    @ResponseBody
    public ServerResponse getGoodsPageList(@ModelAttribute Goods goods, @ModelAttribute Pager page) throws EqianyuanException{
    	PageResponse pageResponse = mallService.getGoodsPageList(goods, page);
    	return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }
	
    /***
     * 查询商城商品详情
     * @param id
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value="/goods/{id}", method=RequestMethod.GET)
    @ResponseBody
    public ServerResponse getGoodsDetail(@PathVariable String id) throws EqianyuanException {
    	GoodsBo goods = mallService.getGoodsDetail(id);
    	return new ServerResponse.ResponseBuilder().data(goods).build();
    }
    
    /***
     * 加入购物车
     * @param cart 其中goodsId、count必填
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value="/joinCart", method=RequestMethod.POST)
    @ResponseBody
    public ServerResponse joinShoppingCart(@RequestBody ShoppingCart cart) throws EqianyuanException{
    	int memberId = getLoginMember().getMemberId();
    	cart.setMemberId(memberId);
    	boolean flag = mallService.joinShoppingCart(cart);
    	return new ServerResponse.ResponseBuilder().data(flag).build();
    }
    
    /***
     * 查询我的购物车
     * @param cart
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value="/myCart", method=RequestMethod.GET)
    @ResponseBody
    public ServerResponse queryShoppingCart() throws EqianyuanException{
    	int memberId = getLoginMember().getMemberId();
    	List<ShoppingCart> resultList = mallService.getMyShoppingCart(memberId);
    	return new ServerResponse.ResponseBuilder().data(resultList).build();
    }
    
    /***
     * 移出购物车
     * @param cart 其中id（主键）必填
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value="/removeCart", method=RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse removeShoppingCart(@RequestBody ShoppingCart cart) throws EqianyuanException{
    	int memberId = getLoginMember().getMemberId();
    	cart.setMemberId(memberId);
    	boolean flag = mallService.removeShoppingCart(cart);
    	return new ServerResponse.ResponseBuilder().data(flag).build();
    }
    
    /***
     * 购物车转订单
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value="/cartTurnToOrder", method=RequestMethod.POST)
    @ResponseBody
    public ServerResponse removeShoppingCart() throws EqianyuanException{
    	int memberId = getLoginMember().getMemberId();
    	boolean flag = mallService.cartTurnToOrder(memberId);
    	return new ServerResponse.ResponseBuilder().data(flag).build();
    }
    
}
