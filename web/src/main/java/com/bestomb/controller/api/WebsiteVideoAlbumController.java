package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.videoAlbum.VideoAlbumEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsiteVideoAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 陵园纪念人视频专辑API控制器
 * Created by jason on 2016-10-18.
 */
@Controller
@RequestMapping("/website/videoAlbum_api")
public class WebsiteVideoAlbumController extends BaseController {

    @Autowired
    private WebsiteVideoAlbumService websiteVideoAlbumService;

    /**
     * 创建纪念人专辑
     *
     * @param videoAlbumEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(VideoAlbumEditRequest videoAlbumEditRequest) throws EqianyuanException {
        websiteVideoAlbumService.add(videoAlbumEditRequest);
        return new ServerResponse();
    }

    /***
     * 根据纪念人及分页信息查询专辑分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getList")
    @ResponseBody
    public ServerResponse getList(String masterId, @ModelAttribute Pager page) throws EqianyuanException {
        PageResponse pageResponse = websiteVideoAlbumService.getList(masterId, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 根据专辑编号删除数据
     *
     * @param cemeteryId   陵园编号
     * @param videoAlbumId 专辑编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/del")
    @ResponseBody
    public ServerResponse del(String cemeteryId, String videoAlbumId) throws EqianyuanException {
        websiteVideoAlbumService.del(cemeteryId, videoAlbumId);
        return new ServerResponse();
    }
}
