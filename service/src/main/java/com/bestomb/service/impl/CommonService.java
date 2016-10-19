package com.bestomb.service.impl;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.dao.ICemeteryDao;
import com.bestomb.dao.IMemberAuthorizationDao;
import com.bestomb.entity.Cemetery;
import com.bestomb.entity.MemberAuthorization;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 业务逻辑处理公共函数类
 * Created by jason on 2016-10-18.
 */
@Component
public class CommonService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ICemeteryDao cemeteryDao;

    @Autowired
    private IMemberAuthorizationDao memberAuthorizationDao;

    /***
     * 验证陵园编号是否有值，并且在数据库中存在。否则直接抛出异常
     *
     * @param cemeteryId
     * @throws EqianyuanException
     */
    public Cemetery validCemeteryId(String cemeteryId) throws EqianyuanException {
        if (StringUtils.isEmpty(cemeteryId)) {
            logger.warn("getListByCemeteryId fail , because cemeteryId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_ID_IS_EMPTY);
        }
        //根据陵园编号获取陵园
        Cemetery cemetery = cemeteryDao.selectByPrimaryKey(cemeteryId);
        if (ObjectUtils.isEmpty(cemetery)
                || ObjectUtils.isEmpty(cemetery.getId())) {
            logger.info("getListByCemeteryId fail , because cemeteryId [" + cemeteryId + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_DATA_NOT_EXISTS);
        }

        return cemetery;
    }

    /**
     * * 检查当前系统登录用户是否拥有对陵园操作权
     * 1.陵园是当前会员所创建
     * 2.当前会员被陵园创建者赋予管理权限
     *
     * @param cemeteryId 陵园编号
     * @param memberId   会员编号
     * @throws EqianyuanException
     */
    public Cemetery hasPermissionsByCemetery(String cemeteryId, Integer memberId) throws EqianyuanException {
        //根据陵园编号校验陵园数据正确性（是否存在有效陵园数据）
        Cemetery cemetery = validCemeteryId(cemeteryId);

        //检查陵园是否为当前操作会员所建
        if (cemetery.getMemberId().equals(memberId)) {
            return cemetery;
        }

        //根据陵园编号查询授权用户
        List<MemberAuthorization> memberAuthorizations = memberAuthorizationDao.selectByCemeteryId(Arrays.asList(cemetery.getId()));
        if (CollectionUtils.isEmpty(memberAuthorizations)) {
            logger.info("get member authorizations by cemetery [" + cemeteryId + "] is null");
            throw new EqianyuanException(ExceptionMsgConstant.IS_NO_MASTER_BY_CEMETERY);
        }

        for (MemberAuthorization memberAuthorization : memberAuthorizations) {
            if (memberAuthorization.getMemberId().equals(memberId)) {
                return cemetery;
            }
        }

        logger.info("member id [" + memberId + "] user is not get cemetery id [" + cemetery + "] manage authorization");
        throw new EqianyuanException(ExceptionMsgConstant.IS_NO_MASTER_BY_CEMETERY);
    }
}
