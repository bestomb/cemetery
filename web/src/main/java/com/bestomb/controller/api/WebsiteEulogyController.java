package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.eulogy.EulogyEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.master.eulogy.EulogyBo;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsiteEulogyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 陵园纪念人祭文悼词API控制器
 * Created by jason on 2016-10-18.
 */
@Controller
@RequestMapping("/website/eulogy_api")
public class WebsiteEulogyController extends BaseController {

    @Autowired
    private WebsiteEulogyService websiteEulogyService;

    /**
     * 添加祭文悼词
     *
     * @param eulogyEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(EulogyEditRequest eulogyEditRequest) throws EqianyuanException {
        websiteEulogyService.add(eulogyEditRequest);
        return new ServerResponse();
    }

    /***
     * 根据纪念人编号查询祭文悼词分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getList")
    @ResponseBody
    public ServerResponse getList(String masterId, @ModelAttribute Pager page) throws EqianyuanException {
        PageResponse pageResponse = websiteEulogyService.getList(masterId, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 根据祭文悼词编号查询详情
     *
     * @param eulogyId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getInfo")
    @ResponseBody
    public ServerResponse getInfo(String eulogyId) throws EqianyuanException {
        EulogyBo eulogyBo = websiteEulogyService.getInfo(eulogyId);
        return new ServerResponse.ResponseBuilder().data(eulogyBo).build();
    }

}
