package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.model.ModelClassifyByEditRequest;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.model.ModelClassifyBo;
import com.bestomb.service.IModelClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 模型管理控制器
 * Created by jason on 2016-07-21.
 */
@Controller
@RequestMapping("/model_classify")
public class ModelClassifyController extends BaseController {

    @Autowired
    private IModelClassifyService modelClassifyService;

    /**
     * 添加模型分类
     *
     * @param modelClassifyByEditRequest
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(ModelClassifyByEditRequest modelClassifyByEditRequest) throws EqianyuanException {
        ModelClassifyBo modelClassifyBo =  modelClassifyService.add(modelClassifyByEditRequest);
        return new ServerResponse.ResponseBuilder().data(modelClassifyBo).build();
    }

    /**
     * 模型分类信息删除
     *
     * @param id 编号
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(String... id) throws EqianyuanException {
        modelClassifyService.removeByIds(id);
        return new ServerResponse();
    }

    /**
     * 模型分类信息修改
     *
     * @param modelClassifyByEditRequest
     * @return
     */
    @RequestMapping("/modify")
    @ResponseBody
    public ServerResponse modify(ModelClassifyByEditRequest modelClassifyByEditRequest) throws EqianyuanException {
        modelClassifyService.modify(modelClassifyByEditRequest);
        return new ServerResponse();
    }

    /**
     * 根据父编号查询下一级子模型分类数据集合
     *
     * @param parentId 父编号
     * @return
     */
    @RequestMapping("/getLevelOneListByParentId")
    @ResponseBody
    public ServerResponse getLevelOneListByParentId(String parentId) throws EqianyuanException {
        List<ModelClassifyBo> modelClassifyBos = modelClassifyService.getLevelOneListByParentId(parentId);
        return new ServerResponse.ResponseBuilder().data(modelClassifyBos).build();
    }
}
