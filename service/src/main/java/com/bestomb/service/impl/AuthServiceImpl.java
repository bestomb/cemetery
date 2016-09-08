package com.bestomb.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.dao.IMemberAuthorizationDao;
import com.bestomb.entity.MemberAuthorization;
import com.bestomb.service.IAuthService;

/***
 * 权限接口实现类（陵园、会员等）
 * @author qfzhang
 *
 */
@Service
public class AuthServiceImpl implements IAuthService {
	
	private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IMemberAuthorizationDao memberAuthDao;
	
	/***
	 * 授权陵园给会员代管理
	 * @param MemberAuthorization {陵园ID、会员ID必填}
	 * @return
	 */
	public boolean authCemeteryToMember(MemberAuthorization record) throws EqianyuanException{
		// 检查陵园编号是否为空
		if (ObjectUtils.isEmpty(record.getCemeteryId())) {
            logger.warn("authCemeteryToMember fail , because cemeteryId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_ID_IS_EMPTY);
        }
		// 检查会员编号是否为空
		if (ObjectUtils.isEmpty(record.getMemberId())) {
            logger.warn("authCemeteryToMember fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
		
		return memberAuthDao.insert(record)>0 ;
	}
	
	/***
	 * 根据陵园ID集合获取已授权会员分页列表
	 * @param memberId
	 * @param page
	 * @return
	 */
	public List<MemberAuthorization> getAuthMembersPageList(List<Integer> cemeteryIds, Pager page) throws EqianyuanException {
		if (ObjectUtils.isEmpty(cemeteryIds)) {
			logger.warn("getAuthMembersPageList fail, cemeteryIds is null");
			throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
		}
		List<MemberAuthorization> resultList = memberAuthDao.selectPageListByCemeteryId(cemeteryIds, page);
		return resultList;
	}
	
	/***
	 * 回收给会员代管理陵园的权限
	 * @param id
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean removeCemeteryAuthToMember(String id) throws EqianyuanException{
		if (StringUtils.isEmpty(id)) {
			logger.warn("removeCemeteryAuthToMember fail, id is null");
			throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
		}
		return memberAuthDao.deleteByPrimaryKey(id)>0 ;
	}
	
}
