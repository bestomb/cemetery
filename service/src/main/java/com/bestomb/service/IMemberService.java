package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.MemberAccountBo;
import com.bestomb.common.response.member.MemberLoginBo;
import com.bestomb.entity.MemberAccount;

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
     * @param nickName        昵称
     */
    MemberAccount register(String mobile, String verifyCode, String loginPassword, String confirmPassword, String inviterId, String nickName) throws EqianyuanException;

    /**
     * 密码找回
     * @param mobile 手机号码
     * @param verifyCode    短信验证码
     * @param loginPassword 密码
     * @param confirmPassword   确认密码
     * @return
     * @throws EqianyuanException
     */
    boolean findPwd(String mobile, String verifyCode, String loginPassword, String confirmPassword) throws EqianyuanException;

    /**
     * 更换手机号码
     * @param mobile
     * @param verifyCode
     * @return
     * @throws EqianyuanException
     */
    boolean updateMobile(int memberId, String mobile, String verifyCode) throws EqianyuanException;

    /**
     * 会员登录
     *
     * @param loginAccount  登录账号
     * @param loginPassword 登录密码
     */
    MemberLoginBo login(String loginAccount, String loginPassword) throws EqianyuanException;

    /**
     * 根据id查询单条信息
     *
     * @param memberId 主键ID
     */
    MemberAccountBo getInfo(String memberId) throws EqianyuanException;

    /**
     * 分页查询
     */
    PageResponse getList(int pageNo, int pageSize, String type) throws EqianyuanException;

    /***
     * 修改会员资料
     *
     * @param memberAccount
     * @return
     * @throws EqianyuanException
     */
    int edit(MemberAccount memberAccount) throws EqianyuanException;

}
