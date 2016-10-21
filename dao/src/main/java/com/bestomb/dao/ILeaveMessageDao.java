package com.bestomb.dao;

import com.bestomb.common.Pager;
import com.bestomb.entity.LeaveMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ILeaveMessageDao {

    int deleteByPrimaryKey(String id);

    int insert(LeaveMessage record);

    int insertSelective(LeaveMessage record);

    LeaveMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LeaveMessage record);

    int updateByPrimaryKeyWithBLOBs(LeaveMessage record);

    int updateByPrimaryKey(LeaveMessage record);

    /***
     * 查询我（接收到）的留言列表总数
     *
     * @param memberId
     * @param page
     * @return
     */
    int getReceivedMessageCount(int memberId);

    /***
     * 查询我（接收到）的留言分页列表
     *
     * @param memberId
     * @param page
     * @return
     */
    List<LeaveMessage> getReceivedMessage(@Param("memberId") int memberId, @Param("page") Pager page);

    /***
     * 查询我（发出）的留言列表总数
     *
     * @param memberId
     * @param page
     * @return
     */
    int getPushedMessageCount(int memberId);

    /***
     * 查询我（发出）的留言分页列表
     *
     * @param memberId
     * @param page
     * @return
     */
    List<LeaveMessage> getPushedMessage(@Param("memberId") int memberId, @Param("page") Pager page);

    /**
     * 根据纪念人编号查询对纪念人的留言数据总数（不包含回复数据）
     *
     * @param masterId
     * @return
     */
    int countByMasterId(@Param("masterId") String masterId);

    /**
     * 根据纪念人编号查询对纪念人的留言分页集合
     *
     * @param masterId
     * @return
     */
    List<LeaveMessage> getPagingByMasterId(@Param("masterId") String masterId, @Param("page") Pager page);

    /**
     * 根据纪念人留言编号查询留言回复集合
     *
     * @param leaveMessageId
     * @return
     */
    List<LeaveMessage> getReplyMessage(@Param("leaveMessageId") String leaveMessageId);

}