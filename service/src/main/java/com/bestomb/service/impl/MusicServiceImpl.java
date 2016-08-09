package com.bestomb.service.impl;

import com.bestomb.common.Page;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.music.MusicBo;
import com.bestomb.dao.ICemeteryDao;
import com.bestomb.dao.IMusicDao;
import com.bestomb.entity.Cemetery;
import com.bestomb.entity.Music;
import com.bestomb.service.IMusicService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

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
     * 根据陵园编号查询陵园背景音乐分页集合
     *
     * @param cemeteryId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getListByCemeteryId(String cemeteryId, int pageNo, int pageSize) throws EqianyuanException {
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

        //根据社区编号获取陵园背景音乐总数
        Long dataCount = musicDao.countByPagination(cemeteryId);

        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("cemeteryId [" + cemeteryId + "] get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        Page page = new Page(pageNo, pageSize);

        List<Music> musics = musicDao.selectByPagination(page, cemeteryId);
        if (CollectionUtils.isEmpty(musics)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "], cemeteryId [" + cemeteryId + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        List<MusicBo> musicBos = new ArrayList<MusicBo>();
        for (Music music : musics) {
            MusicBo musicBo = new MusicBo();
            BeanUtils.copyProperties(music, musicBo);
            musicBos.add(musicBo);
        }
        return new PageResponse(pageNo, pageSize, dataCount, musicBos);
    }
}
