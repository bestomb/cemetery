package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.member.MemberLoginBo;

/**
 * 会员业务逻辑接口
 * Created by jason on 2016-07-12.
 */
public interface IMemberService {

    /**
     * 会员注册
     *
     * @param mobile
     * @param verifyCode
     * @param loginPassword
     * @param confirmPassword 确认密码
     * @param inviterId
     */
    void register(String mobile, String verifyCode, String loginPassword, String confirmPassword, String inviterId) throws EqianyuanException;

    /**
     * 会员登录
     *
     * @param loginAccount  登录账号
     * @param loginPassword 登录密码
     */
    MemberLoginBo login(String loginAccount, String loginPassword) throws EqianyuanException;
}
