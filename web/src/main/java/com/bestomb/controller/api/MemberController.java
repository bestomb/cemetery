package com.bestomb.controller.api;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.member.MemberAccountVo;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.response.member.WalletVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.controller.BaseController;
import com.bestomb.entity.Backpack;
import com.bestomb.entity.MemberAccount;
import com.bestomb.entity.PurchaseOrder;
import com.bestomb.sevice.api.MemberService;

/**
 * 会员接口控制器
 * Created by jason on 2016-07-11.
 */
@Controller
@RequestMapping("/website/member_api")
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
        //获取客户端session
        HttpSession session = SessionUtil.getClientSession();

        /**
         * 将会员VO对象从session中移除
         */
        SessionUtil.removeAttribute(session, SystemConf.WEBSITE_SESSION_MEMBER.toString());
        SessionContextUtil.getInstance().delSession(session);
        return new ServerResponse();
    }

    /**
     * 获取会员信息
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public ServerResponse getMemberInfo() throws EqianyuanException {
        // 从session池中获取系统用户信息
        int memberId = getLoginMember().getMemberId();
        MemberAccountVo memberAccountVo = memberService.getInfo(memberId);
        return new ServerResponse.ResponseBuilder().data(memberAccountVo).build();
    }

    /***
     * 编辑会员资料信息
     *
     * @param memberAccount
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST} )
    @ResponseBody
    public ServerResponse editMemberInfo(@RequestBody MemberAccount memberAccount) throws EqianyuanException {
        // 从session池中获取系统用户信息
        memberAccount.setMemberId(getLoginMember().getMemberId());
        boolean flag = memberService.edit(memberAccount);
        if (flag) { // 编辑成功后，则重置session中会员信息
            //TODO 确认是否需要重置session中会员信息
            MemberAccountVo memberAccountVo = memberService.getInfo(memberAccount.getMemberId());
            MemberLoginVo memberLoginVo = new MemberLoginVo();
            BeanUtils.copyProperties(memberAccountVo, memberLoginVo);
            SessionUtil.setAttribute(SessionUtil.getClientSession(), SystemConf.WEBSITE_SESSION_MEMBER.toString(), memberLoginVo);
        }
        return new ServerResponse.ResponseBuilder().data(flag).build();
    }

    /***
     * 获取钱包信息
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/walletInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ServerResponse getWalletInfo() throws EqianyuanException {
        WalletVo walletVo = memberService.getWalletInfo(getLoginMember().getMemberId());
        return new ServerResponse.ResponseBuilder().data(walletVo).build();
    }

    /***
     * 获取会员订单列表
     *
     * @param PurchaseOrder
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/order", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ServerResponse getOrderPageList(@ModelAttribute PurchaseOrder order, @ModelAttribute Pager page) throws EqianyuanException {
        order.setMemberId(getLoginMember().getMemberId());
        PageResponse pageResponse = memberService.getOrderPageList(order, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 商品购买
     *
     * @param goodsInfo 购物清单JSON字符串
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/goodsBuy")
    @ResponseBody
    public ServerResponse goodsBuy(String goodsInfo) throws EqianyuanException {
        memberService.goodsBuy(goodsInfo);
//        return new ServerResponse.ResponseBuilder().data(flag).build();
        return new ServerResponse();
    }

    /***
     * 查看我（收到的）的留言分页列表
     *
     * @param message
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/receivedMessage", method = {RequestMethod.GET, RequestMethod.POST} )
    @ResponseBody
    public ServerResponse getReceivedMessage(@ModelAttribute Pager page) throws EqianyuanException {
        int memberId = getLoginMember().getMemberId();
        PageResponse pageResponse = memberService.getReceivedMessage(memberId, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 查看我（发出的）的留言分页列表
     *
     * @param message
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/pushedMessage", method = {RequestMethod.GET, RequestMethod.POST} )
    @ResponseBody
    public ServerResponse getPushedMessage(@ModelAttribute Pager page) throws EqianyuanException {
        int memberId = getLoginMember().getMemberId();
        PageResponse pageResponse = memberService.getPushedMessage(memberId, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 删除（我发出的）留言
     *
     * @param messageId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/pushedMessage/{messageId}", method = {RequestMethod.DELETE, RequestMethod.POST} )
    @ResponseBody
    public ServerResponse deleteMessage(@PathVariable String messageId) throws EqianyuanException {
        boolean flag = memberService.deleteMessage(messageId);
        return new ServerResponse.ResponseBuilder().data(flag).build();
    }

    /***
     * 查询会员背包商品分页列表
     *
     * @param goods
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/backpackGoods", method = {RequestMethod.GET, RequestMethod.POST} )
    @ResponseBody
    public ServerResponse getBackpackGoodsPageList(@ModelAttribute Backpack backpack, @ModelAttribute Pager page) throws EqianyuanException {
        int memberId = getLoginMember().getMemberId();
        backpack.setMemberId(memberId);
        PageResponse pageResponse = memberService.getBackpackGoodsPageList(backpack, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 获取背包商品详情
     *
     * @param orderId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/backpackGoods/{id}", method = {RequestMethod.GET, RequestMethod.POST} )
    @ResponseBody
    public ServerResponse getBackpackGoodsDetail(@PathVariable String id, @ModelAttribute Backpack backpack) throws EqianyuanException {
    	backpack.setGoodsId(id);
    	Object goods = memberService.getBackpackGoodsDetail(backpack);
        return new ServerResponse.ResponseBuilder().data(goods).build();
    }

}
