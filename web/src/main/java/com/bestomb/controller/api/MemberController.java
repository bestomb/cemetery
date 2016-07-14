package com.bestomb.controller.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 会员接口控制器
 * Created by jason on 2016-07-11.
 */
@Controller
@RequestMapping("/member_api/")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    /**
     * 发送短信验证码
     *
     * @param mobile 手机号码
     * @return
     */
    @RequestMapping("/sendSMSVerificationCode")
    @ResponseBody
    public ServerResponse sendSMSVerificationCode(String mobile,
                                                  @RequestParam(name = "verify_code_length", required = false, defaultValue = "4") Integer verifyCodeLength)
            throws EqianyuanException, IOException {
        memberService.sendSMSVerificationCode(mobile, verifyCodeLength);
        return new ServerResponse();
    }

    /**
     * 会员注册
     *
     * @param mobile          手机号码
     * @param verifyCode      验证码
     * @param loginPassword   登录密码
     * @param confirmPassword 确认密码
     * @param inviterId       邀请者编号
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public ServerResponse register(String mobile,
                                   String verifyCode,
                                   String loginPassword,
                                   String confirmPassword,
                                   String inviterId) throws EqianyuanException {
        memberService.register(mobile, verifyCode, loginPassword, confirmPassword, inviterId);
        return new ServerResponse();
    }

    /**
     * 带验证码登录
     *
     * @param loginAccount   登录账号
     * @param loginPassworde 登录密码
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/login")
    @ResponseBody
    public ServerResponse login(@RequestParam(value = "login_account") String loginAccount,
                                @RequestParam(value = "login_password") String loginPassworde) throws EqianyuanException {
        MemberLoginVo memberLoginVo = memberService.login(loginAccount, loginPassworde);
        return new ServerResponse.ResponseBuilder().data(memberLoginVo).build();
    }

    /**
     * 退出登录
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ServerResponse logout() throws EqianyuanException {
        /**
         * 将会员VO对象从session中移除
         */
        SessionUtil.removeAttribute(SystemConf.WEBSITE_SESSION_MEMBER.toString());
        return new ServerResponse();
    }

    /**
     * 获取会员信息
     *
     * @return
     */
    public ServerResponse getMemberInfo() {
        return new ServerResponse();
    }

    /**
     * 编辑会员资料信息
     *
     * @return
     */
    public ServerResponse editMemberInfo() {
        return new ServerResponse();
    }
}
