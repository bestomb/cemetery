package com.bestomb.controller;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.goods.GoodsEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.goods.GoodsOfficialBO;
import com.bestomb.service.IGoodsOfficialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 官网商城管理
 */
@Controller
@RequestMapping("/goodsOfficial")
public class GoodsOfficialController extends BaseController {

    @Autowired
    private IGoodsOfficialService goodsOfficialService;

    /**
     * 分页获取官网商城商品集合
     *
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getList")
    @ResponseBody
    public ServerResponse getList(@RequestParam(value = "firstClass", required = false) String firstClass,
                                  @RequestParam(value = "secondClass", required = false) String secondClass,
                                  @ModelAttribute Pager page) throws EqianyuanException {
        PageResponse pageResponse = goodsOfficialService.getList(firstClass, secondClass, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 根据商品编号查询商品详情
     *
     * @param goodsId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getInfo")
    @ResponseBody
    public ServerResponse getInfo(String goodsId) throws EqianyuanException {
        GoodsOfficialBO goodsOfficialBO = goodsOfficialService.getInfo(goodsId);
        return new ServerResponse.ResponseBuilder().data(goodsOfficialBO).build();
    }

    /**
     * 发布商品
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(GoodsEditRequest goodsEditRequest) throws EqianyuanException {
        goodsOfficialService.add(goodsEditRequest);
        return new ServerResponse();
    }

    /**
     * 修改商品
     */
    @RequestMapping("/update")
    @ResponseBody
    public ServerResponse modify(GoodsEditRequest goodsEditRequest) throws EqianyuanException {
        goodsOfficialService.modify(goodsEditRequest);
        return new ServerResponse();
    }

    /**
     * 删除商品
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(String... goodsId) throws EqianyuanException {
        goodsOfficialService.removeByIds(goodsId);
        return new ServerResponse();
    }
}
