package com.bestomb.sevice.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.photoAlbum.photo.PhotoEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.photoAlbum.photo.PhotoBo;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.IPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jason on 2016-10-18.
 */
@Service
public class WebsitePhotoService {

    @Autowired
    private IPhotoService photoService;

    /**
     * 为相册添加相片
     *
     * @param photoEditRequest
     * @throws EqianyuanException
     */
    public void add(PhotoEditRequest photoEditRequest, HttpServletRequest httpServletRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader(httpServletRequest)), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        photoEditRequest.setMemberId(memberLoginVo.getMemberId());
        photoService.add(photoEditRequest);
    }

    /**
     * 根据相片编号删除相片数据
     *
     * @param cemeteryId 陵园编号
     * @param photoId    相片编号
     * @throws EqianyuanException
     */
    public void del(String cemeteryId, String photoId) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        photoService.del(photoId, cemeteryId, memberLoginVo.getMemberId());
    }

    /**
     * 根据相册编号查询相片分页集合
     *
     * @param photoAlbumId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String photoAlbumId, Pager page) throws EqianyuanException {
        return photoService.getList(photoAlbumId, page);
    }

    /***
     * 根据相片编号查询相片详情
     *
     * @param photoId
     * @return
     * @throws EqianyuanException
     */
    public PhotoBo getInfo(String photoId) throws EqianyuanException {
        PhotoBo photoBo = photoService.getInfo(photoId);
        return photoBo;
    }
}
