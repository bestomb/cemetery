package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.controller.BaseController;
import com.bestomb.entity.Music;
import com.bestomb.sevice.api.WebsiteMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 陵园背景音乐控制器
 * Created by jason on 2016-08-08.
 */
@Controller
@RequestMapping("/website/music_api")
public class WebsiteMusicController extends BaseController {

    @Autowired
    private WebsiteMusicService websiteMusicService;

    /***
     * 音乐删除
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/deleteById")
    @ResponseBody
    public ServerResponse deleteById(String id) throws EqianyuanException {
        boolean flag = websiteMusicService.deleteById(id);
        return new ServerResponse.ResponseBuilder().data(flag).build();
    }

    /***
     * 根据条件查询陵园音乐分页集合
     *
     * @param music
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getListByCondition")
    @ResponseBody
    public ServerResponse getListByCondition(@ModelAttribute Music music, @ModelAttribute Pager page) throws EqianyuanException {
        PageResponse pageResponse = websiteMusicService.getListByCondition(music, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 上传背景音乐
     *
     * @param musicFile  音乐文件二进制流
     * @param name       文件自定义名称
     * @param cemeteryId 所属陵园编号
     * @return
     */
    @RequestMapping("/uploadMusic")
    @ResponseBody
    public ServerResponse uploadMusic(MultipartFile musicFile,
                                      String name,
                                      String cemeteryId) throws EqianyuanException {
        websiteMusicService.uploadMusic(musicFile, name, cemeteryId);
        return new ServerResponse();
    }
}
