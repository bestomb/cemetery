package com.bestomb.service.impl;

import com.bestomb.common.Page;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.systemUser.SystemUserByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.systemUser.SystemUserBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.common.util.Md5Util;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.ISystemUserDao;
import com.bestomb.dao.ISystemUserRoleRelateDao;
import com.bestomb.entity.SystemUser;
import com.bestomb.entity.SystemUserRoleRelate;
import com.bestomb.service.ISystemUserService;
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
 * 系统用户管理业务逻辑
 * Created by jason on 2016-07-06.
 */
@Service
public class SystemUserServiceImpl implements ISystemUserService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ISystemUserDao systemUserDao;

    @Autowired
    private ISystemUserRoleRelateDao systemUserRoleRelateDao;

    //系统用户用户名DB许可字节长度
    private static final int LOGIN_NAME_MAX_BYTES_BY_DB = 30;
    //系统用户真实姓名DB许可字节长度
    private static final int REAL_NAME_MAX_BYTES_BY_DB = 30;

    /**
     * 根据主键编号删除数据
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeByIds(String... id) throws EqianyuanException {
        if (ObjectUtils.isEmpty(id)) {
            logger.info("removeByIds fail , because id is null, a full table delete is prohibited");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_ID_IS_EMPTY);
        }
        //删除用户数据
        systemUserDao.deleteByPrimaryKey(id);

        //删除用户角色关系
        systemUserRoleRelateDao.deleteBySystemUser(id);
    }

    /**
     * 添加数据
     *
     * @param systemUserByEditRequest
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(SystemUserByEditRequest systemUserByEditRequest) throws EqianyuanException {
        //用户名是否为空
        if (StringUtils.isEmpty(systemUserByEditRequest.getLoginName())) {
            logger.info("add fail , because login name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_LOGIN_NAME_IS_EMPTY);
        }

        //密码是否为空
        if (StringUtils.isEmpty(systemUserByEditRequest.getLoginPassword())) {
            logger.info("add fail , because login password is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_LOGIN_PASSWORD_IS_EMPTY);
        }

        //真实姓名是否为空
        if (StringUtils.isEmpty(systemUserByEditRequest.getRealName())) {
            logger.info("add fail , because real name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_REAL_NAME_IS_EMPTY);
        }

        //手机号码是否为空
        if (ObjectUtils.isEmpty(systemUserByEditRequest.getMobile())) {
            logger.info("add fail , because mobile is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_MOBILE_IS_EMPTY);
        }

        //用户名内容长度是否超出DB许可长度
        try {
            if (systemUserByEditRequest.getLoginName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > LOGIN_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because login name [" + systemUserByEditRequest.getLoginName() + "] bytes greater than"
                        + LOGIN_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_LOGIN_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because login name [" + systemUserByEditRequest.getLoginName() + "] getBytes("
                    + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //用户真实姓名内容长度是否超出DB许可长度
        try {
            if (systemUserByEditRequest.getRealName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > REAL_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because real name [" + systemUserByEditRequest.getRealName()
                        + "] bytes greater than" + REAL_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_REAL_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because real name [" + systemUserByEditRequest.getRealName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //添加用户数据信息
        SystemUser systemUser = new SystemUser();
        BeanUtils.copyProperties(systemUserByEditRequest, systemUser);
        systemUser.setLoginPassword(Md5Util.MD5By32(systemUserByEditRequest.getLoginPassword()));
        systemUser.setCreateTime(CalendarUtil.getSystemSeconds());
        systemUserDao.insertSelective(systemUser);

        if (!ObjectUtils.isEmpty(systemUserByEditRequest.getRoleId())) {
            //添加用户与角色关系
            systemUserRoleRelateDao.insert(systemUser.getId(), systemUserByEditRequest.getRoleId());
        }
    }

    /**
     * 修改数据
     *
     * @param systemUserByEditRequest
     */
    @Transactional(rollbackFor = Exception.class)
    public void modify(SystemUserByEditRequest systemUserByEditRequest) throws EqianyuanException {
        //主键是否为空
        if (StringUtils.isEmpty(systemUserByEditRequest.getId())) {
            logger.info("modify fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_ID_IS_EMPTY);
        }

        //用户名是否为空
        if (StringUtils.isEmpty(systemUserByEditRequest.getLoginName())) {
            logger.info("modify fail , because login name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_LOGIN_NAME_IS_EMPTY);
        }

        //真实姓名是否为空
        if (StringUtils.isEmpty(systemUserByEditRequest.getRealName())) {
            logger.info("modify fail , because real name is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_REAL_NAME_IS_EMPTY);
        }

        //手机号码是否为空
        if (ObjectUtils.isEmpty(systemUserByEditRequest.getMobile())) {
            logger.info("modify fail , because mobile is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_MOBILE_IS_EMPTY);
        }

        //用户名内容长度是否超出DB许可长度
        try {
            if (systemUserByEditRequest.getLoginName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > LOGIN_NAME_MAX_BYTES_BY_DB) {
                logger.info("modify fail , because login name [" + systemUserByEditRequest.getLoginName() + "] bytes greater than"
                        + LOGIN_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_LOGIN_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("modify fail , because login name [" + systemUserByEditRequest.getLoginName() + "] getBytes("
                    + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //用户真实姓名内容长度是否超出DB许可长度
        try {
            if (systemUserByEditRequest.getRealName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > REAL_NAME_MAX_BYTES_BY_DB) {
                logger.info("modify fail , because real name [" + systemUserByEditRequest.getRealName()
                        + "] bytes greater than" + REAL_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_REAL_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("modify fail , because real name [" + systemUserByEditRequest.getRealName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //添加用户数据信息
        SystemUser systemUser = new SystemUser();
        BeanUtils.copyProperties(systemUserByEditRequest, systemUser);
        systemUserDao.updateByPrimaryKeySelective(systemUser);

        //删除用户与角色关系
        systemUserRoleRelateDao.deleteBySystemUser(systemUserByEditRequest.getId());

        if (!ObjectUtils.isEmpty(systemUserByEditRequest.getRoleId())) {
            //添加用户与角色关系
            systemUserRoleRelateDao.insert(systemUser.getId(), systemUserByEditRequest.getRoleId());
        }
    }

    /**
     * 根据主键查询数据对象
     *
     * @param id
     * @return
     */
    public SystemUserBo queryById(String id) throws EqianyuanException {
        //主键是否为空
        if (StringUtils.isEmpty(id)) {
            logger.info("queryById fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_ID_IS_EMPTY);
        }

        SystemUser systemUser = systemUserDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(systemUser) ||
                StringUtils.isEmpty(systemUser.getId())) {
            logger.info("queryById fail , because id [" + id + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.LOGIN_USER_OBJECT_IS_EMPTY);
        }

        SystemUserBo systemUserBo = new SystemUserBo();
        BeanUtils.copyProperties(systemUser, systemUserBo);
        systemUserBo.setCreateTimeForStr(CalendarUtil.secondsTimeToDateTimeString(systemUser.getCreateTime()));

        //查询绑定的角色编号
        List<SystemUserRoleRelate> systemUserRoleRelates = systemUserRoleRelateDao.selectMenuIdBySystemUser(id);
        if (!CollectionUtils.isEmpty(systemUserRoleRelates)) {
            List<String> roleIds = new ArrayList<String>();
            for (SystemUserRoleRelate systemUserRoleRelate : systemUserRoleRelates) {
                roleIds.add(systemUserRoleRelate.getRoleId());
            }
            systemUserBo.setRoleId(roleIds.toArray(new String[0]));
        }

        return systemUserBo;
    }

    /**
     * 根据分页条件查询数据集合
     *
     * @param pageNo
     * @param pageSize
     * @param roleId
     * @return
     */
    public PageResponse queryByPagination(int pageNo, int pageSize, String roleId) {
        Long dataCount = systemUserDao.countByPagination(roleId);
        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("roleId [" + roleId + "] get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        Page page = new Page(pageNo, pageSize);
        List<SystemUser> systemUsers = systemUserDao.selectByPagination(page, roleId);
        if (CollectionUtils.isEmpty(systemUsers)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "], roleId [" + roleId + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        List<SystemUserBo> systemUserBos = new ArrayList<SystemUserBo>();
        for (SystemUser systemUser : systemUsers) {
            SystemUserBo systemUserBo = new SystemUserBo();
            BeanUtils.copyProperties(systemUser, systemUserBo);
            systemUserBo.setCreateTimeForStr(CalendarUtil.secondsTimeToDateTimeString(systemUser.getCreateTime()));
            systemUserBos.add(systemUserBo);
        }
        return new PageResponse(pageNo, pageSize, dataCount, systemUserBos);
    }

    /**
     * 带验证码登录
     * <p>
     * 业务逻辑
     * 1.检查request请求参数中是否带有登录用户名，如果不存在数据，则抛出登录用户名为空的异常
     * 2.检查request请求参数中是否带有登录密码，如果不存在数据，则抛出登录密码为空的异常
     * 3.检查request请求参数中是否带有登录验证码，如果验证码数据为空，则抛出验证码为空的异常
     * 4.检查session中是否存在验证码，如果验证码不存在，则抛出验证码错误的异常
     * 5.检查request的请求验证码和session中的验证码是否一致，如果不一致，则抛出验证码错误的异常
     * 6.明文密码MD5—32位加密
     * 7.根据用户名及密码查询数据并返回结果
     *
     * @param loginName     用户名
     * @param loginPassword 未加密密码
     * @param code          验证码
     * @return
     * @throws EqianyuanException
     */
    public SystemUserBo login(String loginName, String loginPassword, String code) throws EqianyuanException {
        if (StringUtils.isEmpty(loginName)) {
            logger.warn("login fail, because request param [ loginName ] is empty.");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_LOGIN_NAME_IS_EMPTY);
        }

        if (StringUtils.isEmpty(loginPassword)) {
            logger.warn("loginName [" + loginName + "] login fail, because request param [ loginPassword ] is empty.");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_USER_LOGIN_PASSWORD_IS_EMPTY);
        }

        if (StringUtils.isEmpty(code)) {
            logger.warn("login fail, because request param [ code ] is empty.");
            throw new EqianyuanException(ExceptionMsgConstant.VALIDATA_CODE_IS_EMPTY);
        }

        //从session中获取验证码数据
        String codeByValidation = (String) SessionUtil.getAttribute(SystemConf.VERIFY_CODE.toString());
        if (StringUtils.isEmpty(codeByValidation)) {
            logger.warn("login fail, because there is no verification code in the session attribute.");
            throw new EqianyuanException(ExceptionMsgConstant.VALIDATA_CODE_VALIDATION_ERROR);
        }

        if (!StringUtils.equalsIgnoreCase(code, codeByValidation)) {
            logger.warn("login fail, because session attribute verification code and request param code not consistent.");
            throw new EqianyuanException(ExceptionMsgConstant.VALIDATA_CODE_VALIDATION_ERROR);
        }

        //密码加密处理
        String encryptionPwd = Md5Util.MD5By32(StringUtils.lowerCase(loginPassword));

        SystemUser systemUser = systemUserDao.selectByLogin(loginName, encryptionPwd);
        if (ObjectUtils.isEmpty(systemUser)
                || ObjectUtils.isEmpty(systemUser.getId())) {
            logger.warn("selectByLogin fail , loginName [" + loginName + "] , loginPassword [" + loginPassword + "] , encryptionPwd [" + encryptionPwd + "]");
            throw new EqianyuanException(ExceptionMsgConstant.LOGIN_USER_NAME_OR_PASSWORD_ERROR);
        }

        SystemUserBo systemUserBo = new SystemUserBo();
        BeanUtils.copyProperties(systemUser, systemUserBo);

        //获取用户角色编号
        List<SystemUserRoleRelate> systemUserRoleRelates = systemUserRoleRelateDao.selectMenuIdBySystemUser(systemUser.getId());
        if (!CollectionUtils.isEmpty(systemUserRoleRelates)) {
            List<String> roleIds = new ArrayList<String>();
            for (SystemUserRoleRelate systemUserRoleRelate : systemUserRoleRelates) {
                roleIds.add(systemUserRoleRelate.getRoleId());
            }
            systemUserBo.setRoleId(roleIds.toArray(new String[0]));
        }

        return systemUserBo;
    }
}
