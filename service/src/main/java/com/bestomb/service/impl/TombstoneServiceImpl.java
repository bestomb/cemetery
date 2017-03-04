package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.TombstoneEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.tombstone.TombstoneBO;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.ITombstoneDao;
import com.bestomb.entity.Tombstone;
import com.bestomb.service.ITombstoneService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 陵园墓碑接口业务实现
 * Created by jason on 2016-10-18.
 */
@Service
public class TombstoneServiceImpl implements ITombstoneService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ITombstoneDao tombstoneDao;

    @Autowired
    private CommonService commonService;

    /**
     * 陵园创建墓碑
     *
     * @param tombstoneEditRequest
     * @throws EqianyuanException
     */
    public void addTombstone(TombstoneEditRequest tombstoneEditRequest) throws EqianyuanException {

        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(String.valueOf(tombstoneEditRequest.getCemeteryId()), tombstoneEditRequest.getMemberId());

        //根据陵园编号、墓碑种类和所建设墓碑的位置查询该陵园的墓碑类型下的建设位置有没有墓碑数据，如果有数据，则说明这个位置已经被使用，提示无法建设
        int positionCnt = tombstoneDao.selectByPosition(tombstoneEditRequest.getCemeteryId(), tombstoneEditRequest.getSpecies(), tombstoneEditRequest.getSort());
        if(positionCnt > 0){
            logger.info("当前位置已被使用");
            throw new EqianyuanException(ExceptionMsgConstant.TOMBSTON_POSITION_IS_NOT_EMPTY);
        }

        //构建持久化墓碑数据
        Tombstone tombstone = new Tombstone();
        tombstone.setCemeteryId(tombstoneEditRequest.getCemeteryId());
        tombstone.setMemberId(tombstoneEditRequest.getMemberId());
        tombstone.setSort(tombstoneEditRequest.getSort());
        tombstone.setType(tombstoneEditRequest.getType());
        tombstone.setSpecies(tombstoneEditRequest.getSpecies());
        tombstone.setCreateTime(CalendarUtil.getSystemSeconds());
        //持久陵园墓碑数据
        tombstoneDao.insertSelective(tombstone);
    }

    /**
     * 陵园墓碑信息修改
     *
     * @param tombstoneEditRequest
     * @throws EqianyuanException
     */
    public void modifyTombstone(TombstoneEditRequest tombstoneEditRequest) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(String.valueOf(tombstoneEditRequest.getCemeteryId()), tombstoneEditRequest.getMemberId());

        //根据墓碑编号查询墓碑数据
        Tombstone tombstone = tombstoneDao.selectByPrimaryKey(tombstoneEditRequest.getId());
        if (ObjectUtils.isEmpty(tombstone) || ObjectUtils.isEmpty(tombstone.getId())) {
            logger.info("modifyTombstone fail , because tombstone id [" + tombstoneEditRequest.getId() + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_TOMBSTONE_DATA_NOT_EXISTS);
        }

        if (tombstoneEditRequest.getType() < tombstone.getType()) {
            logger.info("modifyTombstone fail , Reduce the size of tomb Saturday to mark what is prohibited");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_TOMBSTONE_REDUCE_SIZE_IS_PROHIBITED);
        }

        tombstone.setSort(tombstoneEditRequest.getSort());
        tombstone.setType(tombstoneEditRequest.getType());

        //持久化修改
        tombstoneDao.updateByPrimaryKeySelective(tombstone);
    }

    /**
     * 删除陵园墓碑
     *
     * @param tombstoneId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    public void delTombstone(String tombstoneId, String cemeteryId, Integer memberId) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(cemeteryId, memberId);

        //删除数据
        tombstoneDao.deleteByPrimaryKey(tombstoneId);
        //todo 需要删除该墓碑墓主的相关其他数据/附件等
    }

    /**
     * 根据条件查询陵园墓碑分页集合
     *
     * @param tombstone
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getListByCondition(Tombstone tombstone, Pager page) throws EqianyuanException {
        int dataCount = tombstoneDao.countByCondition(tombstone);
        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询陵园墓碑列表无数据");
            return new PageResponse(page, null);
        }
        List<Tombstone> tombstones = tombstoneDao.selectByCondition(tombstone, page);
        if (CollectionUtils.isEmpty(tombstones)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询陵园墓碑列表无数据l");
            return new PageResponse(page, null);
        }
        List<TombstoneBO> tombstoneBOs = new ArrayList<TombstoneBO>();
        for (Tombstone m : tombstones) {
            TombstoneBO tombstoneBO = new TombstoneBO();
            BeanUtils.copyProperties(m, tombstoneBO);
            tombstoneBOs.add(tombstoneBO);
        }
        return new PageResponse(page, tombstoneBOs);
    }


}
