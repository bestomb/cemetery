package com.bestomb.sevice;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.systemUser.SystemUserByEditRequest;
import com.bestomb.service.ISystemUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统用户管理
 * Created by jason on 2016-07-08.
 */
@Service
public class SystemUserService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ISystemUserService systemUserService;

    /**
     * 系统用户添加
     *
     * @param systemUserByAddRequest
     * @throws EqianyuanException
     */
    public void add(SystemUserByEditRequest systemUserByAddRequest) throws EqianyuanException {
//        if (ObjectUtils.isEmpty(systemUserByAddRequest)) {
//            logger.warn("add fail , because add object is null");
//            throw new EqianyuanException(ExceptionMsgConstant.LOGIN_USER_OBJECT_IS_EMPTY);
//        }
//
//        SystemUserBo systemUserBo = new SystemUserBo();
//        BeanUtils.copyProperties(systemUserByAddRequest, systemUserBo);
//        systemUserService.add(systemUserBo);
    }

    /**
     * 系统用户删除
     * @param id
     */
    public void delete(String ... id){

    }
}
