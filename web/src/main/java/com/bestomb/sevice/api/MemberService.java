package com.bestomb.sevice.api;

import com.alibaba.fastjson.JSONObject;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.util.*;
import com.bestomb.common.util.yamlMapper.ClientConf;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.IMemberService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员业务层调用
 * Created by jason on 2016-07-11.
 */
@Service
public class MemberService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IMemberService memberService;

    /**
     * 发送短信验证码
     *
     * @param mobile
     */
    public void sendSMSVerificationCode(String mobile, Integer verifyCodeLength) throws EqianyuanException, IOException {
        //手机号码是否为空
        if (StringUtils.isEmpty(mobile)) {
            logger.warn("batchSend fail , because mobile is null");
            throw new EqianyuanException(ExceptionMsgConstant.MOBILE_IS_EMPTY);
        }

        //正则判断是否为正确手机号
        if (!RegexUtils.isMobile(mobile)) {
            logger.warn("batchSend fail , because mobile is not correct ");
            throw new EqianyuanException(ExceptionMsgConstant.MOBILE_IS_NOT_CORRECT);
        }

        //从session中获取最后发送成功短信时间及手机号码
        Map<String, Map<String, String>> sendByVerificationCodes = getSendByVerificationCodes();

        //判断是否已经存在成功发送请求session
        if (!CollectionUtils.isEmpty(sendByVerificationCodes)) {
            for (String mobileKey : sendByVerificationCodes.keySet()) {
                //判断当前手机号码是否已经在session中有记录
                if (!StringUtils.equals(mobileKey, mobile)) {
                    continue;
                }

                //检查session中记录时间是否超过60S
                if (CalendarUtil.getSystemSeconds() - Integer.parseInt(sendByVerificationCodes.get(mobileKey).get("send_time")) < 60) {
                    logger.warn("sendSMSVerificationCode fail , because mobile [" + mobile + "] send interval less than 60s");
                    throw new EqianyuanException(ExceptionMsgConstant.VALIDATA_CODE_INTERVAL_INSUFFICIENT);
                }
            }
        }

        //获取消息内容模板
        String message = YamlForMapHandleUtil.getValueBykey(ClientConf.getMap(), ClientConf.SMS.SMS.toString(), ClientConf.SMS.BatchSend2_message.toString());

        //获取验证码
        String verifyCode = VerifyCodeUtils.random(verifyCodeLength);

        //替换消息模板内容
        message = StringTemplateReplaceUtil.getStr(message, "\\?", verifyCode);

        //将验证码内容写入session
        SessionUtil.setAttribute(SystemConf.VERIFY_CODE.toString(), verifyCode);

        //发送短信
        boolean sendResult = SMSUtils.batchSend2(mobile, message);

        if (!sendResult) {
            //发送失败，抛出系统异常
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_ERROR);
        }

        //当前手机及发送情况
        Map<String, String> sendInfo = new HashMap<String, String>();
        sendInfo.put("mobile", mobile);
        sendInfo.put("send_time", String.valueOf(CalendarUtil.getSystemSeconds()));

        sendByVerificationCodes.put(mobile, sendInfo);

        //发送短信成功，保存发送时间
        SessionUtil.setAttribute(SystemConf.SEND_SMS_BY_BATCHSEND2.toString(), sendByVerificationCodes);

    }

    /**
     * 会员注册
     *
     * @param mobile        手机号码
     * @param verifyCode    验证码
     * @param loginPassword 登录密码
     * @param inviterId     邀请者编号
     * @throws EqianyuanException
     */
    public void register(String mobile, String verifyCode, String loginPassword, String inviterId) throws EqianyuanException {
        memberService.register(mobile, verifyCode, loginPassword, inviterId);
    }

    /**
     * 从session中获取短信发送集合
     *
     * @return
     */
    private Map<String, Map<String, String>> getSendByVerificationCodes() {
        Object sendSMSByBatchSend2 = SessionUtil.getAttribute(SystemConf.SEND_SMS_BY_BATCHSEND2.toString());
        if (ObjectUtils.isEmpty(sendSMSByBatchSend2)) {
            return new HashMap<String, Map<String, String>>();
        }

        return (Map<String, Map<String, String>>) sendSMSByBatchSend2;
    }

}
