package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.lifeWorksCollection.LifeWorksCollectionEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsiteLifeWorksCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 陵园纪念人生前作品集API控制器
 * Created by jason on 2016-10-18.
 */
@Controller
@RequestMapping("/website/lifeWorksCollection_api")
public class WebsiteLifeWorksCollectionController extends BaseController {

    @Autowired
    private WebsiteLifeWorksCollectionService websiteLifeWorksCollectionService;

    /**
     * 创建纪念人作品集
     *
     * @param lifeWorksCollectionEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(LifeWorksCollectionEditRequest lifeWorksCollectionEditRequest) throws EqianyuanException {
        websiteLifeWorksCollectionService.add(lifeWorksCollectionEditRequest);
        return new ServerResponse();
    }

    /***
     * 根据纪念人及分页信息查询作品集分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getList")
    @ResponseBody
    public ServerResponse getList(String masterId, @ModelAttribute Pager page) throws EqianyuanException {
        PageResponse pageResponse = websiteLifeWorksCollectionService.getList(masterId, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 根据作品集编号删除数据
     *
     * @param cemeteryId            陵园编号
     * @param lifeWorksCollectionId 作品集编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/del")
    @ResponseBody
    public ServerResponse del(String cemeteryId, String lifeWorksCollectionId) throws EqianyuanException {
        websiteLifeWorksCollectionService.del(cemeteryId, lifeWorksCollectionId);
        return new ServerResponse();
    }
}
