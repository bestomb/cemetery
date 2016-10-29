package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.leaveMessage.LeaveMessageEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.LeaveMessageBo;

import java.util.List;

/***
 * 祭祀留言接口
 *
 * @author admin
 */
public interface ILeaveMessage {

    /***
     * 查询我（接收到）的留言
     *
     * @param memberId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getReceivedMessage(Integer memberId, Pager page) throws EqianyuanException;

    /***
     * 查询我（发出）的留言
     *
     * @param memberId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getPushedMessage(Integer memberId, Pager page) throws EqianyuanException;

    /***
     * 删除留言
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    public boolean deleteByPrimaryKey(String id) throws EqianyuanException;

    /**
     * 根据陵园墓碑纪念人编号查询墓中纪念人留言分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    PageResponse getLeaveMessageByMaster(String masterId, Pager page) throws EqianyuanException;

    /**
     * 根据陵园墓碑纪念人留言编号查询墓中纪念人留言回复集合
     *
     * @param leaveMessageId
     * @return
     * @throws EqianyuanException
     */
    List<LeaveMessageBo> getReplyLeaveMessageByMaster(String leaveMessageId) throws EqianyuanException;

    /**
     * 添加陵园墓碑纪念人留言
     *
     * @param leaveMessageEditRequest
     * @throws EqianyuanException
     */
    void addLeaveMessage(LeaveMessageEditRequest leaveMessageEditRequest) throws EqianyuanException;

    /**
     * 回复陵园墓碑纪念人留言
     *
     * @param leaveMessageEditRequest
     * @throws EqianyuanException
     */
    void replyLeaveMessage(LeaveMessageEditRequest leaveMessageEditRequest) throws EqianyuanException;

}
