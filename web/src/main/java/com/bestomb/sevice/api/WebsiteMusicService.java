package com.bestomb.sevice.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.music.MusicBo;
import com.bestomb.common.response.music.MusicVo;
import com.bestomb.service.IMusicService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 网站陵园背景音乐业务调用类
 * Created by jason on 2016-08-08.
 */
@Service
public class WebsiteMusicService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IMusicService musicService;

    /**
     * 根据陵园编号获取陵园背景音乐分页集合
     *
     * @param cemeteryId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getListByCemetery(String cemeteryId, int pageNo, int pageSize) throws EqianyuanException {
        PageResponse pageResponse = musicService.getListByCemeteryId(cemeteryId, pageNo, pageSize);
        List<MusicBo> musicBos = (List<MusicBo>) pageResponse.getList();
        if (!CollectionUtils.isEmpty(musicBos)) {
            setListByPageResponse(pageResponse, musicBos);
        }
        return pageResponse;
    }

    /**
     * 设置分页返回对象数据
     *
     * @param pageResponse
     * @param musicBos
     */
    private void setListByPageResponse(PageResponse pageResponse, List<MusicBo> musicBos) {
        List<MusicVo> musicVos = new ArrayList<MusicVo>();
        for (MusicBo musicBo : musicBos) {
            MusicVo musicVo = new MusicVo();
            BeanUtils.copyProperties(musicBo, musicVo);
            musicVos.add(musicVo);
        }
        pageResponse.setList(musicVos);
    }
}
