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
import com.bestomb.common.response.cemetery.BiontBo;
import com.bestomb.controller.BaseController;
import com.bestomb.entity.Biont;
import com.bestomb.sevice.api.BiontService;

/***
 * 动植物API接口
 * @author qfzhang
 *
 */

@Controller
@RequestMapping("/website/biont_api")
public class BiontController extends BaseController{
	
	@Autowired
	private BiontService biontService ;
	
	
	/***
     * 查询动植物分页列表
     * @param goods
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value="/bionts", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ServerResponse getBiontPageList(@ModelAttribute Biont biont, @ModelAttribute Pager page) throws EqianyuanException{
    	PageResponse pageResponse = biontService.getPageList(biont, page);
    	return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }
	
    /***
     * 查询动植物详情
     * @param id
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value="/bionts/{id}", method={RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ServerResponse getGoodsDetail(@PathVariable String id, @RequestBody Biont biont) throws EqianyuanException {
    	biont.setGoodsId(id);
    	BiontBo biontBo = biontService.getDetail(id);
    	return new ServerResponse.ResponseBuilder().data(biontBo).build();
    }
    
}
