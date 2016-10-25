package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.lifeWorksCollection.lifeWorks.LifeWorksEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.master.lifeWorksCollection.lifeWorks.LifeWorksBo;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsiteLifeWorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 陵园纪念人作品集作品API控制器
 * Created by jason on 2016-10-18.
 */
@Controller
@RequestMapping("/website/lifeWorks_api")
public class WebsiteLifeWorksController extends BaseController {

    @Autowired
    private WebsiteLifeWorksService websiteLifeWorksService;

    /**
     * 为作品集添加作品
     *
     * @param lifeWorksEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(LifeWorksEditRequest lifeWorksEditRequest) throws EqianyuanException {
        websiteLifeWorksService.add(lifeWorksEditRequest);
        return new ServerResponse();
    }

    /***
     * 根据作品及编号编号查询作品分页集合
     *
     * @param lifeWorksCollectionId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getList")
    @ResponseBody
    public ServerResponse getList(String lifeWorksCollectionId, @ModelAttribute Pager page) throws EqianyuanException {
        PageResponse pageResponse = websiteLifeWorksService.getList(lifeWorksCollectionId, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 根据作品编号查询作品详情
     *
     * @param lifeWorksId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getInfo")
    @ResponseBody
    public ServerResponse getInfo(String lifeWorksId) throws EqianyuanException {
        LifeWorksBo lifeWorksBo = websiteLifeWorksService.getInfo(lifeWorksId);
        return new ServerResponse.ResponseBuilder().data(lifeWorksBo).build();
    }

    /***
     * 根据作品编号删除作品数据
     *
     * @param cemeteryId  陵园编号
     * @param lifeWorksId 作品编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/del")
    @ResponseBody
    public ServerResponse del(String cemeteryId, String lifeWorksId) throws EqianyuanException {
        websiteLifeWorksService.del(cemeteryId, lifeWorksId);
        return new ServerResponse();
    }
}
