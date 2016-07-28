package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.model.ModelByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.model.ModelBo;
import com.bestomb.service.IModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 模型控制器
 * Created by jason on 2016-07-25.
 */
@Controller
@RequestMapping("/model")
public class ModelController extends BaseController{

    @Autowired
    private IModelService modelService;

    /**
     * 添加模型
     *
     * @param modelByEditRequest
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(HttpServletRequest request, ModelByEditRequest modelByEditRequest) throws EqianyuanException {
        modelService.add(request, modelByEditRequest);
        return new ServerResponse();
    }

    /**
     * 信息删除
     *
     * @param id 模型编号
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(String... id) throws EqianyuanException {
        modelService.removeByIds(id);
        return new ServerResponse();
    }

    /**
     * 信息修改
     *
     * @param modelByEditRequest
     * @return
     */
    @RequestMapping("/modify")
    @ResponseBody
    public ServerResponse modify(HttpServletRequest request, ModelByEditRequest modelByEditRequest) throws EqianyuanException {
        modelService.modify(request, modelByEditRequest);
        return new ServerResponse();
    }

    /**
     * 分页查询数据集合
     *
     * @param pageNo   分页页码
     * @param pageSize 分页每页显示条数
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/paginationList")
    @ResponseBody
    public ServerResponse dataList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                   String classifyId) {
        PageResponse pageResponse = modelService.queryByPagination(pageNo, pageSize, classifyId);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 信息详情
     *
     * @param id 管序列编号
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public ServerResponse queryById(String id) throws EqianyuanException {
        ModelBo modelBo = modelService.queryById(id);
        return new ServerResponse.ResponseBuilder().data(modelBo).build();
    }

}
