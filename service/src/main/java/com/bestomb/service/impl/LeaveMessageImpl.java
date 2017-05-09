package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.leaveMessage.LeaveMessageEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.LeaveMessageBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.ILeaveMessageDao;
import com.bestomb.dao.IMemberAccountDao;
import com.bestomb.entity.LeaveMessage;
import com.bestomb.entity.MemberAccount;
import com.bestomb.service.ILeaveMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***
 * 留言接口实现类
 *
 * @author qfzhang
 */
@Service
public class LeaveMessageImpl implements ILeaveMessage {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ILeaveMessageDao leaveMessageDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private IMemberAccountDao memberAccountDao;

    /***
     * 查询我（接收到）的留言
     *
     * @param memberId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getReceivedMessage(Integer memberId, Pager page) throws EqianyuanException {
        // 会员编号是否为空
        if (ObjectUtils.isEmpty(memberId)) {
            logger.warn("getMyMessage fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        // 先查询总数
        int dataCount = leaveMessageDao.getReceivedMessageCount(memberId);
        if (dataCount == 0) {
            logger.info("根据条件查询我（接收到）的留言无数据l");
            return new PageResponse(page, null);
        }
        page.setTotalRow(dataCount);
        // 再查询分页数据
        List<LeaveMessage> messages = leaveMessageDao.getReceivedMessage(memberId, page);
        if (CollectionUtils.isEmpty(messages)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询我（接收到）的留言无数据l");
            return new PageResponse(page, null);
        }

        List<String> memberIds = new ArrayList<String>();
        for (LeaveMessage entity : messages) {
            memberIds.add(String.valueOf(entity.getMemberId()));
        }

        //根据会员编号查询获取会员昵称
        List<MemberAccount> memberAccounts = memberAccountDao.selectByMemberIds(memberIds);

        // 使用BO返回
        List<LeaveMessageBo> resultList = new ArrayList<LeaveMessageBo>();
        for (LeaveMessage entity : messages) {
            LeaveMessageBo bo = new LeaveMessageBo();
            BeanUtils.copyProperties(entity, bo);
            bo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(entity.getCreateTime())); // 转化创建时间

            if (!CollectionUtils.isEmpty(memberAccounts)) {
                for (MemberAccount memberAccount : memberAccounts) {
                    if (entity.getMemberId().equals(memberAccount.getMemberId())) {
                        bo.setMemberNickName(memberAccount.getNickName());
                        break;
                    }
                }
            }
            resultList.add(bo);
        }

        return new PageResponse(page, resultList);
    }

    /***
     * 查询我（发出）的留言
     *
     * @param memberId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getPushedMessage(Integer memberId, Pager page) throws EqianyuanException {
        // 会员编号是否为空
        if (ObjectUtils.isEmpty(memberId)) {
            logger.warn("getMyMessage fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
        // 先查询总数
        int dataCount = leaveMessageDao.getPushedMessageCount(memberId);
        if (dataCount == 0) {
            logger.info("根据条件查询我（发出）的留言无数据l");
            return new PageResponse(page, null);
        }
        page.setTotalRow(dataCount);
        // 再查询分页数据
        List<LeaveMessage> messages = leaveMessageDao.getPushedMessage(memberId, page);
        if (CollectionUtils.isEmpty(messages)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询我（发出）的留言无数据l");
            return new PageResponse(page, null);
        }

        List<String> memberIds = new ArrayList<String>();
        for (LeaveMessage entity : messages) {
            memberIds.add(String.valueOf(entity.getMemberId()));
        }

        //根据会员编号查询获取会员昵称
        List<MemberAccount> memberAccounts = memberAccountDao.selectByMemberIds(memberIds);

        // 使用BO返回
        List<LeaveMessageBo> resultList = new ArrayList<LeaveMessageBo>();
        for (LeaveMessage entity : messages) {
            LeaveMessageBo bo = new LeaveMessageBo();
            BeanUtils.copyProperties(entity, bo);
            bo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(entity.getCreateTime())); // 转化创建时间

            if (!CollectionUtils.isEmpty(memberAccounts)) {
                for (MemberAccount memberAccount : memberAccounts) {
                    if (entity.getMemberId().equals(memberAccount.getMemberId())) {
                        bo.setMemberNickName(memberAccount.getNickName());
                        break;
                    }
                }
            }
            resultList.add(bo);
        }

        return new PageResponse(page, resultList);
    }


    /***
     * 删除留言
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    public boolean deleteByPrimaryKey(String id) throws EqianyuanException {
        // 留言ID是否为空
        if (StringUtils.isEmpty(id)) {
            logger.warn("deleteByPrimaryKey fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MESSAGEID_IS_EMPTY);
        }
        // 留言是否存在
        if (leaveMessageDao.selectByPrimaryKey(id) == null) {
            logger.warn("deleteByPrimaryKey fail , because messageId is not exsit.");
            throw new EqianyuanException(ExceptionMsgConstant.MESSAGEID_IS_NOT_EXSITS);
        }
        return leaveMessageDao.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 根据陵园墓碑纪念人编号查询墓中纪念人留言分页集合
     *
     * @param masterId 纪念人编号
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getLeaveMessageByMaster(String masterId, Pager page) throws EqianyuanException {
        // 先查询总数
        int dataCount = leaveMessageDao.countByMasterId(masterId);
        if (dataCount == 0) {
            logger.info("根据条件查询纪念人的留言无数据");
            return new PageResponse(page, null);
        }
        page.setTotalRow(dataCount);
        // 再查询分页数据
        List<LeaveMessage> messages = leaveMessageDao.getPagingByMasterId(masterId, page);
        if (CollectionUtils.isEmpty(messages)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询纪念人的留言无数据");
            return new PageResponse(page, null);
        }

        List<String> memberIds = new ArrayList<String>();
        for (LeaveMessage entity : messages) {
            memberIds.add(String.valueOf(entity.getMemberId()));
        }

        //根据会员编号查询获取会员昵称
        List<MemberAccount> memberAccounts = memberAccountDao.selectByMemberIds(memberIds);

        // 使用BO返回
        List<LeaveMessageBo> resultList = new ArrayList<LeaveMessageBo>();
        for (LeaveMessage entity : messages) {
            LeaveMessageBo bo = new LeaveMessageBo();
            BeanUtils.copyProperties(entity, bo);
            bo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(entity.getCreateTime())); // 转化创建时间

            if (!CollectionUtils.isEmpty(memberAccounts)) {
                for (MemberAccount memberAccount : memberAccounts) {
                    if (entity.getMemberId().equals(memberAccount.getMemberId())) {
                        bo.setMemberNickName(memberAccount.getNickName());
                        break;
                    }
                }
            }
            resultList.add(bo);
        }

        return new PageResponse(page, resultList);
    }

    /**
     * 根据陵园墓碑纪念人留言编号查询墓中纪念人留言回复集合
     *
     * @param leaveMessageId
     * @return
     * @throws EqianyuanException
     */
    public List<LeaveMessageBo> getReplyLeaveMessageByMaster(String leaveMessageId) throws EqianyuanException {
        List<LeaveMessage> messages = leaveMessageDao.getReplyMessage(leaveMessageId);
        if (CollectionUtils.isEmpty(messages)) {
            logger.info("根据条件查询纪念人的留言的回复留言无数据");
            return Collections.EMPTY_LIST;
        }

        List<String> memberIds = new ArrayList<String>();
        for (LeaveMessage entity : messages) {
            memberIds.add(String.valueOf(entity.getMemberId()));
        }

        //根据会员编号查询获取会员昵称
        List<MemberAccount> memberAccounts = memberAccountDao.selectByMemberIds(memberIds);

        // 使用BO返回
        List<LeaveMessageBo> resultList = new ArrayList<LeaveMessageBo>();
        for (LeaveMessage entity : messages) {
            LeaveMessageBo bo = new LeaveMessageBo();
            BeanUtils.copyProperties(entity, bo);
            bo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(entity.getCreateTime())); // 转化创建时间

            if (!CollectionUtils.isEmpty(memberAccounts)) {
                for (MemberAccount memberAccount : memberAccounts) {
                    if (entity.getMemberId().equals(memberAccount.getMemberId())) {
                        bo.setMemberNickName(memberAccount.getNickName());
                        break;
                    }
                }
            }
            resultList.add(bo);
        }
        return resultList;
    }

    /**
     * 添加陵园墓碑纪念人留言
     *
     * @param leaveMessageEditRequest
     * @throws EqianyuanException
     */
    public void addLeaveMessage(LeaveMessageEditRequest leaveMessageEditRequest) throws EqianyuanException {
        //构建持久化纪念人留言数据
        LeaveMessage leaveMessage = new LeaveMessage();
        leaveMessage.setMemberId(leaveMessageEditRequest.getMemberId());
        leaveMessage.setMasterId(leaveMessageEditRequest.getMasterId());
        leaveMessage.setContent(leaveMessageEditRequest.getContent());
        leaveMessage.setCreateTime(CalendarUtil.getSystemSeconds());
        //持久化纪念人留言数据
        leaveMessageDao.insertSelective(leaveMessage);
    }

    /**
     * 回复陵园墓碑纪念人留言
     *
     * @param leaveMessageEditRequest
     * @throws EqianyuanException
     */
    public void replyLeaveMessage(LeaveMessageEditRequest leaveMessageEditRequest) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(String.valueOf(leaveMessageEditRequest.getCemeteryId()), leaveMessageEditRequest.getMemberId());

        //构建持久化纪念人留言回复数据
        LeaveMessage leaveMessage = new LeaveMessage();
        leaveMessage.setMemberId(leaveMessageEditRequest.getMemberId());
        leaveMessage.setMasterId(leaveMessageEditRequest.getMasterId());
        leaveMessage.setContent(leaveMessageEditRequest.getContent());
        leaveMessage.setReplyId(leaveMessageEditRequest.getReplyId());
        leaveMessage.setCreateTime(CalendarUtil.getSystemSeconds());
        //持久化纪念人留言回复数据
        leaveMessageDao.insertSelective(leaveMessage);
    }

}
