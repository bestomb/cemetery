package com.bestomb.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.controller.BaseController;
import com.bestomb.entity.Music;
import com.bestomb.sevice.api.WebsiteMusicService;

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
     * 根据条件查询音乐分页集合
     * @param music
     * @param page
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getListByCondition")
    @ResponseBody
    public ServerResponse getListByCondition(@ModelAttribute Music music, @ModelAttribute Pager page) throws EqianyuanException {
    	music.setType(2); // 查询本地会员音乐
        PageResponse pageResponse = websiteMusicService.getListByCondition(music, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }
    
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
        //todo 询问结果，附件随表单内容一并上传，附件为二进制流
        return new ServerResponse();
    }
}
