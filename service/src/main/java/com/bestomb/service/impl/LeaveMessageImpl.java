package com.bestomb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.LeaveMessageBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.ILeaveMessageDao;
import com.bestomb.entity.LeaveMessage;
import com.bestomb.service.ILeaveMessage;

/***
 * 留言接口实现类
 * @author qfzhang
 *
 */
@Service
public class LeaveMessageImpl implements ILeaveMessage{
	
private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ILeaveMessageDao leaveMessageDao;
	
	/***
	 * 查询我（接收到）的留言
	 * @param memberId
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getReceivedMessage(Integer memberId, Pager page) throws EqianyuanException {
		// 会员编号是否为空
		if (ObjectUtils.isEmpty(memberId)) {
            logger.warn("getMyMessage fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
		// 先查询总数
		int dataCount = leaveMessageDao.getReceivedMessageCount(memberId);
		if ( dataCount == 0 ) {
            logger.info("根据条件查询我（接收到）的留言无数据l");
            return new PageResponse(page,  null);
        }
		page.setTotalRow(dataCount);
		// 再查询分页数据
		List<LeaveMessage> messages = leaveMessageDao.getReceivedMessage(memberId, page);
		if ( CollectionUtils.isEmpty(messages) ) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询我（接收到）的留言无数据l");
            return new PageResponse(page,  null);
        }
		// 使用BO返回
		List<LeaveMessageBo> resultList = new ArrayList<LeaveMessageBo>();
		for (LeaveMessage entity : messages) {
			LeaveMessageBo bo = new LeaveMessageBo();
			BeanUtils.copyProperties(entity, bo);
			bo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(entity.getCreateTime())); // 转化创建时间
			resultList.add(bo);
		}
		
		return new PageResponse(page,  resultList);
	}
	
	/***
	 * 查询我（发出）的留言
	 * @param memberId
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getPushedMessage(Integer memberId, Pager page) throws EqianyuanException {
		// 会员编号是否为空
		if (ObjectUtils.isEmpty(memberId)) {
            logger.warn("getMyMessage fail , because memberId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBERSHIP_NUMBER_IS_EMPTY);
        }
		// 先查询总数
		int dataCount = leaveMessageDao.getPushedMessageCount(memberId);
		if ( dataCount == 0 ) {
            logger.info("根据条件查询我（发出）的留言无数据l");
            return new PageResponse(page,  null);
        }
		page.setTotalRow(dataCount);
		// 再查询分页数据
		List<LeaveMessage> messages = leaveMessageDao.getPushedMessage(memberId, page);
		if ( CollectionUtils.isEmpty(messages) ) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询我（发出）的留言无数据l");
            return new PageResponse(page,  null);
        }
		// 使用BO返回
		List<LeaveMessageBo> resultList = new ArrayList<LeaveMessageBo>();
		for (LeaveMessage entity : messages) {
			LeaveMessageBo bo = new LeaveMessageBo();
			BeanUtils.copyProperties(entity, bo);
			bo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(entity.getCreateTime())); // 转化创建时间
			resultList.add(bo);
		}
		
		return new PageResponse(page,  resultList);
	}

	
	/***
	 * 删除留言
	 * @param id
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean deleteByPrimaryKey(String id) throws EqianyuanException {
		// 留言ID是否为空
		if (StringUtils.isEmpty(id)) {
            logger.warn("deleteByPrimaryKey fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.MESSAGEID_IS_EMPTY);
        }
		// 留言是否存在
		if (leaveMessageDao.selectByPrimaryKey(id) == null ) {
			logger.warn("deleteByPrimaryKey fail , because messageId is not exsit.");
            throw new EqianyuanException(ExceptionMsgConstant.MESSAGEID_IS_NOT_EXSITS);
		}
		return leaveMessageDao.deleteByPrimaryKey(id)>0;
	}

}
