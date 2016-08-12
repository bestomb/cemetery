package com.bestomb.controller.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsiteMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 陵园背景音乐控制器
 * Created by jason on 2016-08-08.
 */
@Controller
@RequestMapping("/website/music_api")
public class WebsiteMusicController extends BaseController {

    @Autowired
    private WebsiteMusicService websiteMusicService;

    /**
     * 根据陵园编号获取陵园背景音乐分页集合
     *
     * @param cemeteryId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getListByCemeteryId")
    @ResponseBody
    public ServerResponse getListByCemetery(String cemeteryId,
                                            @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws EqianyuanException {
        PageResponse pageResponse = websiteMusicService.getListByCemetery(cemeteryId, pageNo, pageSize);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 上传背景音乐
     *
     * @param name
     * @param fileAddress
     * @param cemeteryId
     * @return
     */
    @RequestMapping("/uploadMusic")
    @ResponseBody
    public ServerResponse uploadMusic(String name, String fileAddress, String cemeteryId) {
        //todo 询问U3D表单，是异步上传附件，还是随表单上传附件
        return new ServerResponse();
    }
}