package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.lifeWorksCollection.LifeWorksCollectionEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.ILifeWorksCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jason on 2016-10-18.
 */
@Service
public class WebsiteLifeWorksCollectionService {

    @Autowired
    private ILifeWorksCollectionService lifeWorksCollectionService;

    /**
     * 创建纪念人作品集
     *
     * @param lifeWorksCollectionEditRequest
     * @throws EqianyuanException
     */
    public void add(LifeWorksCollectionEditRequest lifeWorksCollectionEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        lifeWorksCollectionEditRequest.setMemberId(memberLoginVo.getMemberId());
        lifeWorksCollectionService.add(lifeWorksCollectionEditRequest);
    }

    /**
     * 根据作品集编号删除数据
     *
     * @param cemeteryId            陵园编号
     * @param lifeWorksCollectionId 作品集编号
     * @throws EqianyuanException
     */
    public void del(String cemeteryId, String lifeWorksCollectionId) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        lifeWorksCollectionService.del(lifeWorksCollectionId, cemeteryId, memberLoginVo.getMemberId());
    }

    /**
     * 根据纪念人及分页信息查询作品集分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String masterId, Pager page) throws EqianyuanException {
        return lifeWorksCollectionService.getList(masterId, page);
    }
}
