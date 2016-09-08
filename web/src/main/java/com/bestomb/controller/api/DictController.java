package com.bestomb.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestomb.common.exception.DictException;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.controller.BaseController;
import com.bestomb.entity.Dict;
import com.bestomb.service.IDictService;

/***
 * 字典控制器
 * @author qfzhang
 *
 */
@Controller
@RequestMapping("/website/dict_api")
public class DictController extends BaseController {
	
	@Autowired
    private IDictService dictService;
	
	/***
     * 根据字典类型查询字典集合
     * @param dictType 字典类型
     * @return
	 * @throws DictException 
     */
    @RequestMapping(value="/dicts/{dictType}", method=RequestMethod.GET)
    @ResponseBody
    public ServerResponse getGoodsType(@PathVariable String dictType) throws DictException{
    	List<Dict> dictList = dictService.getDictList(dictType);
    	return new ServerResponse.ResponseBuilder().data(dictList).build();
    }
    
}
