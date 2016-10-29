package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.lifeWorksCollection.lifeWorks.LifeWorksEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.lifeWorksCollection.lifeWorks.LifeWorksBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.ICemeteryDao;
import com.bestomb.dao.ILifeWorksDao;
import com.bestomb.entity.Cemetery;
import com.bestomb.entity.LifeWorks;
import com.bestomb.service.ILifeWorksService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 陵园纪念人作品集作品接口业务实现
 * Created by jason on 2016-10-18.
 */
@Service
public class LifeWorksServiceImpl implements ILifeWorksService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ILifeWorksDao lifeWorksDao;

    @Autowired
    private ICemeteryDao cemeteryDao;

    @Autowired
    private CommonService commonService;

    /**
     * 为作品集添加作品
     *
     * @param lifeWorksEditRequest
     * @throws EqianyuanException
     */
    public void add(LifeWorksEditRequest lifeWorksEditRequest) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        Cemetery cemetery = commonService.hasPermissionsByCemetery(lifeWorksEditRequest.getCemeteryId(), lifeWorksEditRequest.getMemberId());

        //插入作品数据
        LifeWorks lifeWorks = new LifeWorks();
        lifeWorks.setName(lifeWorksEditRequest.getName());
        lifeWorks.setContent(lifeWorksEditRequest.getContent());
        lifeWorks.setCollectionId(lifeWorksEditRequest.getCollectionId());
        lifeWorks.setCreateTime(CalendarUtil.getSystemSeconds());
        lifeWorksDao.insertSelective(lifeWorks);
    }

    /**
     * 根据作品编号删除作品数据
     *
     * @param lifeWorksId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    public void del(String lifeWorksId, String cemeteryId, Integer memberId) throws EqianyuanException {
        if (StringUtils.isEmpty(lifeWorksId)) {
            logger.warn("作品数据删除失败，因为作品编号不存在.");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
        }

        //根据视频编号查询视频数据
        LifeWorks lifeWorks = lifeWorksDao.selectByPrimaryKey(lifeWorksId);

        if (!ObjectUtils.isEmpty(lifeWorks)
                && !ObjectUtils.isEmpty(lifeWorks.getId())) {
            //检查当前登录会员是否拥有对该陵园的管理权限
            Cemetery cemetery = commonService.hasPermissionsByCemetery(cemeteryId, memberId);

            //删除作品数据
            lifeWorksDao.deleteByPrimaryKey(lifeWorks.getId());
        }
    }

    /**
     * 根据作品及编号编号查询作品分页集合
     *
     * @param lifeWorksCollectionId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String lifeWorksCollectionId, Pager page) throws EqianyuanException {
        // 根据条件查询视频列表
        int dataCount = lifeWorksDao.countByCondition(lifeWorksCollectionId);
        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询纪念人作品集作品列表无数据");
            return new PageResponse(page, null);
        }
        List<LifeWorks> lifeWorkses = lifeWorksDao.selectByCondition(lifeWorksCollectionId, page);
        if (CollectionUtils.isEmpty(lifeWorkses)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询纪念人作品集作品列表无数据");
            return new PageResponse(page, null);
        }
        List<LifeWorksBo> lifeWorksBos = new ArrayList<LifeWorksBo>();
        for (LifeWorks m : lifeWorkses) {
            LifeWorksBo lifeWorksBo = new LifeWorksBo();
            BeanUtils.copyProperties(m, lifeWorksBo);
            lifeWorksBos.add(lifeWorksBo);
        }
        return new PageResponse(page, lifeWorksBos);
    }

    /**
     * 根据作品编号查询作品详情
     *
     * @param lifeWorksId
     * @return
     * @throws EqianyuanException
     */
    public LifeWorksBo getInfo(String lifeWorksId) throws EqianyuanException {
        LifeWorks lifeWorks = lifeWorksDao.selectByPrimaryKey(lifeWorksId);
        if (ObjectUtils.isEmpty(lifeWorks)
                || ObjectUtils.isEmpty(lifeWorks.getId())) {
            logger.info("根据作品编号【" + lifeWorksId + "】查询数据为空");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_LIFE_WORKS_DATA_NOT_EXISTS);
        }

        LifeWorksBo lifeWorksBo = new LifeWorksBo();
        BeanUtils.copyProperties(lifeWorks, lifeWorksBo);
        return lifeWorksBo;
    }


}
