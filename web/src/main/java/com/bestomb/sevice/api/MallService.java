package com.bestomb.sevice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.entity.Mall;
import com.bestomb.service.IGoodsService;

/***
 * 商城api业务层调用
 * @author qfzhang
 *
 */
@Service
public class MallService {
	
    @Autowired
    private IGoodsService goodsService; // 商品接口
	
    /***
     * 查询商城商品分页列表
     * @param goods
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getGoodsPageList(Mall mall, Pager page) throws EqianyuanException {
    	return goodsService.getGoodsPageList(mall, page);
    }
    
    /***
	 * 获取商品详情
	 * @param id
	 * @return
	 * @throws EqianyuanException
	 */
	public Object getGoodsDetail(Mall mall) throws EqianyuanException{
		return goodsService.getGoodsById(mall);
	}
    
}
