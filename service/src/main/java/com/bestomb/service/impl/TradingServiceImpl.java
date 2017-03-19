package com.bestomb.service.impl;

import com.bestomb.common.Page;
import com.bestomb.common.response.PageResponse;
import com.bestomb.dao.ITradingDao;
import com.bestomb.entity.TradingDetail;
import com.bestomb.service.ITradingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by jason on 2017-03-19.
 */
@Service
public class TradingServiceImpl implements ITradingService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ITradingDao tradingDao;

    /**
     * 分页查询获取会员交易币明细
     *
     * @param pageNo
     * @param pageSize
     * @param memberId
     * @return
     */
    public PageResponse listByTradingDetail(int pageNo, int pageSize, int memberId) {
        //根据会员编号获取交易币明细数据总数
        Long dataCount = tradingDao.countByPagination(memberId);

        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("会员[" + memberId + "]交易币明细数据为空");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        Page page = new Page(pageNo, pageSize);
        List<TradingDetail> tradingDetails = tradingDao.selectByPagination(page, memberId);
        if (CollectionUtils.isEmpty(tradingDetails)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "], memberId [" + memberId + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        return new PageResponse(pageNo, pageSize, dataCount, tradingDetails);
    }
}
