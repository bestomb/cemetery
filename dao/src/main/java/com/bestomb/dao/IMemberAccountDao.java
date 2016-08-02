package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.entity.MemberAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMemberAccountDao {
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
     *
     * @param inviterId
     * @return
     */
    int selectByInviterId(@Param("inviter_id") Integer inviterId);

    /**
     * 分页查询
     */
    List<MemberAccount> selectByPagination( @Param("page") Page page);

    /**
     * 根据数据对象获取总条数
     */
    Long countByPagination();

    /**
     * 根据登录信息查询数据对象
     *
     * @param loginAccount
     * @param loginPassword
     * @return
     */
    MemberAccount selectByLogin(@Param("login_account") String loginAccount, @Param("login_password") String loginPassword);

    int updateByPrimaryKeySelective(MemberAccount record);
}