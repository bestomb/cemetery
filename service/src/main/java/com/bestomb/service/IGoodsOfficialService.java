package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.goods.GoodsEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.goods.GoodsOfficialBO;

public interface IGoodsOfficialService {

    /**
     * 分页查询商品集合
     *
     * @param page
     * @return
     * @throws EqianyuanException
     */
    PageResponse getList(Pager page) throws EqianyuanException;

    /**
     * 根据商品编号查询商品详情
     *
     * @param goodsId
     * @return
     * @throws EqianyuanException
     */
    GoodsOfficialBO getInfo(String goodsId) throws EqianyuanException;

    /**
     * 添加商品
     */
    void add(GoodsEditRequest goodsEditRequest) throws EqianyuanException;

    /**
     * 修改商品
     */
    void modify(GoodsEditRequest goodsEditRequest) throws EqianyuanException;

    /**
     * 删除商品
     */
    void removeByIds(String... goodsId) throws EqianyuanException;


}
