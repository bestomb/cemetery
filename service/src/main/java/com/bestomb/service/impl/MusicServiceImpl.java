package com.bestomb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.music.MusicBo;
import com.bestomb.dao.ICemeteryDao;
import com.bestomb.dao.IMusicDao;
import com.bestomb.entity.Cemetery;
import com.bestomb.entity.Music;
import com.bestomb.service.IMusicService;

/**
 * 陵园背景音乐业务逻辑接口实现类
 * Created by jason on 2016-08-08.
 */
@Service
public class MusicServiceImpl implements IMusicService {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IMusicDao musicDao;

    @Autowired
    private ICemeteryDao cemeteryDao;
    
    /***
     * 音乐删除
     */
    public boolean deleteById(String id) throws EqianyuanException{
    	if (StringUtils.isEmpty(id)) {
            logger.warn("deleteById fail , because id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
        }
    	return musicDao.deleteByPrimaryKey(id) > 0 ;
    }
    
    /***
     * 封装查询音乐列表方法
     * @param dto
     * @param page
     * @return
     * @throws EqianyuanException 
     */
    public PageResponse getListByCondition(Music music, Pager page) throws EqianyuanException{
    	String cemeteryId = music.getCemeteryId();
    	// 验证陵园编号
    	validCemeteryId(cemeteryId);
        // 根据条件查询音乐列表
        int dataCount = musicDao.countByCondition(music, page);
        page.setTotalRow(dataCount);
        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("根据条件查询音乐列表无数据l");
            return new PageResponse(page,  null);
        }
        List<Music> musics = musicDao.selectByCondition(music, page);
        if ( CollectionUtils.isEmpty(musics) ) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询音乐列表无数据l");
            return new PageResponse(page,  null);
        }
        List<MusicBo> musicBos = new ArrayList<MusicBo>();
        for (Music m : musics) {
            MusicBo musicBo = new MusicBo();
            BeanUtils.copyProperties(m, musicBo);
            musicBos.add(musicBo);
        }
        return new PageResponse(page, musicBos);
    }
    
    
    /***
     * 根据陵园编号查询陵园背景音乐分页集合
     *
     * @param cemeteryId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getListByCemeteryId(String cemeteryId, int pageNo, int pageSize) throws EqianyuanException {
    	Music music = new Music();
    	music.setCemeteryId(cemeteryId);
    	Pager page = new Pager(pageNo, pageSize);
    	return getListByCondition(music, page);
    }
    
    /***
     * 验证陵园编号是否有值，并且在数据库中存在。否则直接抛出异常
     * @param cemeteryId
     * @throws EqianyuanException
     */
	private void validCemeteryId(String cemeteryId) throws EqianyuanException {
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
	}

	
}
