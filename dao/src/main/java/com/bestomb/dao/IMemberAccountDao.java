package com.bestomb.dao;

import com.bestomb.entity.MemberAccount;
import org.apache.ibatis.annotations.Param;

public interface IMemberAccountDao {
    int deleteByPrimaryKey(Integer memberId);

    int insert(MemberAccount record);

    int insertSelective(MemberAccount record);

    MemberAccount selectByPrimaryKey(Integer memberId);

    /**
     * 根据手机号码查询数据数量
     *
     * @param mobile
     * @return
     */
    int selectByMobile(@Param("mobile_number") Long mobile);

    /**
     * 根据邀请者编号查询数据数量
     * @param inviterId
     * @return
     */
    int selectByInviterId(@Param("inviter_id") Integer inviterId);

    int updateByPrimaryKeySelective(MemberAccount record);

    int updateByPrimaryKey(MemberAccount record);
}