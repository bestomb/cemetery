package com.bestomb.dao;

import com.bestomb.common.Pager;
import com.bestomb.entity.MemberAuthorization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IMemberAuthorizationDao {
    int deleteByPrimaryKey(String id);

    int insert(MemberAuthorization record);

    int insertSelective(MemberAuthorization record);

    MemberAuthorization selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MemberAuthorization record);

    int updateByPrimaryKey(MemberAuthorization record);

    /**
     * 根据陵园编号查询集合
     *
     * @param cemeteryIds
     * @return
     */
    List<MemberAuthorization> selectByCemeteryId(@Param("cemetery_ids") List<Integer> cemeteryIds);
    
    /***
     * 根据陵园编号分页查询集合
     * @param cemeteryIds
     * @param page
     * @return
     */
    List<MemberAuthorization> selectPageListByCemeteryId(@Param("cemetery_ids") List<Integer> cemeteryIds, @Param("page")  Pager page);
    
}