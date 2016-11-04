package com.bestomb.dao;

import com.bestomb.common.Page;
import com.bestomb.entity.MemberAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
    List<MemberAccount> selectByPagination(@Param("page") Page page, @Param("type") String type);

    /**
     * 根据数据对象获取总条数
     */
    Long countByPagination(@Param("type") String type);

    /**
     * 根据登录信息查询数据对象
     *
     * @param loginAccount
     * @param loginPassword
     * @return
     */
    MemberAccount selectByLogin(@Param("login_account") String loginAccount, @Param("login_password") String loginPassword);

    int updateByPrimaryKeySelective(MemberAccount record);

    /**
     * 购物后修改账户信息，对购物消费的积分和交易币进行扣减
     *
     * @param tradingAmount 支付的交易币
     * @param integral      支付的积分
     * @param memberId      会员编号
     * @return
     */
    int updateByGoodsBuy(@Param("tradingAmount") Double tradingAmount, @Param("integral") int integral, @Param("memberId") Integer memberId);

    /***
     * 编辑会员资料
     *
     * @param record
     * @return
     */
    int memberEdit(MemberAccount record);

    /**
     * 更新交易币
     *
     * @param tradingAmount 交易币金额
     * @param memberId      会员编号
     * @return
     */
    int updateTradingAmount(@Param("tradingAmount") Double tradingAmount, @Param("memberId") Integer memberId);

    /**
     * 根据会员编号集合查询会员数据集合
     *
     * @param memberIds
     * @return
     */
    List<MemberAccount> selectByMemberIds(@Param("member_ids") List<String> memberIds);

    /**
     * 查询全部会员信息
     *
     * @return
     */
    List<MemberAccount> selectAll();

    /**
     * 根据会员商品销售情况批量更新会员账户交易币信息，将营收交易币叠加到账户
     *
     * @param memberSaleList
     * @return
     */
    int batchUpdateBySale(@Param("memberSaleList") List<Map<String, Object>> memberSaleList);
}