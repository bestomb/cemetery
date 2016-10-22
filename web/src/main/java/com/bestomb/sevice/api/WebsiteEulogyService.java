package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.eulogy.EulogyEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.eulogy.EulogyBo;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.IEulogyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jason on 2016-10-18.
 */
@Service
public class WebsiteEulogyService {

    @Autowired
    private IEulogyService eulogyService;

    /**
     * 添加祭文悼词
     *
     * @param eulogyEditRequest
     * @throws EqianyuanException
     */
    public void add(EulogyEditRequest eulogyEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        eulogyEditRequest.setMemberId(memberLoginVo.getMemberId());
        eulogyService.add(eulogyEditRequest);
    }

    /**
     * 根据纪念人编号查询祭文悼词分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String masterId, Pager page) throws EqianyuanException {
        return eulogyService.getList(masterId, page);
    }

    /**
     * 根据祭文悼词编号查询详情
     *
     * @param eulogyId
     * @return
     * @throws EqianyuanException
     */
    public EulogyBo getInfo(String eulogyId) throws EqianyuanException {
        return eulogyService.getInfo(eulogyId);
    }
}
