package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.memorialRecord.MemorialRecordEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsiteMemorialRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 陵园墓碑纪念人纪念记录API控制器
 * Created by jason on 2016-10-18.
 */
@Controller
@RequestMapping("/website/memorialRecord_api")
public class WebsiteMemorialRecordController extends BaseController {

    @Autowired
    private WebsiteMemorialRecordService websiteMemorialRecordService;

    /**
     * 保存纪念记录
     *
     * @param memorialRecordEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(MemorialRecordEditRequest memorialRecordEditRequest) throws EqianyuanException {
        websiteMemorialRecordService.add(memorialRecordEditRequest);
        return new ServerResponse();
    }

    /***
     * 根据纪念人编号分页查询纪念人纪念记录
     *
     * @param masterId 纪念人编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getList")
    @ResponseBody
    public ServerResponse getList(String masterId, Pager page) throws EqianyuanException {
        PageResponse pageResponse = websiteMemorialRecordService.getList(masterId, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }
}
