package com.bestomb.controller.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.goods.SecondClassificationBO;
import com.bestomb.controller.BaseController;
import com.bestomb.service.ISecondClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品二级分类控制器
 * Created by jason on 2016-07-25.
 */
@Controller
@RequestMapping("/website/second_classify_api")
public class WebsiteSecondClassifyController extends BaseController {

    @Autowired
    private ISecondClassifyService secondClassifyService;

    /**
     * 根据一级分类查询二级分类数据集合
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getList")
    @ResponseBody
    public ServerResponse getList(Integer firstClassify) throws EqianyuanException {
        List<SecondClassificationBO> secondClassificationBOList = secondClassifyService.getList(firstClassify);
        return new ServerResponse.ResponseBuilder().data(secondClassificationBOList).build();
    }
}
