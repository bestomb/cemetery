package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.leaveMessage.LeaveMessageEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.LeaveMessageBo;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.ILeaveMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jason on 2016-10-18.
 */
@Service
public class WebsiteLeaveMessageService {

    @Autowired
    private ILeaveMessage leaveMessage;

    /**
     * 添加陵园墓碑纪念人留言
     *
     * @param leaveMessageEditRequest
     * @throws EqianyuanException
     */
    public void addLeaveMessage(LeaveMessageEditRequest leaveMessageEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        leaveMessageEditRequest.setMemberId(memberLoginVo.getMemberId());
        leaveMessage.addLeaveMessage(leaveMessageEditRequest);
    }

    /**
     * 添加陵园墓碑纪念人留言
     *
     * @param leaveMessageEditRequest
     * @throws EqianyuanException
     */
    public void replyLeaveMessage(LeaveMessageEditRequest leaveMessageEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        leaveMessageEditRequest.setMemberId(memberLoginVo.getMemberId());
        leaveMessage.replyLeaveMessage(leaveMessageEditRequest);
    }

    /**
     * 根据陵园墓碑纪念人编号查询墓中纪念人留言分页集合
     *
     * @param masterId
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getLeaveMessageByMaster(String masterId, Pager page) throws EqianyuanException {
        return leaveMessage.getLeaveMessageByMaster(masterId, page);
    }

    /**
     * 根据陵园墓碑纪念人留言编号查询墓中纪念人留言回复集合
     *
     * @param leaveMessageId 纪念人留言编号
     * @return
     * @throws EqianyuanException
     */
    public List<LeaveMessageBo> getReplyLeaveMessageByMaster(String leaveMessageId) throws EqianyuanException {
        return leaveMessage.getReplyLeaveMessageByMaster(leaveMessageId);
    }
}
