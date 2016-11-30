package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.service.IGoodsOfficialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jason on 2016-10-18.
 */
@Service
public class WebsiteGoodsOfficialService {

    @Autowired
    private IGoodsOfficialService goodsOfficialService;

    /**
     * 根据商品二级分类及分页信息查询商品集合
     *
     * @param secondClassify
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getGoodsBySecondClassify(Integer secondClassify, Pager page) throws EqianyuanException {
        return goodsOfficialService.getGoodsBySecondClassify(secondClassify, page);
    }
}
