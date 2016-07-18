package com.bestomb.controller.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.cemetery.CemeteryByAreaListRequest;
import com.bestomb.common.request.cemetery.CemeteryByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.cemetery.CemeteryByMineVo;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsiteCemeteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 前台陵园接口控制器
 * Created by jason on 2016-07-15.
 */
@Controller
@RequestMapping("/website/cemetery_api")
public class WebsiteCemeteryController extends BaseController {

    @Autowired
    private WebsiteCemeteryService websiteCemeteryService;

    /**
     * 创建陵园
     *
     * @param cemeteryByEditRequest
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public ServerResponse create(CemeteryByEditRequest cemeteryByEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByCookie()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        cemeteryByEditRequest.setMemberId(String.valueOf(memberLoginVo.getMemberId()));
        websiteCemeteryService.create(cemeteryByEditRequest);
        return new ServerResponse();
    }

    /**
     * 根据地区信息查询陵园分页集合
     *
     * @param cemeteryByAreaListRequest 陵园地区查询对象
     * @param pageNo                    分页页码
     * @param pageSize                  分页条数    默认64条每页
     * @return
     */
    @RequestMapping("/getListByArea")
    @ResponseBody
    public ServerResponse getListByArea(CemeteryByAreaListRequest cemeteryByAreaListRequest,
                                        @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "64") int pageSize) throws EqianyuanException {
        PageResponse pageResponse = websiteCemeteryService.getListByArea(cemeteryByAreaListRequest, pageNo, pageSize);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 根据陵园编号查询陵园归属地陵园分页集合
     *
     * @param cemeteryId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("/getListByCemeteryId")
    @ResponseBody
    public ServerResponse getListByCemetery(String cemeteryId,
                                            @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "64") int pageSize) throws EqianyuanException {
        PageResponse pageResponse = websiteCemeteryService.getListByCemetery(cemeteryId, pageNo, pageSize);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 任意门
     * 根据社区编号及行为获取左邻右里社区
     *
     * @param cemeteryByAreaListRequest 陵园地区查询对象
     * @param behavior                  行为（上一个=prev、下一个=next）
     * @param pageNo                    分页页码
     * @param pageSize                  分页条数    默认64条每页
     * @return
     */
    @RequestMapping("/arbitraryDoor")
    @ResponseBody
    public ServerResponse arbitraryDoor(CemeteryByAreaListRequest cemeteryByAreaListRequest, String behavior,
                                        String pageNo,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "64") int pageSize) throws EqianyuanException {
        PageResponse pageResponse = websiteCemeteryService.arbitraryDoor(cemeteryByAreaListRequest, behavior, pageNo, pageSize);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /**
     * 获取我的陵园集合
     *
     * @return
     */
    @RequestMapping("/getMineList")
    @ResponseBody
    public ServerResponse getMineList() throws EqianyuanException {
        List<CemeteryByMineVo> cemeteryByMineVos = websiteCemeteryService.getMineList();
        return new ServerResponse.ResponseBuilder().data(cemeteryByMineVos).build();
    }
}
