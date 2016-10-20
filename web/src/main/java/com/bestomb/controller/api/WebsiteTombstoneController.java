package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.TombstoneEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.controller.BaseController;
import com.bestomb.entity.Tombstone;
import com.bestomb.sevice.api.WebsiteTombstoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 陵园墓碑API控制器
 * Created by jason on 2016-10-18.
 */
@Controller
@RequestMapping("/website/tombstone_api")
public class WebsiteTombstoneController extends BaseController {

    @Autowired
    private WebsiteTombstoneService websiteTombstoneService;

    /**
     * 陵园墓碑创建
     *
     * @param tombstoneEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/addTombstone")
    @ResponseBody
    public ServerResponse addTombstone(TombstoneEditRequest tombstoneEditRequest) throws EqianyuanException {
        websiteTombstoneService.addTombstone(tombstoneEditRequest);
        return new ServerResponse();
    }

    /**
     * 陵园墓碑修改
     *
     * @param tombstoneEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/modifyTombstone")
    @ResponseBody
    public ServerResponse modifyTombstone(TombstoneEditRequest tombstoneEditRequest) throws EqianyuanException {
        websiteTombstoneService.modifyTombstone(tombstoneEditRequest);
        return new ServerResponse();
    }

    /***
     * 根据条件查询陵园墓碑分页集合
     *
     * @param tombstone
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getListByCondition")
    @ResponseBody
    public ServerResponse getListByCondition(@ModelAttribute Tombstone tombstone, @ModelAttribute Pager page) throws EqianyuanException {
        PageResponse pageResponse = websiteTombstoneService.getListByCondition(tombstone, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 根据陵园编号删除墓碑
     *
     * @param cemeteryId  陵园编号
     * @param tombstoneId 墓碑编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/delTombstone")
    @ResponseBody
    public ServerResponse delTombstone(String cemeteryId, String tombstoneId) throws EqianyuanException {
        websiteTombstoneService.delTombstone(cemeteryId, tombstoneId);
        return new ServerResponse();
    }
}
