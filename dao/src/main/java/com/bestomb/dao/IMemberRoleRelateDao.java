package com.bestomb.dao;

import com.bestomb.entity.MemberRoleRelate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMemberRoleRelateDao {

    /**
     * 删除数据
     * @return
     */
    int deleteByPrimaryKey(@Param("memberId") String memberId, @Param("type") Integer type);

    /**
     * 添加一条数据
     * @param record
     * @return
     */
    int insert(MemberRoleRelate record);

    int insertSelective(MemberRoleRelate record);

    MemberRoleRelate selectByPrimaryKey(Integer memberId);

    /**
     * 根据memberid，type查询数据
     * @param memberId
     * @param type
     * @return
     */
    List<MemberRoleRelate> selectByPrimary(@Param("memberId") Integer memberId, @Param("type") Integer type);

    int updateByPrimaryKeySelective(MemberRoleRelate record);

    int updateByPrimaryKey(MemberRoleRelate record);

    /**
     * 查询所有数据集合
     *
     * @return
     */
    List<MemberRoleRelate> selectByList(Integer memmemberId);
}