package com.bestomb.controller;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.goods.SecondClassificationEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.goods.SecondClassificationBO;
import com.bestomb.service.ISecondClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品二级分类控制器
 * Created by jason on 2016-07-25.
 */
@Controller
@RequestMapping("/second_classify")
public class SecondClassifyController extends BaseController {

    @Autowired
    private ISecondClassifyService secondClassifyService;

    /**
     * 添加分类
     *
     * @param secondClassificationEditRequest
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(SecondClassificationEditRequest secondClassificationEditRequest) throws EqianyuanException {
        secondClassifyService.add(secondClassificationEditRequest);
        return new ServerResponse();
    }

    /**
     * 信息删除
     *
     * @param id 分类编号
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(String... id) throws EqianyuanException {
        secondClassifyService.removeByIds(id);
        return new ServerResponse();
    }

    /**
     * 信息修改
     *
     * @param secondClassificationEditRequest
     * @return
     */
    @RequestMapping("/modify")
    @ResponseBody
    public ServerResponse modify(SecondClassificationEditRequest secondClassificationEditRequest) throws EqianyuanException {
        secondClassifyService.modify(secondClassificationEditRequest);
        return new ServerResponse();
    }

    /**
     * 分页查询数据集合
     *
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/paginationList")
    @ResponseBody
    public ServerResponse dataList(@ModelAttribute Pager page,
                                   Integer firstClassify) throws EqianyuanException {
        PageResponse pageResponse = secondClassifyService.getPageList(firstClassify, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 分页查询数据集合
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

    /**
     * 根据编号查询二级分类详情
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getInfo")
    @ResponseBody
    public ServerResponse getInfo(String id) throws EqianyuanException {
        SecondClassificationBO secondClassificationBO = secondClassifyService.getInfo(id);
        return new ServerResponse.ResponseBuilder().data(secondClassificationBO).build();
    }
}
