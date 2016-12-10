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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    public ServerResponse addMaster(MasterEditRequest masterEditRequest, HttpServletRequest httpServletRequest) throws EqianyuanException {
        websiteMasterService.addMaster(masterEditRequest, httpServletRequest);
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
    public ServerResponse modifyMaster(MasterEditRequest masterEditRequest, HttpServletRequest httpServletRequest) throws EqianyuanException {
        websiteMasterService.modifyMaster(masterEditRequest, httpServletRequest);
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
     * 根据条件纪念人编号查询祭品集合
     *
     * @param masterId 纪念人编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getOblationByMasterId")
    @ResponseBody
    public ServerResponse getOblationByMasterId(String masterId) throws EqianyuanException {
        List<Map<String, String>> oblations = websiteMasterService.getOblationByMasterId(masterId);
        return new ServerResponse.ResponseBuilder().data(oblations).build();
    }

    /***
     * 根据条件陵园墓碑编号查询墓中纪念人祭品集合
     *
     * @param tombstoneId 墓碑编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getOblationByTombstoneId")
    @ResponseBody
    public ServerResponse getOblationByTombstoneId(String tombstoneId) throws EqianyuanException {
        List<Map<String, String>> oblations = websiteMasterService.getOblationByTombstoneId(tombstoneId);
        return new ServerResponse.ResponseBuilder().data(oblations).build();
    }

    /***
     * 根据陵园墓碑纪念人编号删除纪念人信息
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
