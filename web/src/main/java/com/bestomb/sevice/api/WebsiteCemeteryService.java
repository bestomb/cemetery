package com.bestomb.sevice.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.cemetery.CemeteryByAreaListRequest;
import com.bestomb.common.request.cemetery.CemeteryByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.cemetery.CemeteryBo;
import com.bestomb.common.response.cemetery.CemeteryByAreaVo;
import com.bestomb.common.response.cemetery.CemeteryByMineVo;
import com.bestomb.common.response.cemetery.CemeteryVo;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.ICemeteryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 网站陵园业务调用类
 * Created by jason on 2016-07-15.
 */
@Service
public class WebsiteCemeteryService {

    @Autowired
    private ICemeteryService cemeteryService;

    /**
     * 创建陵园
     *
     * @param cemeteryByEditRequest
     */
    public void create(CemeteryByEditRequest cemeteryByEditRequest) throws EqianyuanException {
        cemeteryService.add(cemeteryByEditRequest);
    }

    /**
     * 根据地区信息及分页信息查找陵园集合
     *
     * @param cemeteryByAreaListRequest 地区信息
     * @param pageNo                    分页页码
     * @param pageSize                  每页显示条数
     * @return
     */
    public PageResponse getListByArea(CemeteryByAreaListRequest cemeteryByAreaListRequest, int pageNo, int pageSize) throws EqianyuanException {
        PageResponse pageResponse = cemeteryService.getListByArea(cemeteryByAreaListRequest, pageNo, pageSize);
        List<CemeteryBo> cemeteryBos = (List<CemeteryBo>) pageResponse.getList();
        if (!CollectionUtils.isEmpty(cemeteryBos)) {
            setListByPageResponse(pageResponse, cemeteryBos);
        }
        return pageResponse;
    }

    /**
     * 根据陵园编号查询陵园归属地陵园分页集合
     *
     * @param cemeteryId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageResponse getListByCemetery(String cemeteryId, int pageNo, int pageSize) throws EqianyuanException {
        PageResponse pageResponse = cemeteryService.getListByCemeteryId(cemeteryId, pageNo, pageSize);
        List<CemeteryBo> cemeteryBos = (List<CemeteryBo>) pageResponse.getList();
        if (!CollectionUtils.isEmpty(cemeteryBos)) {
            setListByPageResponse(pageResponse, cemeteryBos);
        }
        return pageResponse;
    }

    /**
     * 任意门
     *
     * @param cemeteryByAreaListRequest
     * @param behavior
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    public PageResponse arbitraryDoor(CemeteryByAreaListRequest cemeteryByAreaListRequest, String behavior, String pageNo, int pageSize) throws EqianyuanException {
        PageResponse pageResponse = cemeteryService.getListByArbitraryDoor(cemeteryByAreaListRequest, behavior, pageNo, pageSize);
        List<CemeteryBo> cemeteryBos = (List<CemeteryBo>) pageResponse.getList();
        if (!CollectionUtils.isEmpty(cemeteryBos)) {
            setListByPageResponse(pageResponse, cemeteryBos);
        }
        return pageResponse;
    }

    /**
     * 获取我的陵园集合
     *
     * @return
     */
    public List<CemeteryByMineVo> getMineList() throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByCookie()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        List<CemeteryBo> cemeteryBos = cemeteryService.getListByMemberId(memberLoginVo.getMemberId());
        List<CemeteryByMineVo> cemeteryByMineVos = new ArrayList<CemeteryByMineVo>();
        for (CemeteryBo cemeteryBo : cemeteryBos) {
            CemeteryByMineVo cemeteryByMineVo = new CemeteryByMineVo();
            BeanUtils.copyProperties(cemeteryBo, cemeteryByMineVo);
            cemeteryByMineVos.add(cemeteryByMineVo);
        }
        return cemeteryByMineVos;
    }

    /**
     * 根据陵园编号查找陵园信息
     *
     * @param cemeteryId
     * @return
     */
    public CemeteryVo getInfoById(String cemeteryId) throws EqianyuanException {
        CemeteryBo cemeteryBo = cemeteryService.getInfoById(cemeteryId);
        CemeteryVo cemeteryVo = new CemeteryVo();
        BeanUtils.copyProperties(cemeteryBo, cemeteryVo);
        return cemeteryVo;
    }

    /**
     * 进入陵园内部
     *
     * @param cemeteryId
     * @param enterPwd
     */
    public void enterCemetery(String cemeteryId, String enterPwd) throws EqianyuanException {
        cemeteryService.checkAccessPassword(cemeteryId, enterPwd);
    }

    /**
     * 设置分页返回对象数据
     *
     * @param pageResponse
     * @param cemeteryBos
     */
    private void setListByPageResponse(PageResponse pageResponse, List<CemeteryBo> cemeteryBos) {
        List<CemeteryByAreaVo> cemeteryByAreaVos = new ArrayList<CemeteryByAreaVo>();
        for (CemeteryBo cemeteryBo : cemeteryBos) {
            CemeteryByAreaVo cemeteryByAreaVo = new CemeteryByAreaVo();
            BeanUtils.copyProperties(cemeteryBo, cemeteryByAreaVo);
            cemeteryByAreaVos.add(cemeteryByAreaVo);
        }

        pageResponse.setList(cemeteryByAreaVos);
    }
}
