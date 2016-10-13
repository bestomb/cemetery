package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.goods.GoodsBo;
import com.bestomb.common.response.goods.GoodsBoWithCount;
import com.bestomb.common.response.member.MemberAccountVo;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.response.member.WalletVo;
import com.bestomb.common.response.orderGoods.OrderGoodsBo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.controller.BaseController;
import com.bestomb.entity.*;
import com.bestomb.sevice.api.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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
    @RequestMapping(value = "/info", method = RequestMethod.GET)
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
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
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
    @RequestMapping(value = "/walletInfo", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getWalletInfo() throws EqianyuanException {
        WalletVo walletVo = memberService.getWalletInfo(getLoginMember().getMemberId());
        return new ServerResponse.ResponseBuilder().data(walletVo).build();
    }

    /***
     * 会员充值
     *
     * @param tradingDetail
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deposit(@RequestBody TradingDetail tradingDetail) throws EqianyuanException {
        // TODO 调用充值接口
        tradingDetail.setMemberId(getLoginMember().getMemberId());
        boolean flag = memberService.deposit(tradingDetail);
        return new ServerResponse.ResponseBuilder().data(flag).build();
    }

    /***
     * 获取会员订单列表
     *
     * @param PurchaseOrder
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getOrderPageList(@ModelAttribute PurchaseOrder order, @ModelAttribute Pager page) throws EqianyuanException {
        order.setMemberId(getLoginMember().getMemberId());
        order.setType(1); // 1表示会员购物订单
        PageResponse pageResponse = memberService.getOrderPageList(order, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 获取订单详情
     *
     * @param orderId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getOrderGoodsByOrderId(@PathVariable String orderId) throws EqianyuanException {
        List<OrderGoodsWithBLOBs> list = memberService.getOrderGoodsByOrderId(orderId);
        return new ServerResponse.ResponseBuilder().data(list).build();
    }

    /***
     * 订单支付
     *
     * @param orderId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/order/{orderId}/pay", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse orderPay(@PathVariable String orderId) throws EqianyuanException {
        // TODO  调用支付接口
        boolean flag = memberService.orderPay(orderId);
        return new ServerResponse.ResponseBuilder().data(flag).build();
    }

    /***
     * 查看我（收到的）的留言分页列表
     *
     * @param message
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/receivedMessage", method = RequestMethod.GET)
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
    @RequestMapping(value = "/pushedMessage", method = RequestMethod.GET)
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
    @RequestMapping(value = "/pushedMessage/{messageId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse deleteMessage(@PathVariable String messageId) throws EqianyuanException {
        boolean flag = memberService.deleteMessage(messageId);
        return new ServerResponse.ResponseBuilder().data(flag).build();
    }

    /***
     * 查看我的店铺已发布商品分页列表
     *
     * @param message
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/storeGoods", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getStoreGoods(@ModelAttribute Goods goods, @ModelAttribute Pager page) throws EqianyuanException {
        int memberId = getLoginMember().getMemberId();
        PageResponse pageResponse = memberService.getStoreGoods(memberId, goods, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 获取店铺商品详情
     *
     * @param orderId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/storeGoods/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getStoreGoodsDetail(@PathVariable String id) throws EqianyuanException {
        GoodsBo goods = memberService.getStoreGoodsDetail(id);
        return new ServerResponse.ResponseBuilder().data(goods).build();
    }

    /***
     * 发布商品
     *
     * @param goodsId 背包中商品ID
     * @param store
     * @param goods
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/store/sell/{goodsId}", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse sellGoods(@RequestBody StoreWithGoods storeWithGoods, @PathVariable String goodsId) throws EqianyuanException {
        int memberId = getLoginMember().getMemberId();
        storeWithGoods.setMemberId(memberId);
        storeWithGoods.setBackpackGoodsId(goodsId);
        boolean flag = memberService.sellGoods(storeWithGoods);
        return new ServerResponse.ResponseBuilder().data(flag).build();
    }

    /***
     * 下架商品
     *
     * @param id 店铺表主键ID
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/store/takeback/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ServerResponse takebackGoods(@PathVariable Integer id) throws EqianyuanException {
        boolean flag = memberService.takebackGoods(id);
        return new ServerResponse.ResponseBuilder().data(flag).build();
    }

    /***
     * 查询会员店铺销售订单
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/storeOrders", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getStoreOrders(@ModelAttribute Pager page) throws EqianyuanException {
        int memberId = getLoginMember().getMemberId();
        PageResponse pageResponse = memberService.getStoreOrders(memberId, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 查询会员店铺销售订单详情
     *
     * @param orderId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/storeOrders/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getStoreOrdersDetail(@PathVariable String orderId) throws EqianyuanException {
        int memberId = getLoginMember().getMemberId();
        List<OrderGoodsBo> list = memberService.getStoreOrdersDetail(orderId, memberId);
        return new ServerResponse.ResponseBuilder().data(list).build();
    }

    /***
     * 查询会员店铺订单销售总额
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/storeOrdersTotalPrice", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getStoreOrdersTotalPrice() throws EqianyuanException {
        int memberId = getLoginMember().getMemberId();
        Double totalPrice = memberService.getStoreOrdersTotalPrice(memberId);
        return new ServerResponse.ResponseBuilder().data(totalPrice).build();
    }

    /***
     * 查询会员背包商品分页列表
     *
     * @param goods
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/backpackGoods", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getBackpackGoodsPageList(@ModelAttribute Goods goods, @ModelAttribute Pager page) throws EqianyuanException {
        int memberId = getLoginMember().getMemberId();
        PageResponse pageResponse = memberService.getBackpackGoodsPageList(memberId, goods, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 获取背包商品详情
     *
     * @param orderId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/backpackGoods/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getBackpackGoodsDetail(@PathVariable String id) throws EqianyuanException {
        GoodsBoWithCount goods = memberService.getBackpackGoodsDetail(id);
        return new ServerResponse.ResponseBuilder().data(goods).build();
    }

}
