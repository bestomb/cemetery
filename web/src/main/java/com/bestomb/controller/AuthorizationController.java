package com.bestomb.controller;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.VerifyCodeUtils;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.sevice.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户授权
 * 主要功能：
 * 获取验证码
 * 用户登录
 * 用户登出
 * <p>
 * Created by jason on 2016-05-17.
 */
@Controller
@RequestMapping("/system-manage")
public class AuthorizationController extends BaseController {

    @Autowired
    private AuthorizationService authorizationService;

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
     * 带验证码登录
     *
     * @param userName   用户名
     * @param password   密码
     * @param verifyCode 验证码
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/login")
    @ResponseBody
    public ServerResponse login(@RequestParam(value = "user_name") String userName,
                                @RequestParam String password,
                                @RequestParam(value = "verify_code") String verifyCode) throws EqianyuanException {
        authorizationService.login(userName, password, verifyCode);
        return new ServerResponse();
    }

    /**
     * 退出登录
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/logout")
    public String logout() throws EqianyuanException {
        /**
         * 将用户VO对象从session中移除
         */
        SessionUtil.removeAttribute(SystemConf.SYSTEM_SESSION_USER.toString());
        return SystemConf.SYSTEM_MANAGE_LOGIN_BY_PAGE.toString();
    }

}
