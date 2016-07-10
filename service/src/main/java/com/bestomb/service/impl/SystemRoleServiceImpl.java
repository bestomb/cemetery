package com.bestomb.service.impl;

import com.bestomb.common.Page;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.systemRole.SystemRoleByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.systemRole.SystemRoleBo;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.ISystemRoleDao;
import com.bestomb.dao.ISystemRoleMenuRelateDao;
import com.bestomb.dao.ISystemUserRoleRelateDao;
import com.bestomb.entity.SystemRole;
import com.bestomb.entity.SystemRoleMenuRelate;
import com.bestomb.service.ISystemRoleService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单业务逻辑实现类
 * Created by jason on 2016-07-08.
 */
@Service
public class SystemRoleServiceImpl implements ISystemRoleService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ISystemRoleDao systemRoleDao;

    @Autowired
    private ISystemUserRoleRelateDao systemUserRoleRelateDao;

    @Autowired
    private ISystemRoleMenuRelateDao systemRoleMenuRelateDao;

    //系统角色名称DB许可字节长度
    private static final int NAME_MAX_BYTES_BY_DB = 30;
    //系角色备注DB许可字节长度
    private static final int REMARK_MAX_BYTES_BY_DB = 300;

    /**
     * 根获取数据集合
     *
     * @return
     */
    public List<SystemRoleBo> getList() throws EqianyuanException {
        List<SystemRole> systemRoles = systemRoleDao.selectByList();
        if (CollectionUtils.isEmpty(systemRoles)) {
            logger.info("get List is null");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_ROLE_DATA_NOT_EXISTS);
        }

        List<SystemRoleBo> systemRoleBos = new ArrayList<SystemRoleBo>();
        for (SystemRole systemRole : systemRoles) {
            SystemRoleBo systemRoleBo = new SystemRoleBo();
            BeanUtils.copyProperties(systemRole, systemRoleBo);
            systemRoleBos.add(systemRoleBo);
        }
        return systemRoleBos;
    }

    /**
     * 根据主键编号删除数据
     *
     * @param id
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeByIds(String... id) throws EqianyuanException {
        if (ObjectUtils.isEmpty(id)) {
            logger.info("removeByIds fail , because id is null, a full table delete is prohibited");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_ID_IS_EMPTY);
        }

        //删除角色信息
        systemRoleDao.deleteByPrimaryKey(id);

        //删除用户角色关系
        systemUserRoleRelateDao.deleteBySystemRole(id);

        //删除角色与菜单管理
        systemRoleMenuRelateDao.deleteBySystemRole(id);
    }

    /**
     * 添加数据
     *
     * @param systemRoleByEditRequest
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(SystemRoleByEditRequest systemRoleByEditRequest) throws EqianyuanException {
        //名称是否为空
        if (StringUtils.isEmpty(systemRoleByEditRequest.getName())) {
            logger.info("add fail , because name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_ROLE_NAME_IS_EMPTY);
        }

        //角色名称内容长度是否超出DB许可长度
        try {
            if (systemRoleByEditRequest.getName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because name [" + systemRoleByEditRequest.getName()
                        + "] bytes greater than" + NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_ROLE_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + systemRoleByEditRequest.getName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //角色备注内容长度是否超出DB许可长度
        try {
            if (!StringUtils.isEmpty(systemRoleByEditRequest.getRemark())) {
                if (systemRoleByEditRequest.getRemark().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > REMARK_MAX_BYTES_BY_DB) {
                    logger.info("add fail , because name [" + systemRoleByEditRequest.getRemark()
                            + "] bytes greater than" + REMARK_MAX_BYTES_BY_DB);
                    throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_ROLE_REMARK_TO_LONG);
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + systemRoleByEditRequest.getRemark()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //添加角色数据信息
        SystemRole systemRole = new SystemRole();
        BeanUtils.copyProperties(systemRoleByEditRequest, systemRole);
        systemRoleDao.insertSelective(systemRole);

        if (!ObjectUtils.isEmpty(systemRoleByEditRequest.getMenuId())) {
            //添加用户与角色关系
            systemRoleMenuRelateDao.insert(systemRole.getId(), systemRoleByEditRequest.getMenuId());
        }

    }

    /**
     * 修改数据
     *
     * @param systemRoleByEditRequest
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void modify(SystemRoleByEditRequest systemRoleByEditRequest) throws EqianyuanException {
        //主键是否为空
        if (StringUtils.isEmpty(systemRoleByEditRequest.getId())) {
            logger.info("modify fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_ROLE_ID_IS_EMPTY);
        }

        //名称是否为空
        if (StringUtils.isEmpty(systemRoleByEditRequest.getName())) {
            logger.info("modify fail , because name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_ROLE_NAME_IS_EMPTY);
        }

        //角色名称内容长度是否超出DB许可长度
        try {
            if (systemRoleByEditRequest.getName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > NAME_MAX_BYTES_BY_DB) {
                logger.info("modify fail , because name [" + systemRoleByEditRequest.getName()
                        + "] bytes greater than" + NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_ROLE_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("modify fail , because name [" + systemRoleByEditRequest.getName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //角色备注内容长度是否超出DB许可长度
        try {
            if (!StringUtils.isEmpty(systemRoleByEditRequest.getRemark())) {
                if (systemRoleByEditRequest.getRemark().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > REMARK_MAX_BYTES_BY_DB) {
                    logger.info("modify fail , because name [" + systemRoleByEditRequest.getRemark()
                            + "] bytes greater than" + REMARK_MAX_BYTES_BY_DB);
                    throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_ROLE_REMARK_TO_LONG);
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("modify fail , because name [" + systemRoleByEditRequest.getRemark()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        SystemRole systemRole = new SystemRole();
        BeanUtils.copyProperties(systemRoleByEditRequest, systemRole);
        systemRoleDao.updateByPrimaryKeySelective(systemRole);

        //删除角色与菜单关系
        systemRoleMenuRelateDao.deleteBySystemRole(systemRoleByEditRequest.getId());

        if (!ObjectUtils.isEmpty(systemRoleByEditRequest.getMenuId())) {
            //添加角色与菜单关系
            systemRoleMenuRelateDao.insert(systemRole.getId(), systemRoleByEditRequest.getMenuId());
        }
    }

    /**
     * 根据主键查询数据对象
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    public SystemRoleBo queryById(String id) throws EqianyuanException {
        //主键是否为空
        if (StringUtils.isEmpty(id)) {
            logger.info("queryById fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_ROLE_ID_IS_EMPTY);
        }

        SystemRole systemRole = systemRoleDao.selectByPrimaryKey(id);

        if (ObjectUtils.isEmpty(systemRole) ||
                StringUtils.isEmpty(systemRole.getId())) {
            logger.info("queryById fail , because id [" + id + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_ROLE_DATA_NOT_EXISTS);
        }

        SystemRoleBo systemRoleBo = new SystemRoleBo();
        BeanUtils.copyProperties(systemRole, systemRoleBo);

        //查询绑定的菜单编号
        List<SystemRoleMenuRelate> systemRoleMenuRelates = systemRoleMenuRelateDao.selectMenuIdBySystemRole(id);
        if (!CollectionUtils.isEmpty(systemRoleMenuRelates)) {
            List<String> menuIds = new ArrayList<String>();
            for (SystemRoleMenuRelate systemRoleMenuRelate : systemRoleMenuRelates) {
                menuIds.add(systemRoleMenuRelate.getMenuId());
            }
            systemRoleBo.setMenuId(menuIds.toArray(new String[0]));
        }

        return systemRoleBo;
    }

    /**
     * 根据分页条件查询数据集合
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageResponse queryByPagination(int pageNo, int pageSize) {
        Long dataCount = systemRoleDao.countByPagination();
        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        Page page = new Page(pageNo, pageSize);
        List<SystemRole> systemRoles = systemRoleDao.selectByPagination(page);
        if (CollectionUtils.isEmpty(systemRoles)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        List<SystemRoleBo> systemRoleBos = new ArrayList<SystemRoleBo>();
        for (SystemRole systemRole : systemRoles) {
            SystemRoleBo systemRoleBo = new SystemRoleBo();
            BeanUtils.copyProperties(systemRole, systemRoleBo);
            systemRoleBos.add(systemRoleBo);
        }
        return new PageResponse(pageNo, pageSize, dataCount, systemRoleBos);
    }
}
