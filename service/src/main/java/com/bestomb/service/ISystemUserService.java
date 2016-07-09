package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.systemUser.SystemUserByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.systemUser.SystemUserBo;

/**
 * 系统用户管理业务逻辑
 * Created by jason on 2016-07-06.
 */
public interface ISystemUserService {

    /**
     * 根据序列编号删除数据
     *
     * @param id
     */
    void removeByIds(String... id) throws EqianyuanException;

    /**
     * 插入对象数据
     *
     * @param systemUserByEditRequest
     */
    void add(SystemUserByEditRequest systemUserByEditRequest) throws EqianyuanException;

    /**
     * 更新对象数据
     *
     * @param systemUserByEditRequest
     */
    void modify(SystemUserByEditRequest systemUserByEditRequest) throws EqianyuanException;

    /**
     * 根据主键编号查询数据对象
     *
     * @param id
     * @return
     */
    SystemUserBo queryById(String id) throws EqianyuanException;

    /**
     * 根据对象及分页条件获取分页数据集合
     *
     * @return
     */
    PageResponse queryByPagination(int pageNo, int pageSize, String roleId);

    /**
     * 带验证码的登录
     *
     * @param loginName     用户名
     * @param loginPassword 密码
     * @param code          登录验证码
     * @return 系统用户BO对象
     * @throws EqianyuanException
     */
    SystemUserBo login(String loginName, String loginPassword, String code) throws EqianyuanException;
}
