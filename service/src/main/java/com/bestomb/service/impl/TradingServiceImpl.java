package com.bestomb.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.IMemberAccountDao;
import com.bestomb.dao.ITradingDao;
import com.bestomb.entity.MemberAccount;
import com.bestomb.entity.TradingDetail;
import com.bestomb.service.ITradingService;

/***
 * 会员交易币接口实现类
 * @author qfzhang
 *
 */
@Service
public class TradingServiceImpl implements ITradingService {
	
	private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ITradingDao tradingDetailDao;
    @Autowired
    private IMemberAccountDao memberAccountDao;
	
	/***
	 * 充值是否成功
	 * 
	 * @param TradingDetail
	 * @return
	 */
    @Transactional
	public boolean deposit(TradingDetail record) throws EqianyuanException{
		// 判断会员编号是否为空
        if (ObjectUtils.isEmpty(record.getMemberId())) {
            logger.warn("deposit fail , because memberId is null");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        // 判断交易币是否为空
        if (ObjectUtils.isEmpty(record.getTrading())) {
        	logger.warn("deposit fail , because trading is null");
        	throw new EqianyuanException(ExceptionMsgConstant.TRADING_IS_EMPTY);
        }
        record.setType(1); // 1获得，2消费
        record.setCreateTime(CalendarUtil.getSystemSeconds());
        // 将充值信息插入交易币明细表
        int i = tradingDetailDao.insertSelective(record);
        if ( i > 0 ) {
        	MemberAccount memberAccount = new MemberAccount();
        	memberAccount.setTradingAmount(record.getTrading());
        	memberAccount.setMemberId(record.getMemberId());
        	// 将充值信息更新到会员账号信息表
        	i = memberAccountDao.updateTradingAmount(memberAccount);
        	if (i<=0) {
        		logger.error("会员充值，更新会员账号信息表失败");
			}
		}else{
			logger.error("会员充值，插入交易币明细表失败");
		}
        
		return i>0 ;
	}

}
