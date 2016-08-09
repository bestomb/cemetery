package com.bestomb.controller.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsiteNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 陵园公告控制器
 * Created by jason on 2016-08-09.
 */
@Controller
@RequestMapping("/website/notice_api")
public class WebsiteNoticeController extends BaseController {

    @Autowired
    private WebsiteNoticeService noticeService;

    /**
     * 添加公告
     *
     * @param content    公告内容
     * @param cemeteryId 陵园编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(String content, String cemeteryId) throws EqianyuanException {
        noticeService.add(content, cemeteryId);
        return new ServerResponse();
    }

    /**
     * 删除公告
     *
     * @param id 公告编号
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(String... id) throws EqianyuanException {
        noticeService.delete(id);
        return new ServerResponse();
    }

    /**
     * 修改公告
     *
     * @param id      公告编号
     * @param content 公告内容
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/modify")
    @ResponseBody
    public ServerResponse modify(String id, String content) throws EqianyuanException {
        noticeService.modify(id, content);
        return new ServerResponse();
    }

    /**
     * 分页查询公告内容数据集合
     *
     * @param pageNo     分页页码
     * @param pageSize   分页每页显示条数
     * @param cemeteryId 陵园编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/paginationList")
    @ResponseBody
    public ServerResponse getListByCemetery(@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                            String cemeteryId) throws EqianyuanException {
        PageResponse pageResponse = noticeService.getListByCemetery(cemeteryId, pageNo, pageSize);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

}
