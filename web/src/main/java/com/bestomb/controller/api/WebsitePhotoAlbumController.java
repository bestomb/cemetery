package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.photoAlbum.PhotoAlbumEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsitePhotoAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 陵园纪念人相册API控制器
 * Created by jason on 2016-10-18.
 */
@Controller
@RequestMapping("/website/photoAlbum_api")
public class WebsitePhotoAlbumController extends BaseController {

    @Autowired
    private WebsitePhotoAlbumService websitePhotoAlbumService;

    /**
     * 创建纪念人相册
     *
     * @param photoAlbumEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(PhotoAlbumEditRequest photoAlbumEditRequest) throws EqianyuanException {
        websitePhotoAlbumService.add(photoAlbumEditRequest);
        return new ServerResponse();
    }

    /***
     * 根据纪念人及分页信息查询相册分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getList")
    @ResponseBody
    public ServerResponse getList(String masterId, @ModelAttribute Pager page) throws EqianyuanException {
        PageResponse pageResponse = websitePhotoAlbumService.getList(masterId, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 根据相册编号删除数据
     *
     * @param cemeteryId   陵园编号
     * @param photoAlbumId 相册编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/del")
    @ResponseBody
    public ServerResponse del(String cemeteryId, String photoAlbumId) throws EqianyuanException {
        websitePhotoAlbumService.del(cemeteryId, photoAlbumId);
        return new ServerResponse();
    }
}
