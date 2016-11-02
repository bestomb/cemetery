package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.entity.PurchaseOrder;

/***
 * 订单接口
 *
 * @author qfzhang
 */
public interface IOrderService {

    /***
     * 查询订单分页列表
     *
     * @param order
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getPageList(PurchaseOrder order, Pager page) throws EqianyuanException;

    /***
     * 商品购买
     *
     * @param goodsInfo
     * @return
     * @throws EqianyuanException
     */
    void goodsBuy(String goodsInfo) throws EqianyuanException;

}
