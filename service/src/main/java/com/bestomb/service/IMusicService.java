package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.entity.Music;

/**
 * 陵园背景音乐业务接口
 * Created by jason on 2016-07-15.
 */
public interface IMusicService {
	
	/***
	 * 删除音乐
	 * @param Id
	 * @return
	 * @throws EqianyuanException 
	 */
	public boolean deleteById(String id) throws EqianyuanException;
	
	/***
	 * 根据条件查询音乐分页集合
	 * @param music
	 * @param page
	 * @return
	 * @throws EqianyuanException 
	 */
	PageResponse getListByCondition(Music music, Pager page) throws EqianyuanException;
	
    /**
     * 根据陵园编号查询陵园背景音乐分页集合
     *
     * @param cemeteryId
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResponse getListByCemeteryId(String cemeteryId, int pageNo, int pageSize) throws EqianyuanException;
    
    
}
