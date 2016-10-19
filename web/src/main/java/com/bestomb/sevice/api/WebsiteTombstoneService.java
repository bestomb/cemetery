package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.TombstoneEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.entity.Tombstone;
import com.bestomb.service.ITombstoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jason on 2016-10-18.
 */
@Service
public class WebsiteTombstoneService {

    @Autowired
    private ITombstoneService tombstoneService;

    /**
     * 创建陵园墓碑
     *
     * @param tombstoneEditRequest
     * @throws EqianyuanException
     */
    public void addTombstone(TombstoneEditRequest tombstoneEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        tombstoneEditRequest.setMemberId(memberLoginVo.getMemberId());
        tombstoneService.addTombstone(tombstoneEditRequest);
    }

    /**
     * 修改陵园墓碑
     *
     * @param tombstoneEditRequest
     * @throws EqianyuanException
     */
    public void modifyTombstone(TombstoneEditRequest tombstoneEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        tombstoneEditRequest.setMemberId(memberLoginVo.getMemberId());
        tombstoneService.modifyTombstone(tombstoneEditRequest);
    }

    /**
     * 删除陵园墓碑
     *
     * @param cemeteryId  陵园编号
     * @param tombstoneId 墓碑编号
     * @throws EqianyuanException
     */
    public void delTombstone(String cemeteryId, String tombstoneId) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        tombstoneService.delTombstone(tombstoneId, cemeteryId, memberLoginVo.getMemberId());
    }

    /**
     * 根据条件查询陵园墓碑分页集合
     *
     * @param tombstone
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getListByCondition(Tombstone tombstone, Pager page) throws EqianyuanException {
        return tombstoneService.getListByCondition(tombstone, page);
    }
}
