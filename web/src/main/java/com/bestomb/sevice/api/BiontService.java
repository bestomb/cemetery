package com.bestomb.sevice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.cemetery.BiontBo;
import com.bestomb.entity.Biont;
import com.bestomb.service.IParkService;

/***
 * 动植物api业务层调用
 * @author qfzhang
 *
 */

@Service
public class BiontService {
	
	@Autowired
	private IParkService parkService;
	
	/***
	 * 查询动植物分页列表
	 * @param biont
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getPageList(Biont biont, Pager page) throws EqianyuanException {
		return parkService.getPageList(biont, page);
	}
	
	/***
	 * 查询动植物详情
	 * @param biont
	 * @return
	 * @throws EqianyuanException
	 */
	public BiontBo getDetail(String goodsId) throws EqianyuanException{
		return parkService.getDetail(goodsId);
	}
	
}
