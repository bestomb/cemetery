package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.memorialRecord.MemorialRecordEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.IMemorialRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jason on 2016-10-18.
 */
@Service
public class WebsiteMemorialRecordService {

    @Autowired
    private IMemorialRecordService memorialRecordService;

    /**
     * 保存纪念记录
     *
     * @param memorialRecordEditRequest
     * @throws EqianyuanException
     */
    public void add(MemorialRecordEditRequest memorialRecordEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        memorialRecordEditRequest.setMemberId(memberLoginVo.getMemberId());
        memorialRecordService.add(memorialRecordEditRequest);
    }

    /**
     * 根据纪念人编号分页查询纪念人纪念记录
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String masterId, Pager page) throws EqianyuanException {
        return memorialRecordService.getList(masterId, page);
    }
}
