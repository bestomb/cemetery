package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.lifeWorksCollection.lifeWorks.LifeWorksEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.lifeWorksCollection.lifeWorks.LifeWorksBo;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.ILifeWorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jason on 2016-10-18.
 */
@Service
public class WebsiteLifeWorksService {

    @Autowired
    private ILifeWorksService lifeWorksService;

    /**
     * 为作品集添加作品
     *
     * @param lifeWorksEditRequest
     * @throws EqianyuanException
     */
    public void add(LifeWorksEditRequest lifeWorksEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        lifeWorksEditRequest.setMemberId(memberLoginVo.getMemberId());
        lifeWorksService.add(lifeWorksEditRequest);
    }

    /**
     * 根据作品编号删除作品数据
     *
     * @param cemeteryId  陵园编号
     * @param lifeWorksId 作品编号
     * @throws EqianyuanException
     */
    public void del(String cemeteryId, String lifeWorksId) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        lifeWorksService.del(lifeWorksId, cemeteryId, memberLoginVo.getMemberId());
    }

    /**
     * 根据作品及编号编号查询作品分页集合
     *
     * @param lifeWorksCollectionId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String lifeWorksCollectionId, Pager page) throws EqianyuanException {
        return lifeWorksService.getList(lifeWorksCollectionId, page);
    }

    /***
     * 根据作品编号查询作品详情
     *
     * @param lifeWorksId
     * @return
     * @throws EqianyuanException
     */
    public LifeWorksBo getInfo(String lifeWorksId) throws EqianyuanException {
        LifeWorksBo lifeWorksBo = lifeWorksService.getInfo(lifeWorksId);
        return lifeWorksBo;
    }
}
