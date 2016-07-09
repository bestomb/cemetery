package com.bestomb.service;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.systemRole.SystemRoleByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.systemRole.SystemRoleBo;

import java.util.List;

/**
 * 系统角色业务接口
 * Created by jason on 2016-07-08.
 */
public interface ISystemRoleService {

    /**
     * 获取数据集合
     *
     * @return
     */
    List<SystemRoleBo> getList() throws EqianyuanException;

    /**
     * 根据序列编号删除数据
     *
     * @param id
     */
    void removeByIds(String... id) throws EqianyuanException;

    /**
     * 插入对象数据
     *
     * @param systemRoleByEditRequest
     */
    void add(SystemRoleByEditRequest systemRoleByEditRequest) throws EqianyuanException;

    /**
     * 更新对象数据
     *
     * @param systemRoleByEditRequest
     */
    void modify(SystemRoleByEditRequest systemRoleByEditRequest) throws EqianyuanException;

    /**
     * 根据主键编号查询数据对象
     *
     * @param id
     * @return
     */
    SystemRoleBo queryById(String id) throws EqianyuanException;

    /**
     * 根据对象及分页条件获取分页数据集合
     *
     * @return
     */
    PageResponse queryByPagination(int pageNo, int pageSize);
}
