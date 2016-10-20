package com.bestomb.controller.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.MasterEditRequest;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.master.MasterBo;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsiteMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 陵园墓碑纪念人API控制器
 * Created by jason on 2016-10-18.
 */
@Controller
@RequestMapping("/website/master_api")
public class WebsiteMasterController extends BaseController {

    @Autowired
    private WebsiteMasterService websiteMasterService;

    /**
     * 陵园墓碑创建
     *
     * @param masterEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/addMaster")
    @ResponseBody
    public ServerResponse addMaster(MasterEditRequest masterEditRequest) throws EqianyuanException {
        websiteMasterService.addMaster(masterEditRequest);
        return new ServerResponse();
    }

    /**
     * 陵园墓碑修改
     *
     * @param masterEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/modifyMaster")
    @ResponseBody
    public ServerResponse modifyMaster(MasterEditRequest masterEditRequest) throws EqianyuanException {
        websiteMasterService.modifyMaster(masterEditRequest);
        return new ServerResponse();
    }

    /***
     * 根据陵园墓碑编号查询墓中纪念人集合
     *
     * @param tombstoneId 陵园墓碑编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getMasterByTombstone")
    @ResponseBody
    public ServerResponse getMasterByTombstone(String tombstoneId) throws EqianyuanException {
        List<MasterBo> masterBos = websiteMasterService.queryByTombstone(tombstoneId);
        return new ServerResponse.ResponseBuilder().data(masterBos).build();
    }

    /***
     * 根据陵园编号删除墓碑
     *
     * @param cemeteryId 陵园编号
     * @param masterId   纪念人编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/delMaster")
    @ResponseBody
    public ServerResponse delMaster(String cemeteryId, String masterId) throws EqianyuanException {
        websiteMasterService.delMaster(cemeteryId, masterId);
        return new ServerResponse();
    }
}
