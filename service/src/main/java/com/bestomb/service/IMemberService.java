package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;

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
     * @param inviterId
     */
    void register(String mobile, String verifyCode, String loginPassword, String inviterId) throws EqianyuanException;
}
