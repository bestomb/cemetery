package com.bestomb.service.impl;

import com.bestomb.common.Page;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.MemberAccountBo;
import com.bestomb.common.response.member.MemberLoginBo;
import com.bestomb.common.util.*;
import com.bestomb.common.util.yamlMapper.ClientConf;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.IMemberAccountDao;
import com.bestomb.dao.IMemberAccountIdBuildDao;
import com.bestomb.entity.MemberAccount;
import com.bestomb.entity.MemberAccountIdBuild;
import com.bestomb.service.IMemberService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员业务逻辑接口实现类
 * Created by jason on 2016-07-12.
 */
@Service
public class MemberServiceImpl implements IMemberService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IMemberAccountDao memberAccountDao;

    @Autowired
    private IMemberAccountIdBuildDao memberAccountIdBuildDao;

    /**
     * 会员注册
     *
     * @param mobile          手机号码
     * @param verifyCode      验证码
     * @param loginPassword   登录密码
     * @param confirmPassword 确认密码
     * @param inviterId       邀请者编号
     */
    @Transactional(rollbackFor = Exception.class)
    public void register(String mobile, String verifyCode, String loginPassword, String confirmPassword, String inviterId) throws EqianyuanException {
        //手机号码是否为空
        if (StringUtils.isEmpty(mobile)) {
            logger.warn("register fail , because mobile is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MOBILE_IS_EMPTY);
        }

        //密码是否为空
        if (StringUtils.isEmpty(loginPassword)) {
            logger.warn("register fail , because loginPassword is null.");
            throw new EqianyuanException(ExceptionMsgConstant.LOGIN_PASSWORD_IS_EMPTY);
        }

        //确认密码是否为空
        if (StringUtils.isEmpty(confirmPassword)) {
            logger.warn("register fail , because confirmPassword is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CONFIRM_PASSWORD_IS_EMPTY);
        }

        //验证码是否为空
        if (StringUtils.isEmpty(verifyCode)) {
            logger.warn("register fail , because verifyCode is null");
            throw new EqianyuanException(ExceptionMsgConstant.VALIDATA_CODE_IS_EMPTY);
        }

        //正则判断是否为正确手机号
        if (!RegexUtils.isMobile(mobile)) {
            logger.warn("register fail , because mobile is not correct ");
            throw new EqianyuanException(ExceptionMsgConstant.MOBILE_IS_NOT_CORRECT);
        }

        //判断两次密码是否一致
        if (!StringUtils.equals(loginPassword, confirmPassword)) {
            logger.warn("register fail , because the two  passwords don't match.");
            throw new EqianyuanException(ExceptionMsgConstant.TWO_PASSWORD_DO_NOT_MATCH);
        }

        //获取客户端session
        HttpSession session = SessionUtil.getClientSession();

        //从session中获取验证码数据
        String codeByValidation = (String) SessionUtil.getAttribute(session, SystemConf.VERIFY_CODE.toString());
        if (StringUtils.isEmpty(codeByValidation)) {
            logger.warn("register fail, because there is no verification code in the session attribute.");
            throw new EqianyuanException(ExceptionMsgConstant.VALIDATA_CODE_VALIDATION_ERROR);
        }

        //比较用户提交验证码与session中验证码是否一致
        if (!StringUtils.equalsIgnoreCase(verifyCode, codeByValidation)) {
            logger.warn("register fail, because session attribute verification code and request param code not consistent.");
            throw new EqianyuanException(ExceptionMsgConstant.VALIDATA_CODE_VALIDATION_ERROR);
        }

        //判断客户端配置默认可建设陵园总数是否存在
        Object constructionCount = YamlForMapHandleUtil.getMapByKey(ClientConf.getMap(), ClientConf.Cemetery.constructionCount.toString());
        if (ObjectUtils.isEmpty(constructionCount)) {
            logger.warn("batchSend fail , because constructionCount not exists the client-conf.yaml");
            throw new EqianyuanException(ExceptionMsgConstant.GET_CONFIGURATION_ERROR);
        }

        //检查手机号是否已经注册过
        int memberCountByMobile = memberAccountDao.selectByMobile(Long.parseLong(mobile));

        //当会员数量大于0，说明已经被注册，则抛出对应错误提示
        if (memberCountByMobile > 0) {
            logger.warn("register fail, because mobile is already register.");
            throw new EqianyuanException(ExceptionMsgConstant.MOBILE_IS_ALREADY_REGISTER);
        }

        MemberAccount memberAccount = new MemberAccount();
        if (!StringUtils.isEmpty(inviterId)) {
            //根据会员编号获取会员信息，检查邀请者是否存在
            int memberCountByInviterId = memberAccountDao.selectByInviterId(Integer.parseInt(inviterId));
            if (memberCountByInviterId > 0) {
                memberAccount.setInviterId(Integer.parseInt(inviterId));
            }
        }

        do {
            //构建一个编号并设置状态为：1=未使用
            MemberAccountIdBuild memberAccountIdBuild = new MemberAccountIdBuild();
            memberAccountIdBuild.setStatus(1);

            //获取会员编号
            memberAccountIdBuildDao.insertSelective(memberAccountIdBuild);
            Integer memberId = memberAccountIdBuild.getId();

            //检查编号是否为靓号，如果是靓号，则重新获取编号
            if (VIPIDFilterUtil.doFilter(String.valueOf(memberId))) {
                continue;
            }

            memberAccount.setMemberId(memberId);

            //更新会员账号为：2=已使用
            memberAccountIdBuild.setStatus(2);
            memberAccountIdBuildDao.updateByPrimaryKeySelective(memberAccountIdBuild);
            break;
        } while (true);

        //密码加密处理
        String encryptionPwd = Md5Util.MD5By32(StringUtils.lowerCase(loginPassword));

        memberAccount.setMobileNumber(Long.parseLong(mobile));
        memberAccount.setLoginPassword(encryptionPwd);
        memberAccount.setConstructionCount(Integer.parseInt(String.valueOf(constructionCount)));
        memberAccount.setCreateTime(CalendarUtil.getSystemSeconds());

        memberAccountDao.insertSelective(memberAccount);
        SessionUtil.removeAttribute(SystemConf.VERIFY_CODE.toString());
    }

    /**
     * 会员登录
     *
     * @param loginAccount  登录账号
     * @param loginPassword 登录密码
     */
    public MemberLoginBo login(String loginAccount, String loginPassword) throws EqianyuanException {
        //判断登录账号是否为空
        if (StringUtils.isEmpty(loginAccount)) {
            logger.warn("login fail , because loginAccount is null");
            throw new EqianyuanException(ExceptionMsgConstant.LOGIN_ACCOUNT_IS_EMPTY);
        }

        //判断登录密码是否为空
        if (StringUtils.isEmpty(loginPassword)) {
            logger.warn("login fail , because loginPassword is null");
            throw new EqianyuanException(ExceptionMsgConstant.LOGIN_PASSWORD_IS_EMPTY);
        }

        //密码加密处理
        String encryptionPwd = Md5Util.MD5By32(StringUtils.lowerCase(loginPassword));
        //登录查询
        MemberAccount memberAccount = memberAccountDao.selectByLogin(loginAccount, encryptionPwd);
        if (ObjectUtils.isEmpty(memberAccount) ||
                ObjectUtils.isEmpty(memberAccount.getMemberId())) {
            logger.warn("login fail , loginAccount [" + loginAccount +
                    "] , loginPassword [" + loginPassword + "] , encryptionPwd [" + encryptionPwd + "]");
            throw new EqianyuanException(ExceptionMsgConstant.LOGIN_ACCOUNT_OR_PASSWORD_ERROR);
        }

        MemberLoginBo memberLoginBo = new MemberLoginBo();
        BeanUtils.copyProperties(memberAccount, memberLoginBo);
        memberLoginBo.setCreateTimeForStr(CalendarUtil.secondsTimeToDateTimeString(memberAccount.getCreateTime()));
        return memberLoginBo;
    }

    /**
     * 根据主键ID查询信息
     *
     * @param memberId
     * @return
     * @throws EqianyuanException
     */
    public MemberAccountBo getInfo(String memberId) throws EqianyuanException {
        //主键是否为空
        if (ObjectUtils.isEmpty(memberId)) {
            logger.info("getInfo fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        MemberAccount memberAccount = memberAccountDao.selectByPrimaryKey(Integer.parseInt(memberId));
        MemberAccountBo memberAccountBo = new MemberAccountBo();
        BeanUtils.copyProperties(memberAccount, memberAccountBo);

        memberAccountBo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(memberAccount.getCreateTime()));
        return memberAccountBo;
    }

    /**
     * 分页查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(int pageNo, int pageSize) throws EqianyuanException {

        Long dataCount = memberAccountDao.countByPagination();
        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        Page page = new Page(pageNo, pageSize);
        List<MemberAccount> memberAccountList = memberAccountDao.selectByPagination(page);
        if (CollectionUtils.isEmpty(memberAccountList)) {
            logger.warn("pageNo [" + pageNo + "], pageSize [" + pageSize + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }
        List<MemberAccountBo> memberAccountBoList = new ArrayList<MemberAccountBo>();
        for (MemberAccount memberAccount : memberAccountList) {
            MemberAccountBo memberAccountBo = new MemberAccountBo();
            BeanUtils.copyProperties(memberAccount, memberAccountBo);

            memberAccountBo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(memberAccount.getCreateTime()));
            memberAccountBoList.add(memberAccountBo);
        }
        return new PageResponse(pageNo, pageSize, dataCount, memberAccountBoList);
    }
}
