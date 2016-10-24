package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.photoAlbum.photo.PhotoEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.master.photoAlbum.photo.PhotoBo;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsitePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 陵园纪念人相册相片API控制器
 * Created by jason on 2016-10-18.
 */
@Controller
@RequestMapping("/website/photo_api")
public class WebsitePhotoController extends BaseController {

    @Autowired
    private WebsitePhotoService websitePhotoService;

    /**
     * 为相册添加相片
     *
     * @param photoEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(PhotoEditRequest photoEditRequest) throws EqianyuanException {
        websitePhotoService.add(photoEditRequest);
        return new ServerResponse();
    }

    /***
     * 根据相册编号查询相片分页集合
     *
     * @param photoAlbumId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getList")
    @ResponseBody
    public ServerResponse getList(String photoAlbumId, @ModelAttribute Pager page) throws EqianyuanException {
        PageResponse pageResponse = websitePhotoService.getList(photoAlbumId, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 根据相片编号查询相片详情
     *
     * @param photoId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getInfo")
    @ResponseBody
    public ServerResponse getInfo(String photoId) throws EqianyuanException {
        PhotoBo photoBo = websitePhotoService.getInfo(photoId);
        return new ServerResponse.ResponseBuilder().data(photoBo).build();
    }

    /***
     * 根据相片编号删除相片数据
     *
     * @param cemeteryId 陵园编号
     * @param photoId    相片编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/del")
    @ResponseBody
    public ServerResponse del(String cemeteryId, String photoId) throws EqianyuanException {
        websitePhotoService.del(cemeteryId, photoId);
        return new ServerResponse();
    }
}
