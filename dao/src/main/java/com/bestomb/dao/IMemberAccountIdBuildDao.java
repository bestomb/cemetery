package com.bestomb.dao;

import com.bestomb.entity.MemberAccountIdBuild;

public interface IMemberAccountIdBuildDao {
    int insertSelective(MemberAccountIdBuild record);

    int updateByPrimaryKeySelective(MemberAccountIdBuild record);
}