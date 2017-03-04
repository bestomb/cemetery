package com.bestomb.service.impl;

import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.ICemeteryDao;
import com.bestomb.dao.IMasterDao;
import com.bestomb.dao.IMemorialRecordDao;
import com.bestomb.dao.ITombstoneDao;
import com.bestomb.service.IDashboardService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jason on 2017-03-04.
 */
@Service
public class DashboardServiceImpl implements IDashboardService {

    @Resource
    private ICemeteryDao cemeteryDao;

    @Resource
    private ITombstoneDao tombstoneDao;

    @Resource
    private IMasterDao masterDao;

    @Resource
    private IMemorialRecordDao memorialRecordDao;

    /**
     * 获取首页工作台各项统计数据
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public Map<String, String> getStatistics(String beginTime, String endTime) {
        Integer bTime = null;
        Integer eTime = null;
        if(!StringUtils.isEmpty(beginTime)){
            //将字符串时间转为时间戳
            bTime = CalendarUtil.getSecondsByDate(beginTime);
        }
        if(!StringUtils.isEmpty(endTime)){
            eTime = CalendarUtil.getSecondsByDate(endTime);
        }
        //查询时间范围内，所建设了多少座陵园
        long countByCemetery = cemeteryDao.countByTime(bTime, eTime);
        //查询时间范围内，创建了多少墓碑
        long countByTombstone = tombstoneDao.countByTime(bTime, eTime);
        //查询时间范围内，创建了多少纪念人
        long countByMaster = masterDao.countByTime(bTime, eTime);
        //查询时间范围内，网陵祭拜了多少次
        long countByAction = memorialRecordDao.countByTime(bTime, eTime);

        Map<String, String> result = new HashMap<String, String>();
        result.put("cemetery", countByCemetery+"");
        result.put("tombstone", countByTombstone+"");
        result.put("master", countByMaster+"");
        result.put("action", countByAction+"");
        return result;
    }
}
