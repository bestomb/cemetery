package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;

/***
 * 祭祀留言接口
 * @author admin
 *
 */
public interface ILeaveMessage {
	
	/***
	 * 查询我（接收到）的留言
	 * @param memberId
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getReceivedMessage(Integer memberId, Pager page) throws EqianyuanException;
	
	/***
	 * 查询我（发出）的留言
	 * @param memberId
	 * @param page
	 * @return
	 * @throws EqianyuanException
	 */
	public PageResponse getPushedMessage(Integer memberId, Pager page) throws EqianyuanException;
	
	/***
	 * 删除留言
	 * @param id
	 * @return
	 * @throws EqianyuanException
	 */
	public boolean deleteByPrimaryKey(String id) throws EqianyuanException;
	
}
