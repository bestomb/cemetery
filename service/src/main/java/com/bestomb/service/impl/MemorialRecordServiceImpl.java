package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.memorialRecord.MemorialRecordEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.memorialRecord.MemorialRecordBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.IMemorialRecordDao;
import com.bestomb.entity.MemorialRecord;
import com.bestomb.service.IMemorialRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 陵园墓碑纪念人纪念记录接口业务实现
 * Created by jason on 2016-10-18.
 */
@Service
public class MemorialRecordServiceImpl implements IMemorialRecordService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IMemorialRecordDao memorialRecordDao;

    /**
     * 保存纪念记录
     *
     * @param memorialRecordEditRequest
     * @throws EqianyuanException
     */
    public void add(MemorialRecordEditRequest memorialRecordEditRequest) throws EqianyuanException {
        //构建持久化纪念记录数据
        MemorialRecord memorialRecord = new MemorialRecord();
        memorialRecord.setMasterId(memorialRecordEditRequest.getMasterId());
        memorialRecord.setContent(memorialRecordEditRequest.getContent());
        memorialRecord.setMemberId(memorialRecordEditRequest.getMemberId());
        memorialRecord.setCreateTime(CalendarUtil.getSystemSeconds());
        //持久陵园纪念记录数据
        memorialRecordDao.insertSelective(memorialRecord);
    }

    /**
     * 根据纪念人编号分页查询纪念人纪念记录
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String masterId, Pager page) throws EqianyuanException {
        // 根据条件查询音乐列表
        int dataCount = memorialRecordDao.countByCondition(masterId);
        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询陵园纪念人纪念记录列表无数据");
            return new PageResponse(page, null);
        }
        List<MemorialRecord> memorialRecords = memorialRecordDao.selectByCondition(masterId, page);
        if (CollectionUtils.isEmpty(memorialRecords)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询陵园纪念人纪念记录列表无数据l");
            return new PageResponse(page, null);
        }
        List<MemorialRecordBo> memorialRecordBos = new ArrayList<MemorialRecordBo>();
        for (MemorialRecord m : memorialRecords) {
            MemorialRecordBo memorialRecordBo = new MemorialRecordBo();
            BeanUtils.copyProperties(m, memorialRecordBo);
            memorialRecordBos.add(memorialRecordBo);
        }
        return new PageResponse(page, memorialRecordBos);
    }


}
