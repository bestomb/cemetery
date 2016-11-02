package com.bestomb.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.cemetery.BiontBo;
import com.bestomb.dao.IParkDao;
import com.bestomb.dao.IPlantsAndAnimalsDao;
import com.bestomb.entity.Biont;
import com.bestomb.entity.Park;
import com.bestomb.service.IParkService;

@Service
public class ParkServiceImpl implements IParkService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IParkDao parkDao;
	@Autowired
	private IPlantsAndAnimalsDao plantsAndAnimalsDao;
	
	public PageResponse getPageList(Biont biont, Pager page) throws EqianyuanException {
		// 陵园编号是否为空
		if (ObjectUtils.isEmpty(biont.getCemeteryId())) {
            logger.warn("getPageList fail , because cemeteryId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_ID_IS_EMPTY);
        }
		// 生物类型为空（必须传动物或者植物）
		if (ObjectUtils.isEmpty(biont.getType())) {
            logger.warn("getPageList fail , because type is null.");
            throw new EqianyuanException(ExceptionMsgConstant.BIONTTYPE_IS_EMPTY);
        }
		// 先查询总数
		int dataCount = parkDao.getPageListCount(biont);
		if ( dataCount == 0 ) {
	        logger.info("根据条件查询动植物分页列表无数据l");
	        return new PageResponse(page,  null);
	    }
		page.setTotalRow(dataCount);
		// 再查询分页数据
		List<Park> entityList = parkDao.getPageList(biont, page);
		return new PageResponse(page,  entityList);
	}

	public BiontBo getDetail(String goodsId) throws EqianyuanException {
		// 商品编号是否为空
		if (ObjectUtils.isEmpty(goodsId)) {
            logger.warn("getDetail fail , because goodsId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
        }
		Biont entity = plantsAndAnimalsDao.getDetail(goodsId);
		// 动植物是否存在
		if (ObjectUtils.isEmpty(entity)) {
            logger.warn("getDetail fail , because data is null.");
            throw new EqianyuanException(ExceptionMsgConstant.GOODS_DATA_NOT_EXISTS);
        }
		BiontBo bo = new BiontBo();
		BeanUtils.copyProperties(entity, bo);
		// 后面需要可以在这里做字典转换
		
		return bo;
	}

	public boolean upgrade(Biont biont) throws EqianyuanException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean pickUp(Biont biont) throws EqianyuanException {
		// TODO Auto-generated method stub
		return false;
	}

}
