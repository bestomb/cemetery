package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.goodsclass.GoodsClassifyRequest;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.goodsclassify.GoodsClassifyBo;
import com.bestomb.service.IGoodsClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/sys_goodsclassify")
public class GoodsClassifyController extends BaseController {

    @Autowired
    private IGoodsClassifyService goodsClassifyService;


    /**
     * 添加商品分类
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(GoodsClassifyRequest goodsClassifyRequest)throws EqianyuanException{
        GoodsClassifyBo goodsClassifyBo = goodsClassifyService.add(goodsClassifyRequest);
        return new ServerResponse.ResponseBuilder().data(goodsClassifyBo).build();
    }

    /**
     * 修改商品
     */
    @RequestMapping("/update")
    @ResponseBody
    public ServerResponse modify(GoodsClassifyRequest goodsClassifyRequest) throws EqianyuanException{
        goodsClassifyService.modify(goodsClassifyRequest);
        return new ServerResponse();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(String... id) throws EqianyuanException{
        goodsClassifyService.removeByIds(id);
        return new ServerResponse();
    }

    /**
     * 根据父类编号查询子类集合
     *
     *  @param parentId 父编号
     */
    @RequestMapping("/getleveParentid")
    @ResponseBody
    public ServerResponse getLevelOneListByParentId (String parentId) throws EqianyuanException{
        List<GoodsClassifyBo> goodsClassifyBos = goodsClassifyService.getLevelOneListByParentId(parentId);
        return new ServerResponse.ResponseBuilder().data(goodsClassifyBos).build();
    }

}
