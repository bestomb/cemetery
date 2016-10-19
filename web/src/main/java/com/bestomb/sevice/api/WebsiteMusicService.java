package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.response.music.MusicBo;
import com.bestomb.common.response.music.MusicVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.entity.Music;
import com.bestomb.service.IMusicService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

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

    /***
     * 删除指定id的音乐
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    public boolean deleteById(String id) throws EqianyuanException {
        logger.info("删除指定id为 " + id + "的音乐");
        return musicService.deleteById(id, getLoginUserId());
    }

    /***
     * 根据条件查询音乐分页集合
     *
     * @param music
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getListByCondition(Music music, Pager page) throws EqianyuanException {
        PageResponse pageResponse = musicService.getListByCondition(music, page);
        List<MusicBo> musicBos = (List<MusicBo>) pageResponse.getList();
        if (!CollectionUtils.isEmpty(musicBos)) {
            setListByPageResponse(pageResponse, musicBos);
        }
        return pageResponse;
    }

    /**
     * 对陵园上传音乐
     *
     * @param musicFile
     * @param name
     * @param cemeteryId
     * @throws EqianyuanException
     */
    public void uploadMusic(MultipartFile musicFile, String name, String cemeteryId) throws EqianyuanException {
        musicService.uploadMusic(musicFile, name, cemeteryId, getLoginUserId());
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

    /**
     * 从session中获取当前用户编号
     *
     * @return
     */
    private Integer getLoginUserId() {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        return memberLoginVo.getMemberId();
    }
}
