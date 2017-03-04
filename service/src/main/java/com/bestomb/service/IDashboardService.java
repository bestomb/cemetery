package com.bestomb.service;

import java.util.Map;

/**
 * Created by jason on 2017-03-04.
 */
public interface IDashboardService {

    /**
     * 获得工作台各项统计数据
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    Map<String, String> getStatistics(String beginTime, String endTime);
}
