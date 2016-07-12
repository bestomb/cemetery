package com.bestomb.controller.api;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.util.VerifyCodeUtils;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
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
     * 获取验证码
     *
     * @param verifyCodeLength 验证码内容字符长度
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/verifyCode")
    public void verifyCode(@RequestParam(name = "verify_code_length", required = false, defaultValue = "4") Integer verifyCodeLength,
                           HttpServletResponse response) throws EqianyuanException, IOException {
        /**
         * 控制验证码生成数量，避免构建图片时出现内存不足问题
         */
        if (verifyCodeLength > 10) {
            throw new EqianyuanException(ExceptionMsgConstant.VALIDATA_CODE_CONTENT_LENGTH_TO0_LONG);
        }

        String verifyCode = VerifyCodeUtils.random(verifyCodeLength, VerifyCodeUtils.SEEDS_BY_NUMBER);

        verifyCode(verifyCode, response, 30);
    }

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
     * @param mobile        手机号码
     * @param verifyCode    验证码
     * @param loginPassword 登录密码
     * @param inviterId     邀请者编号
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public ServerResponse register(String mobile,
                                   String verifyCode,
                                   String loginPassword,
                                   String inviterId) throws EqianyuanException {
        memberService.register(mobile, verifyCode, loginPassword, inviterId);
        return new ServerResponse();
    }
}
