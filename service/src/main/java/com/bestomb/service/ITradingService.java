package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.entity.TradingDetail;

/***
 * 交易币接口
 * @author qfzhang
 *
 */
public interface ITradingService {
	
	/***
	 * 充值
	 * @param record
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean deposit(TradingDetail record) throws EqianyuanException;
	
	
}
