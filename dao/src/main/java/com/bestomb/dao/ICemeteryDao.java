package com.bestomb.dao;

import com.bestomb.entity.Cemetery;
import org.apache.ibatis.annotations.Param;

public interface ICemeteryDao {
    int deleteByPrimaryKey(String id);

    int insertSelective(Cemetery record);

    Cemetery selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Cemetery record);

    /**
     * 根据会员编号获取陵园总数
     *
     * @return
     */
    Long countByMemberId(@Param("member_id") String memberId);
}