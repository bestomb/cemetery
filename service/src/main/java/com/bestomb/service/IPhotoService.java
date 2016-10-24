package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.photoAlbum.photo.PhotoEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.photoAlbum.photo.PhotoBo;

/**
 * 陵园纪念人相册相片接口
 * Created by jason on 2016-10-18.
 */
public interface IPhotoService {

    /**
     * 为相册添加相片
     *
     * @param photoEditRequest
     * @throws EqianyuanException
     */
    void add(PhotoEditRequest photoEditRequest) throws EqianyuanException;

    /**
     * 根据相片编号删除相片数据
     *
     * @param photoId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    void del(String photoId, String cemeteryId, Integer memberId) throws EqianyuanException;

    /***
     * 根据相册编号查询相片分页集合
     *
     * @param photoAlbumId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    PageResponse getList(String photoAlbumId, Pager page) throws EqianyuanException;

    /**
     * 根据相片编号查询相片详情
     *
     * @param photoId
     * @return
     * @throws EqianyuanException
     */
    public PhotoBo getInfo(String photoId) throws EqianyuanException;
}
