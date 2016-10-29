package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.lifeWorksCollection.LifeWorksCollectionEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.lifeWorksCollection.LifeWorksCollectionBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.ILifeWorksCollectionDao;
import com.bestomb.entity.LifeWorksCollection;
import com.bestomb.service.ILifeWorksCollectionService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 陵园纪念人作品集接口业务实现
 * Created by jason on 2016-10-18.
 */
@Service
public class LifeWorksCollectionServiceImpl implements ILifeWorksCollectionService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ILifeWorksCollectionDao lifeWorksCollectionDao;

    @Autowired
    private CommonService commonService;

    /**
     * 创建纪念人作品集
     *
     * @param lifeWorksCollectionEditRequest
     * @throws EqianyuanException
     */
    public void add(LifeWorksCollectionEditRequest lifeWorksCollectionEditRequest) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(lifeWorksCollectionEditRequest.getCemeteryId(), lifeWorksCollectionEditRequest.getMemberId());

        //构建持久化作品集数据
        LifeWorksCollection lifeWorksCollection = new LifeWorksCollection();
        lifeWorksCollection.setMasterId(lifeWorksCollectionEditRequest.getMasterId());
        lifeWorksCollection.setName(lifeWorksCollectionEditRequest.getName());
        lifeWorksCollection.setRemark(lifeWorksCollectionEditRequest.getRemark());
        lifeWorksCollection.setCreateTime(CalendarUtil.getSystemSeconds());
        //持久陵园纪念人作品集数据
        lifeWorksCollectionDao.insertSelective(lifeWorksCollection);
    }

    /**
     * 根据作品集编号删除数据
     *
     * @param lifeWorksCollectionId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    public void del(String lifeWorksCollectionId, String cemeteryId, Integer memberId) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(cemeteryId, memberId);

        //删除数据
        lifeWorksCollectionDao.deleteByPrimaryKey(lifeWorksCollectionId);
        //todo 需要删除该作品集的所有作品相关数据
    }

    /**
     * 根据纪念人及分页信息查询作品集分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String masterId, Pager page) throws EqianyuanException {
        // 根据条件查询作品集列表
        int dataCount = lifeWorksCollectionDao.countByCondition(masterId);
        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询陵园纪念人作品集列表无数据");
            return new PageResponse(page, null);
        }
        List<LifeWorksCollection> lifeWorksCollections = lifeWorksCollectionDao.selectByCondition(masterId, page);
        if (CollectionUtils.isEmpty(lifeWorksCollections)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询陵园纪念人作品集列表无数据");
            return new PageResponse(page, null);
        }
        List<LifeWorksCollectionBo> lifeWorksCollectionBos = new ArrayList<LifeWorksCollectionBo>();
        for (LifeWorksCollection m : lifeWorksCollections) {
            LifeWorksCollectionBo lifeWorksCollectionBo = new LifeWorksCollectionBo();
            BeanUtils.copyProperties(m, lifeWorksCollectionBo);
            lifeWorksCollectionBos.add(lifeWorksCollectionBo);
        }
        return new PageResponse(page, lifeWorksCollectionBos);
    }


}
