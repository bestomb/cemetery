package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.PurchaseOrderBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.IOrderGoodsDao;
import com.bestomb.dao.IPurchaseOrderDao;
import com.bestomb.entity.PurchaseOrder;
import com.bestomb.service.IOrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/***
 * 订单接口实现
 *
 * @author Administrator
 */
@Service
public class OrderServiceImpl implements IOrderService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IPurchaseOrderDao purchaseOrderDao;
    @Autowired
    private IOrderGoodsDao orderGoodsDao;

    /***
     * 查询订单分页列表
     *
     * @param order
     * @param page
     * @return
     */
    public PageResponse getPageList(PurchaseOrder order, Pager page) throws EqianyuanException {
        // 会员编号是否为空
        if (ObjectUtils.isEmpty(order.getMemberId())) {
            logger.warn("getPageList fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        // 先查询总数
        int dataCount = purchaseOrderDao.getPageListCount(order);
        if (dataCount == 0) {
            logger.info("根据条件查询订单列表无数据l");
            return new PageResponse(page, null);
        }
        page.setTotalRow(dataCount);
        // 再查询数据
        List<PurchaseOrder> orders = purchaseOrderDao.getPageList(order, page);
        if (CollectionUtils.isEmpty(orders)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询订单列表无数据l");
            return new PageResponse(page, null);
        }
        // 使用BO返回
        List<PurchaseOrderBo> resultList = new ArrayList<PurchaseOrderBo>();
        for (PurchaseOrder o : orders) {
            PurchaseOrderBo bo = new PurchaseOrderBo();
            BeanUtils.copyProperties(o, bo);
            bo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(o.getCreateTime())); // 转化创建时间
            resultList.add(bo);
        }

        return new PageResponse(page, resultList);
    }

    /***
     * 商品购买
     *
     * @param goodsInfo
     * @return
     * @throws EqianyuanException
     */
    public void goodsBuy(String goodsInfo) throws EqianyuanException {
//        if (StringUtils.isEmpty(goodsInfo)) {
//            logger.warn("不存在购物商品");
//            return;
//        }
//
//        /**
//         * 购物业务
//         * 1.将请求购物单json字符串转为json对象
//         * 2.从json对象中获取商品信息jsonArray对象
//         * 3.遍历商品对象集合，获取每一个商品的商品编号，并分开
//         * 4.分别查询商品信息集合
//         * 5.遍历商品集合，并根据商品信息json对象中的数量进行商品总价运算
//         * 6.从json中获取是否积分抵扣及抵扣额
//         * 7.将积分等比后，将购买商品总价进行扣减
//         * 8.更新会员表数据，扣减积分和交易币
//         * 9.成功更新后，持久化各种商品数据
//         */
//
//        //将购物清单json字符串转为json对象
//        JSONObject goodsJSONObject = JSONObject.parseObject(goodsInfo);
//
//        //从json中获取是否积分抵扣及抵扣积分数额
//        boolean integralDeduction = goodsJSONObject.getBooleanValue("integral_deduction");
//
//        //积分抵扣额度
//        int deduction;
//        if (integralDeduction) {
//            deduction = goodsJSONObject.getIntValue("deduction");
//        }
//        //从json中获取商品集合数据
//        JSONArray goodsJSONArray = goodsJSONObject.getJSONArray("goods");
//        //申明商品编号集合对象
//        List<String> goodsIdsForOfficial = new ArrayList<String>();
//        List<String> goodsIdsForPersonage = new ArrayList<String>();
//        List<String> goodsIdsForPlantsAndAnimals = new ArrayList<String>();
//
//        //遍历商品集合
//        for (int i = 0; i < goodsJSONArray.size(); i++) {
//            JSONObject goodsInfoJSONObject = goodsJSONArray.getJSONObject(i);
//            if (goodsInfoJSONObject.getIntValue("source") == 1) {
//                goodsIdsForOfficial.add(goodsInfoJSONObject.getString("goods_id"));
//            } else if (goodsInfoJSONObject.getIntValue("source") == 2) {
//                goodsIdsForPersonage.add(goodsInfoJSONObject.getString("goods_id"));
//            } else if (goodsInfoJSONObject.getIntValue("source") == 3) {
//                goodsIdsForPlantsAndAnimals.add(goodsInfoJSONObject.getString("goods_id"));
//            }
//        }

        //根据商品编号集合查询商品数据


        //积分与交易币的兑换比例为：100:1


        // 查询该条订单总金额（此处比较麻烦）
        // 查询会员交易币总额
        // 根据订单总金额扣减会员交易币（交易币足够直接扣减；否则抛出错误）
        // 将订单商品放入我的背包和我的订单中

    }

}
