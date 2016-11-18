package com.bestomb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.common.util.IPUtils;
import com.bestomb.common.util.PingppUtil;
import com.bestomb.dao.IChargesVerifyDao;
import com.bestomb.dao.IMemberAccountDao;
import com.bestomb.dao.ITradingDao;
import com.bestomb.entity.ChargesVerify;
import com.bestomb.entity.TradingDetail;
import com.bestomb.service.IPingggService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * ping++第三方支付，系统接口业务时间
 * Created by jason on 2016-10-31.
 */
@Service
public class PingppServiceImpl implements IPingggService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IChargesVerifyDao chargesVerifyDao;

    @Autowired
    private IMemberAccountDao memberAccountDao;

    @Autowired
    private ITradingDao tradingDao;

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
    public String getCharge(double amount, String channel, Integer memberId, HttpServletRequest request) throws Exception {
        if (StringUtils.isEmpty(channel)) {
            throw new NullPointerException("未知的支付渠道");
        }
        //生成商户订单编号（纯数字）
        Long orderNo = System.currentTimeMillis() + Math.round(Math.random() * 10000000);

        //构建交易凭证持久化数据
        ChargesVerify chargesVerify = new ChargesVerify();
        chargesVerify.setOrderNo(orderNo);
        chargesVerify.setMemberId(memberId);
        chargesVerify.setCreateTime(CalendarUtil.getSystemSeconds());

        //持久化交易凭证信息
        int executeRow = chargesVerifyDao.insertSelective(chargesVerify);
        if (executeRow > 0) {
            //获取客户端IP
            String clientIp = IPUtils.getIpAddr(request);

            //amount 单位是元，转成交易凭证金额（单位：分），需要*100
            return PingppUtil.getCharge((int) (amount * 100), channel, clientIp, String.valueOf(orderNo), "充值", "在线充值网陵交易币");
        }

        return StringUtils.EMPTY;
    }

    /**
     * 接受、验证Webhooks并且更新会员充值支付信息
     *
     * @param request
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void webHooksByChargeSucceeded(HttpServletRequest request) throws Exception {
        //验证webhooks，如果通过则返回postData
        String webHooksPostData = PingppUtil.webHooksByChargeSucceeded(request);

        if (StringUtils.isEmpty(webHooksPostData)) {
            return;
        }
        //将回调数据转为JSON
        JSONObject webHooksJSON = JSON.parseObject(webHooksPostData);

        //获取json中livemode（是否处于  live 模式）数据，如果为false，则支付环境为测试环境，则业务跳出
        Boolean livemode = (Boolean) webHooksJSON.get("livemode");
        if (!livemode) {
            return;
        }

        //获取ping++回调数据中paid(是否已经付款）
        Boolean paid = (Boolean) webHooksJSON.getJSONObject("data").getJSONObject("object").get("paid");
        //如果未付款，则业务跳出
        if (!paid) {
            return;
        }

        //获取商户订单编号（order_no)
        Long orderNo = Long.parseLong(String.valueOf(webHooksJSON.getJSONObject("data").getJSONObject("object").get("order_no")));
        //获得支付金额（单位：分）
        String amount = String.valueOf(webHooksJSON.getJSONObject("data").getJSONObject("object").get("amount"));

        logger.debug("订单编号：" + orderNo + " 金额：" + amount);
        //将支付金额转为double型
        double tradingAmount;
        if (amount.length() > 2) {
            //元
            String amountYuan = amount.substring(0, amount.length() - 2);
            //分
            String amountPoints = amount.substring(amount.length() - 2);
            tradingAmount = Double.parseDouble(amountYuan + "." + amountPoints);
        } else {
            tradingAmount = Double.parseDouble("0." + amount);
        }

        //根据订单编号查询会员编号
        Integer memberId = chargesVerifyDao.selectMemberByOrderNo(orderNo);
        if (ObjectUtils.isEmpty(memberId)) {
            return;
        }

        //持久化会员充值金额
        memberAccountDao.updateTradingAmount(tradingAmount, memberId);

        //构建交易币明细数据
        TradingDetail tradingDetail = new TradingDetail();
        tradingDetail.setType(1);//1:获得，2：消费
        tradingDetail.setTrading(tradingAmount);
        tradingDetail.setCreateTime(CalendarUtil.getSystemSeconds());
        tradingDetail.setMemberId(memberId);

        //持久化交易币交易明细
        tradingDao.insertSelective(tradingDetail);

        //根据订单和会员信息删除支付验证数据表数据
        chargesVerifyDao.deleteByOrderNo(orderNo);

    }
}
