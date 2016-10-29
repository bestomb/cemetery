package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.photoAlbum.PhotoAlbumEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.IPhotoAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jason on 2016-10-18.
 */
@Service
public class WebsitePhotoAlbumService {

    @Autowired
    private IPhotoAlbumService photoAlbumService;

    /**
     * 创建纪念人相册
     *
     * @param photoAlbumEditRequest
     * @throws EqianyuanException
     */
    public void add(PhotoAlbumEditRequest photoAlbumEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        photoAlbumEditRequest.setMemberId(memberLoginVo.getMemberId());
        photoAlbumService.add(photoAlbumEditRequest);
    }

    /**
     * 根据相册编号删除数据
     *
     * @param cemeteryId   陵园编号
     * @param photoAlbumId 相册编号
     * @throws EqianyuanException
     */
    public void del(String cemeteryId, String photoAlbumId) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        photoAlbumService.del(photoAlbumId, cemeteryId, memberLoginVo.getMemberId());
    }

    /**
     * 根据纪念人及分页信息查询相册分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String masterId, Pager page) throws EqianyuanException {
        return photoAlbumService.getList(masterId, page);
    }
}
