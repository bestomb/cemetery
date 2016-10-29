package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.memberRoleRelate.MemberRoleRelateRequest;
import com.bestomb.common.response.MemberRoleRelate.MemberRoleRelateBo;

import java.util.List;

public interface IMemberRoleRelateService {
    /**
     * 获取数据集合
     *
     * @return
     */
    List<MemberRoleRelateBo> getList(String memberId) throws EqianyuanException;

    /**
     * 根据类型添加一条数据
     */
    void add(MemberRoleRelateRequest memberRoleRelateRequest) throws EqianyuanException;

}
