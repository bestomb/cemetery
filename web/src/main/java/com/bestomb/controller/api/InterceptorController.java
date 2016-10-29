package com.bestomb.controller.api;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.controller.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * WEBSITE拦击成功控制器
 * Created by jason on 2016-07-14.
 */
@Controller
@RequestMapping("/website")
public class InterceptorController extends BaseController {

    private Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping("/authorization")
    public void Authorization() throws Exception {
        logger.info("website operator fail , because member no authorization , please login first");
        throw new EqianyuanException(ExceptionMsgConstant.MEMBER_NO_AUTHORIZATION_BY_LOGIN);
    }

    /**
     * 空操作，用于反馈给u3d获取sessionId
     *
     * @return
     */
    @RequestMapping("/getSessionId")
    @ResponseBody
    public String getSessionId() throws EqianyuanException {
        return SessionUtil.getSession().getId();
    }
}
