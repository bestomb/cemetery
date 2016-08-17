package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.specialholiday.SpecialHolidayRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.specialholiday.SpecialHolidayBo;
import com.bestomb.service.ISpecialHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sys_specialholiday")
public class SpecialHolidayController extends BaseController {

    @Autowired
    private ISpecialHolidayService specialHolidayService;

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getlistpagespecial")
    @ResponseBody
    public ServerResponse getList(@RequestParam(value = "pageNo" ,required = false , defaultValue = "1") int pageNo,
                                  @RequestParam(value = "pageSize" , required = false , defaultValue = "10") int pageSize) throws EqianyuanException{
        PageResponse pageResponse = specialHolidayService.getList(pageNo,pageSize);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 添加特殊节日
     * @param specialHolidayRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/specialadd")
    @ResponseBody
    public ServerResponse add(SpecialHolidayRequest specialHolidayRequest) throws EqianyuanException{
        specialHolidayService.add(specialHolidayRequest);
        return new ServerResponse();
    }

    /**
     * 删除特殊节日
     * @param id
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/specialdelete")
    @ResponseBody
    public ServerResponse delete(String... id) throws EqianyuanException{
        specialHolidayService.removeByIds(id);
        return new ServerResponse();
    }
}
