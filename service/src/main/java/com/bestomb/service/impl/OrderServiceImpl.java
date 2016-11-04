package com.bestomb.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.PurchaseOrderBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.*;
import com.bestomb.entity.*;
import com.bestomb.service.IOrderService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private IBackpackDao backpackDao;

    @Autowired
    private IMemberAccountDao memberAccountDao;

    @Autowired
    private IGoodsOfficialDao goodsOfficialDao;

    @Autowired
    private IGoodsPersonageDao goodsPersonageDao;

    @Autowired
    private IPlantsAndAnimalsDao plantsAndAnimalsDao;

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
     * @param memberId
     * @return
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void goodsBuy(String goodsInfo, Integer memberId) throws Exception {
        if (StringUtils.isEmpty(goodsInfo)) {
            logger.warn("不存在购物商品");
            return;
        }

        //将购物清单json字符串转为json对象
        JSONObject goodsJSONObject = JSONObject.parseObject(goodsInfo);
        System.out.println(JSONArray.parseArray(goodsJSONObject.getJSONArray("goods").toJSONString()));

        //从json中获取商品集合数据
        JSONArray goodsJSONArray = goodsJSONObject.getJSONArray("goods");
        if (CollectionUtils.isEmpty(goodsJSONArray)) {
            logger.warn("不存在购物商品");
            return;
        }

        //申明商品编号集合对象
        List<String> goodsIdsForOfficial = new ArrayList<String>();
        List<String> goodsIdsForPersonage = new ArrayList<String>();
        List<String> goodsIdsForPlantsAndAnimals = new ArrayList<String>();

        //遍历商品集合
        for (int i = 0; i < goodsJSONArray.size(); i++) {
            JSONObject goodsInfoJSONObject = goodsJSONArray.getJSONObject(i);
            //商品来源：1=官网、2=会员、3=动植物园商城
            int goodsSource = goodsInfoJSONObject.getIntValue("source");
            String goodsId = goodsInfoJSONObject.getString("goods_id");
            if (goodsSource == 1) {
                goodsIdsForOfficial.add(goodsId);
            } else if (goodsSource == 2) {
                goodsIdsForPersonage.add(goodsId);
            } else if (goodsSource == 3) {
                goodsIdsForPlantsAndAnimals.add(goodsId);
            }
        }

        //官网商城商品集合
        List<GoodsOfficialWithBLOBs> goodsOfficialWithBLOBses = null;
        if (!CollectionUtils.isEmpty(goodsIdsForOfficial)) {
            goodsOfficialWithBLOBses = goodsOfficialDao.selectByIds(goodsIdsForOfficial);
        }
        //会员商城商品集合
        List<GoodsPersonageInfo> goodsPersonageInfoList = null;
        if (!CollectionUtils.isEmpty(goodsIdsForPersonage)) {
            goodsPersonageInfoList = goodsPersonageDao.selectByIds(goodsIdsForPersonage);
        }
        //动植物园商品集合
        List<PlantsAndAnimals> plantsAndAnimalses = null;
        if (!CollectionUtils.isEmpty(goodsIdsForPlantsAndAnimals)) {
            plantsAndAnimalses = plantsAndAnimalsDao.selectByIds(goodsIdsForPlantsAndAnimals);
        }

        //如果没有查询到商城商品，返回空
        if (CollectionUtils.isEmpty(goodsOfficialWithBLOBses) && CollectionUtils.isEmpty(goodsPersonageInfoList) && CollectionUtils.isEmpty(plantsAndAnimalses)) {
            return;
        }

        //构建会员背包数据集合
        List<Backpack> backpackList = new ArrayList<Backpack>();
        //构建订单商品交易信息
        List<OrderGoods> orderGoodsList = new ArrayList<OrderGoods>();
        //构建会员商城商品库存扣减信息集合
        List<Map<String, String>> repertory = new ArrayList<Map<String, String>>();
        //会员商品销售情况集合，用于销售成功后，将销售交易币累计到当前商品发布的会员账号中
        List<Map<String, Object>> memberSaleList = new ArrayList<Map<String, Object>>();

        //购物商品总价
        double goodsAmount = 0;

        //计算购物总价
        for (Object o : goodsJSONArray) {
            JSONObject goodsJson = (JSONObject) o;
            //单品购买量
            int buyCount = goodsJson.getIntValue("count");
            //当前遍历的商品数据，是否已经在某个商城的集合数据中得到计算
            boolean isCalculate = false;
            if (StringUtils.equals(goodsJson.getString("source"), "1")) {
                for (GoodsOfficialWithBLOBs goodsOfficialWithBLOBse : goodsOfficialWithBLOBses) {
                    if (!StringUtils.equals(goodsOfficialWithBLOBse.getId(), goodsJson.getString("goods_id"))) {
                        continue;
                    }

                    goodsAmount += calculateGoodsAmount(goodsOfficialWithBLOBse.getPrice(), buyCount);

                    //构建背包数据
                    backpackList.add(buildBackpack(memberId, 1, buyCount, goodsOfficialWithBLOBse.getId()));
                    //构建订单商品数据
                    orderGoodsList.add(buildOrderGoods(buyCount, goodsOfficialWithBLOBse.getName(), goodsOfficialWithBLOBse.getPrice().doubleValue()));

                    isCalculate = true;
                    break;
                }
            }

            if (!isCalculate && StringUtils.equals(goodsJson.getString("source"), "2")) {
                for (GoodsPersonageInfo goodsPersonageInfo : goodsPersonageInfoList) {
                    if (!StringUtils.equals(goodsPersonageInfo.getId(), goodsJson.getString("goods_id"))) {
                        continue;
                    }

                    goodsAmount += calculateGoodsAmount(goodsPersonageInfo.getPrice(), buyCount);

                    //构建背包数据
                    backpackList.add(buildBackpack(memberId, 2, buyCount, goodsPersonageInfo.getId()));
                    //构建订单商品数据
                    orderGoodsList.add(buildOrderGoods(buyCount, goodsPersonageInfo.getName(), goodsPersonageInfo.getPrice().doubleValue()));
                    //构建库存扣减数据
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("repertory", String.valueOf(buyCount));
                    map.put("id", goodsPersonageInfo.getId());
                    repertory.add(map);

                    //构建销售情况数据
                    Map<String, Object> saleMap = new HashMap<String, Object>();
                    saleMap.put("memberId", goodsPersonageInfo.getMemberId());
                    saleMap.put("tradingAmount", calculateGoodsAmount(goodsPersonageInfo.getPrice(), buyCount));
                    memberSaleList.add(saleMap);

                    isCalculate = true;
                    break;
                }
            }

            if (!isCalculate && StringUtils.equals(goodsJson.getString("source"), "3")) {
                for (PlantsAndAnimals plantsAndAnimalse : plantsAndAnimalses) {
                    if (!StringUtils.equals(plantsAndAnimalse.getId(), goodsJson.getString("goods_id"))) {
                        continue;
                    }
                    goodsAmount += calculateGoodsAmount(plantsAndAnimalse.getPrice(), buyCount);

                    //构建背包数据
                    backpackList.add(buildBackpack(memberId, 3, buyCount, plantsAndAnimalse.getId()));
                    //构建订单商品数据
                    orderGoodsList.add(buildOrderGoods(buyCount, plantsAndAnimalse.getName(), plantsAndAnimalse.getPrice().doubleValue()));
                    break;
                }
            }
        }

        //从json中获取是否积分抵扣及抵扣积分数额
        boolean integralDeduction = goodsJSONObject.getBooleanValue("integral_deduction");

        //积分抵扣额度
        int deduction = 0;
        if (integralDeduction) {
            //积分与交易币的兑换比例为：100:1
            deduction = goodsJSONObject.getIntValue("deduction") / 100;
        }

        goodsAmount = goodsAmount - deduction;

        /**
         * 持久化会员数据
         * 扣减积分、扣减交易币
         */
        int updateMemberAccount = memberAccountDao.updateByGoodsBuy(goodsAmount, deduction * 100, memberId);
        if (updateMemberAccount > 0) {
            //持久化会员购物订单数据
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setMemberId(memberId);
            purchaseOrder.setCreateTime(CalendarUtil.getSystemSeconds());
            purchaseOrder.setPrice(BigDecimal.valueOf(goodsAmount + deduction));
            purchaseOrder.setPaidPrice(BigDecimal.valueOf(goodsAmount));
            purchaseOrder.setIntegral(deduction * 100);
            purchaseOrder.setGoods(JSONArray.toJSONString(orderGoodsList));
            purchaseOrderDao.insertSelective(purchaseOrder);

            //持久化会员背包数据
            backpackDao.insertByGoodsBuy(backpackList);

            //将会员发布商品销售额叠加到会员账户
            memberAccountDao.batchUpdateBySale(memberSaleList);

            //会员商城商品库存扣减
            int updateRow = goodsPersonageDao.batchUpdateByRepertory(repertory);
            if (updateRow < repertory.size()) {
                logger.error("批量更新会员商品库存失败");
                throw new Exception("批量更新会员商品库存失败");
            }
        }

        //todo 将每一笔交易币变动和积分变动都持久化记录，将不同错误提示写完整，例如：账户余额不足...
    }

    /**
     * 构建背包数据
     *
     * @param memberId 会员编号
     * @param source   来源（1：官网、2：会员商城、3：动植物园商城
     * @param count    单品购买量
     * @param goodsId  商品编号
     */
    private Backpack buildBackpack(Integer memberId, int source, int count, String goodsId) {
        Backpack backpack = new Backpack();
        backpack.setMemberId(memberId);
        backpack.setSource(source);
        backpack.setCount(count);
        backpack.setGoodsId(goodsId);
        return backpack;
    }

    /**
     * 构建购物订单商品数据
     *
     * @param buyCount   单品购买数量
     * @param goodsName  商品名称
     * @param goodsPrice 单品价格
     * @return
     */
    private OrderGoods buildOrderGoods(int buyCount, String goodsName, double goodsPrice) {
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setName(goodsName);
        orderGoods.setPrice(BigDecimal.valueOf(goodsPrice));
        orderGoods.setCount(buyCount);
        return orderGoods;
    }

    /**
     * 根据商品单价及商品购买总量计算单品总价
     *
     * @param signAmount 单价
     * @param count      购买总量
     * @return
     */
    private double calculateGoodsAmount(BigDecimal signAmount, int count) {
        return signAmount.doubleValue() * count;
    }
}
