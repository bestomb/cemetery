package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.videoAlbum.video.VideoEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.videoAlbum.video.VideoBo;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jason on 2016-10-18.
 */
@Service
public class WebsiteVideoService {

    @Autowired
    private IVideoService videoService;

    /**
     * 为专辑添加视频
     *
     * @param videoEditRequest
     * @throws EqianyuanException
     */
    public void add(VideoEditRequest videoEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        videoEditRequest.setMemberId(memberLoginVo.getMemberId());
        videoService.add(videoEditRequest);
    }

    /**
     * 根据视频编号删除视频数据
     *
     * @param cemeteryId 陵园编号
     * @param videoId    视频编号
     * @throws EqianyuanException
     */
    public void del(String cemeteryId, String videoId) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        videoService.del(videoId, cemeteryId, memberLoginVo.getMemberId());
    }

    /**
     * 根据专辑编号查询视频分页集合
     *
     * @param videoAlbumId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String videoAlbumId, Pager page) throws EqianyuanException {
        return videoService.getList(videoAlbumId, page);
    }

    /***
     * 根据视频编号查询视频详情
     *
     * @param videoId
     * @return
     * @throws EqianyuanException
     */
    public VideoBo getInfo(String videoId) throws EqianyuanException {
        VideoBo videoBo = videoService.getInfo(videoId);
        return videoBo;
    }
}
