package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.*;
import com.bestomb.common.util.*;
import com.bestomb.common.util.yamlMapper.ClientConf;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.entity.Backpack;
import com.bestomb.entity.MemberAccount;
import com.bestomb.entity.PurchaseOrder;
import com.bestomb.service.IBackpackService;
import com.bestomb.service.ILeaveMessage;
import com.bestomb.service.IMemberService;
import com.bestomb.service.IOrderService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;
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
    private IMemberService memberService; // 会员接口
    @Autowired
    private IOrderService orderSerivce; // 订单接口
    @Autowired
    private ILeaveMessage leaveMessage; // 祭祀留言接口
    @Autowired
    private IBackpackService backpackService; // 背包接口

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

        //获取客户端session
        HttpSession session = SessionUtil.getClientSession();

        //从session中获取最后发送成功短信时间及手机号码
        Map<String, Map<String, String>> sendByVerificationCodes = getSendByVerificationCodes(session);

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

        if (ObjectUtils.isEmpty(message)) {
            logger.warn("batchSend fail , because BatchSend2_message not exists the client-conf.yaml");
            throw new EqianyuanException(ExceptionMsgConstant.GET_CONFIGURATION_ERROR);
        }

        //获取验证码
        String verifyCode = VerifyCodeUtils.random(verifyCodeLength, VerifyCodeUtils.SEEDS_BY_NUMBER);

        //替换消息模板内容
        message = StringTemplateReplaceUtil.getStr(message, "\\?", verifyCode);

        //将验证码内容写入session
        SessionUtil.setAttribute(session, SystemConf.VERIFY_CODE.toString(), verifyCode);

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
        SessionUtil.setAttribute(session, SystemConf.SEND_SMS_BY_BATCHSEND2.toString(), sendByVerificationCodes);

    }

    /**
     * 会员注册
     *
     * @param mobile          手机号码
     * @param verifyCode      验证码
     * @param loginPassword   登录密码
     * @param confirmPassword 确认密码
     * @param inviterId       邀请者编号
     * @throws EqianyuanException
     */
    public void register(String mobile, String verifyCode, String loginPassword, String confirmPassword, String inviterId) throws EqianyuanException {
        memberService.register(mobile, verifyCode, loginPassword, confirmPassword, inviterId);
    }

    /**
     * 会员登录
     *
     * @param loginAccount  登录账号
     * @param loginPassword 登录密码
     */
    public MemberLoginVo login(String loginAccount, String loginPassword) throws EqianyuanException {
        MemberLoginBo memberLoginBo = memberService.login(loginAccount, loginPassword);
        MemberLoginVo memberLoginVo = new MemberLoginVo();
        BeanUtils.copyProperties(memberLoginBo, memberLoginVo);

        //获取客户端session
        HttpSession session;
        try {
            session = SessionUtil.getClientSession();
        } catch (EqianyuanException e) {
            session = SessionUtil.getSession();
            SessionContextUtil.getInstance().addSession(session);
        }
        SessionUtil.setAttribute(session, SystemConf.WEBSITE_SESSION_MEMBER.toString(), memberLoginVo);
        return memberLoginVo;
    }

    /**
     * 从session中获取短信发送集合
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Map<String, String>> getSendByVerificationCodes(HttpSession session) throws EqianyuanException {
        Object sendSMSByBatchSend2 = SessionUtil.getAttribute(session, SystemConf.SEND_SMS_BY_BATCHSEND2.toString());
        if (ObjectUtils.isEmpty(sendSMSByBatchSend2)) {
            return new HashMap<String, Map<String, String>>();
        }

        return (Map<String, Map<String, String>>) sendSMSByBatchSend2;
    }


    /**
     * 根据主键ID查询信息
     *
     * @param memberId
     * @return
     * @throws EqianyuanException
     */
    public MemberAccountVo getInfo(int memberId) throws EqianyuanException {
        MemberAccountVo memberAccountVo = new MemberAccountVo();
        MemberAccountBo memberLoginBo = memberService.getInfo(memberId + "");
        BeanUtils.copyProperties(memberLoginBo, memberAccountVo);
        return memberAccountVo;
    }

    /**
     * 根据主键ID查询钱包信息（交易币和积分）
     *
     * @param memberId
     * @return
     * @throws EqianyuanException
     */
    public WalletVo getWalletInfo(int memberId) throws EqianyuanException {
        WalletVo walletVo = new WalletVo();
        MemberAccountBo memberLoginBo = memberService.getInfo(memberId + "");
        BeanUtils.copyProperties(memberLoginBo, walletVo);
        return walletVo;
    }

    /***
     * 修改会员资料
     *
     * @param memberAccount
     * @return
     * @throws EqianyuanException
     */
    public boolean edit(MemberAccount memberAccount) throws EqianyuanException {
        // 会员编号是否为空
        if (ObjectUtils.isEmpty(memberAccount.getMemberId())) {
            logger.warn("edit fail , because memberId is null");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        return memberService.edit(memberAccount) > 0;
    }

    /***
     * 查询订单分页列表
     *
     * @param order
     * @param page
     * @return
     */
    public PageResponse getOrderPageList(PurchaseOrder order, Pager page) throws EqianyuanException {
        // 会员编号是否为空
        if (ObjectUtils.isEmpty(order.getMemberId())) {
            logger.warn("getOrderPageList fail , because memberId is null");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        return orderSerivce.getPageList(order, page);
    }

    /***
     * 商品购买
     *
     * @param goodsInfo
     * @param memberId
     * @return
     * @throws EqianyuanException
     */
    public void goodsBuy(String goodsInfo, Integer memberId) throws Exception {
        orderSerivce.goodsBuy(goodsInfo, memberId);
    }

    /***
     * 查看我（收到的）的留言分页列表
     *
     * @param memberId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getReceivedMessage(Integer memberId, Pager page) throws EqianyuanException {
        // 会员编号是否为空
        if (ObjectUtils.isEmpty(memberId)) {
            logger.warn("getReceivedMessage fail , because memberId is null");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        return leaveMessage.getReceivedMessage(memberId, page);
    }

    /***
     * 查看我（发出）的留言分页列表
     *
     * @param memberId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getPushedMessage(Integer memberId, Pager page) throws EqianyuanException {
        // 会员编号是否为空
        if (ObjectUtils.isEmpty(memberId)) {
            logger.warn("getPushedMessage fail , because memberId is null");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        return leaveMessage.getPushedMessage(memberId, page);
    }

    /***
     * 删除（我发出的）留言
     *
     * @param messageId
     * @return
     * @throws EqianyuanException
     */
    public boolean deleteMessage(String messageId) throws EqianyuanException {
        // 留言ID是否为空
        if (StringUtils.isEmpty(messageId)) {
            logger.warn("deleteMessage fail , because messageId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MESSAGEID_IS_EMPTY);
        }
        return leaveMessage.deleteByPrimaryKey(messageId);
    }

    /***
     * 根据查询条件查询会员背包商品分页列表
     *
     * @param backpack
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getBackpackGoodsPageList(Backpack backpack, Pager page) throws EqianyuanException {
        return backpackService.getGoodsPageList(backpack, page);
    }

    /***
     * 获取背包商品详情
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    public Object getBackpackGoodsDetail(Backpack backpack) throws EqianyuanException {
        return backpackService.getGoodsDetail(backpack);
    }

}
