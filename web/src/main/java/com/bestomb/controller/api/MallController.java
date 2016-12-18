package com.bestomb.controller.api;

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
import com.bestomb.controller.BaseController;
import com.bestomb.entity.Mall;
import com.bestomb.sevice.api.MallService;

/***
 * 商城
 * @author qfzhang
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
    @RequestMapping(value="/goods", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ServerResponse getGoodsPageList(@ModelAttribute Mall mall, @ModelAttribute Pager page) throws EqianyuanException{
    	PageResponse pageResponse = mallService.getGoodsPageList(mall, page);
    	return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }
	
    /***
     * 查询商城商品详情
     * @param id
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value="/goods/{id}", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ServerResponse getGoodsDetail(@PathVariable String id, Mall mall) throws EqianyuanException {
    	mall.setGoodsId(id);
    	Object goods = mallService.getGoodsDetail(mall);
    	return new ServerResponse.ResponseBuilder().data(goods).build();
    }
    
}
