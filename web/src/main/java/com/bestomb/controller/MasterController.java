package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.master.MasterBo;
import com.bestomb.service.IMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys_master")
public class MasterController extends BaseController {

    @Autowired
    private IMasterService masterService;

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getlistmaster")
    @ResponseBody
    public ServerResponse getList(Integer cemeteryId,
                                  @RequestParam(value = "pageNo" ,required = false ,defaultValue = "1") int pageNo,
                                  @RequestParam(value = "pageSize",required = false, defaultValue = "10")int pageSize)throws EqianyuanException {
        PageResponse pageResponse = masterService.getList(pageNo,pageSize,cemeteryId);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 详细查询
     * @param id
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/queryid")
    @ResponseBody
    public ServerResponse queryById(String id)throws EqianyuanException {
        MasterBo masterBo = masterService.queryById(id);
        return new ServerResponse.ResponseBuilder().data(masterBo).build();
    }
}
