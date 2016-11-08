package com.bestomb.service.impl;

import java.util.List;

import com.bestomb.dao.IBackpackDao;
import com.bestomb.entity.Backpack;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	private IParkDao parkDao; // 公园Dao层接口
	@Autowired
	private IPlantsAndAnimalsDao plantsAndAnimalsDao; // 动植物Dao层接口
	@Autowired
	private IBackpackDao backpackDao; // 背包Dao层接口
	
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
		// 陵园编号是否为空
		if (StringUtils.isEmpty(biont.getCemeteryId())) {
			logger.warn("upgrade fail , because cemeteryId is null.");
			throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_ID_IS_EMPTY);
		}
		// 商品编号是否为空
		if (StringUtils.isEmpty(biont.getGoodsId())) {
			logger.warn("upgrade fail , because goodsId is null.");
			throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
		}

		return parkDao.upgrade(biont)>0;
	}

	@Transactional
	public boolean pickUp(Biont biont, Integer memberId) throws EqianyuanException {

		boolean flag = false;
		// 陵园编号是否为空
		if (StringUtils.isEmpty(biont.getCemeteryId())) {
			logger.warn("pickUp fail , because cemeteryId is null.");
			throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_ID_IS_EMPTY);
		}
		// 商品编号是否为空
		if (StringUtils.isEmpty(biont.getGoodsId())) {
			logger.warn("pickUp fail , because goodsId is null.");
			throw new EqianyuanException(ExceptionMsgConstant.GOODSID_IS_EMPTY);
		}
		// 将该动植物放入会员背包中
		Backpack backpack = new Backpack(memberId, 3, biont.getGoodsId());
		flag = backpackDao.insert(backpack)>0;
		// park表中删除该动植物数据
		if (flag) {
			flag = parkDao.deleteByCondition(biont)>0;
			if (!flag) {
				logger.error("会员（"+memberId+"）在陵园（"+biont.getCemeteryId()+"）中拾取动植物（"+biont.getGoodsId()+"）时，将该动植物从park表中删除失败！");
			}
		}else{
			logger.error("会员（"+memberId+"）在陵园（"+biont.getCemeteryId()+"）中拾取动植物（"+biont.getGoodsId()+"）时，将该动植物插入会员背包表中失败！");
		}
		return flag;
	}

}
