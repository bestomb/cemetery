package com.bestomb.service;

import javax.servlet.http.HttpServletRequest;

/**
 * ping++第三方支付，系统业务接口
 * Created by jason on 2016-07-06.
 */
public interface IPingggService {

    /**
     * 获取在线支付凭证
     *
     * @param amount   支付金额，单位（元）
     * @param channel  支付渠道
     * @param memberId 会员编号
     * @param request
     * @return
     * @throws Exception
     */
    String getCharge(int amount, String channel, Integer memberId, HttpServletRequest request) throws Exception;

    /**
     * 接受、验证Webhooks并且更新会员充值支付信息
     *
     * @param request
     * @throws Exception
     */
    void webHooksByChargeSucceeded(HttpServletRequest request) throws Exception;
}
