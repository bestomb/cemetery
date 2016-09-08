package com.bestomb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bestomb.common.Pager;
import com.bestomb.entity.LeaveMessage;

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
     * @param memberId
     * @param page
     * @return
     */
    int getReceivedMessageCount(int memberId);
    
    /***
     * 查询我（接收到）的留言分页列表
     * @param memberId
     * @param page
     * @return
     */
    List<LeaveMessage> getReceivedMessage(int memberId, @Param("page") Pager page);
    
    /***
     * 查询我（发出）的留言列表总数
     * @param memberId
     * @param page
     * @return
     */
    int getPushedMessageCount(int memberId);
    
    /***
     * 查询我（发出）的留言分页列表
     * @param memberId
     * @param page
     * @return
     */
    List<LeaveMessage> getPushedMessage(int memberId, @Param("page") Pager page);
    
}