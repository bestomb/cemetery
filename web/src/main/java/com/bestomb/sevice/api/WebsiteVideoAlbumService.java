package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.videoAlbum.VideoAlbumEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.IVideoAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jason on 2016-10-18.
 */
@Service
public class WebsiteVideoAlbumService {

    @Autowired
    private IVideoAlbumService videoAlbumService;

    /**
     * 创建纪念人专辑
     *
     * @param videoAlbumEditRequest
     * @throws EqianyuanException
     */
    public void add(VideoAlbumEditRequest videoAlbumEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        videoAlbumEditRequest.setMemberId(memberLoginVo.getMemberId());
        videoAlbumService.add(videoAlbumEditRequest);
    }

    /**
     * 根据专辑编号删除数据
     *
     * @param cemeteryId   陵园编号
     * @param videoAlbumId 专辑编号
     * @throws EqianyuanException
     */
    public void del(String cemeteryId, String videoAlbumId) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        videoAlbumService.del(videoAlbumId, cemeteryId, memberLoginVo.getMemberId());
    }

    /**
     * 根据纪念人及分页信息查询专辑分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String masterId, Pager page) throws EqianyuanException {
        return videoAlbumService.getList(masterId, page);
    }
}
