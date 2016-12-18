package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.cemetery.BiontBo;
import com.bestomb.controller.BaseController;
import com.bestomb.entity.Biont;
import com.bestomb.sevice.api.BiontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/***
 * 动植物API接口
 *
 * @author qfzhang
 */

@Controller
@RequestMapping("/website/biont_api")
public class BiontController extends BaseController {

    @Autowired
    private BiontService biontService;


    /***
     * 查询动植物分页列表
     *
     * @param goods
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/bionts", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ServerResponse getBiontPageList(Biont biont, Pager page) throws EqianyuanException {
        PageResponse pageResponse = biontService.getPageList(biont, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 查询动植物详情
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/bionts/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ServerResponse getGoodsDetail(@PathVariable String id) throws EqianyuanException {
        BiontBo biontBo = biontService.getDetail(id);
        return new ServerResponse.ResponseBuilder().data(biontBo).build();
    }

    /***
     * 动植物升级（动物喂养，植物施肥）
     *
     * @param { cemeteryId, goodsId }
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/bionts/upgrade", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ServerResponse upgrade(Biont biont) throws EqianyuanException {
        boolean flag = biontService.upgrade(biont);
        return new ServerResponse.ResponseBuilder().data(flag).build();
    }

    /***
     * 动植物拾取（动物收取，植物收割）
     *
     * @param { cemeteryId, goodsId }
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping(value = "/bionts/pickUp", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ServerResponse pickUp(Biont biont) throws EqianyuanException {
        int memberId = getLoginMember().getMemberId();
        boolean flag = biontService.pickUp(biont, memberId);
        return new ServerResponse.ResponseBuilder().data(flag).build();
    }

}
