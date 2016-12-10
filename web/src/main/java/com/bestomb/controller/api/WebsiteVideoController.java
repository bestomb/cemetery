package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.videoAlbum.video.VideoEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.master.videoAlbum.video.VideoBo;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsiteVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 陵园纪念人专辑视频API控制器
 * Created by jason on 2016-10-18.
 */
@Controller
@RequestMapping("/website/video_api")
public class WebsiteVideoController extends BaseController {

    @Autowired
    private WebsiteVideoService websiteVideoService;

    /**
     * 为专辑添加视频
     *
     * @param videoEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(VideoEditRequest videoEditRequest, HttpServletRequest httpServletRequest) throws EqianyuanException {
        websiteVideoService.add(videoEditRequest, httpServletRequest);
        return new ServerResponse();
    }

    /***
     * 根据专辑编号查询视频分页集合
     *
     * @param videoAlbumId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getList")
    @ResponseBody
    public ServerResponse getList(String videoAlbumId, @ModelAttribute Pager page) throws EqianyuanException {
        PageResponse pageResponse = websiteVideoService.getList(videoAlbumId, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 根据视频编号查询视频详情
     *
     * @param videoId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getInfo")
    @ResponseBody
    public ServerResponse getInfo(String videoId) throws EqianyuanException {
        VideoBo videoBo = websiteVideoService.getInfo(videoId);
        return new ServerResponse.ResponseBuilder().data(videoBo).build();
    }

    /***
     * 根据视频编号删除视频数据
     *
     * @param cemeteryId 陵园编号
     * @param videoId    视频编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/del")
    @ResponseBody
    public ServerResponse del(String cemeteryId, String videoId) throws EqianyuanException {
        websiteVideoService.del(cemeteryId, videoId);
        return new ServerResponse();
    }
}
