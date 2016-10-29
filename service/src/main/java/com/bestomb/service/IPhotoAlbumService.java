package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.photoAlbum.PhotoAlbumEditRequest;
import com.bestomb.common.response.PageResponse;

/**
 * 陵园纪念人相册接口
 * Created by jason on 2016-10-18.
 */
public interface IPhotoAlbumService {

    /**
     * 创建纪念人相册
     *
     * @param photoAlbumEditRequest
     * @throws EqianyuanException
     */
    void add(PhotoAlbumEditRequest photoAlbumEditRequest) throws EqianyuanException;

    /**
     * 根据相册编号删除数据
     *
     * @param photoAlbumId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    void del(String photoAlbumId, String cemeteryId, Integer memberId) throws EqianyuanException;

    /***
     * 根据纪念人及分页信息查询相册分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    PageResponse getList(String masterId, Pager page) throws EqianyuanException;
}
