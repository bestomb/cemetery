package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.entity.MemberAuthorization;

import java.util.List;

/***
 * 权限接口（陵园、会员等）
 *
 * @author qfzhang
 */
public interface IAuthService {

    /***
     * 授权陵园给会员
     *
     * @param MemberAuthorization {陵园ID、会员ID必填}
     * @return
     * @throws EqianyuanException
     */
    public boolean authCemeteryToMember(MemberAuthorization record) throws EqianyuanException;

    /***
     * 根据陵园ID集合获取已授权会员分页列表
     *
     * @param cemeteryIds
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public List<MemberAuthorization> getAuthMembersPageList(List<Integer> cemeteryIds, Pager page) throws EqianyuanException;

    /***
     * 回收给会员代管理陵园的权限
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    public boolean removeCemeteryAuthToMember(String id) throws EqianyuanException;

    /**
     * 检查当前系统登录会员是否拥有对陵园的管理权限
     *
     * @param cemeteryId
     * @param memberId
     * @return
     */
    boolean hasOperationAuth(String cemeteryId, Integer memberId);
}
