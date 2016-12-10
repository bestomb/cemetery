package com.bestomb.controller.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.IPingggService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * ping++支付
 * Created by jason on 2016-10-28.
 */
@Controller
@RequestMapping("/pingpp_api")
public class PingppController {

    @Autowired
    private IPingggService pingggService;

    /**
     * 获取支付凭证
     *
     * @param amount  金额（元）
     * @param channel 支付渠道
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getCharge")
    @ResponseBody
    public String getCharge(double amount, String channel, String jsessionId, HttpServletRequest request) throws Exception {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(jsessionId), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        return pingggService.getCharge(amount, channel, memberLoginVo.getMemberId(), request);
    }

    /**
     * 接受并验证Webhooks
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/chargeSucceeded")
    @ResponseBody
    public int webHooksByChargeSucceeded(HttpServletRequest request) throws Exception {
        pingggService.webHooksByChargeSucceeded(request);
        return 200;
    }
}
