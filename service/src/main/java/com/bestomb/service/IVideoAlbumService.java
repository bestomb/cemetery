package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.videoAlbum.VideoAlbumEditRequest;
import com.bestomb.common.response.PageResponse;

/**
 * 陵园纪念人专辑接口
 * Created by jason on 2016-10-18.
 */
public interface IVideoAlbumService {

    /**
     * 创建纪念人专辑
     *
     * @param videoAlbumEditRequest
     * @throws EqianyuanException
     */
    void add(VideoAlbumEditRequest videoAlbumEditRequest) throws EqianyuanException;

    /**
     * 根据专辑编号删除数据
     *
     * @param videoAlbumId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    void del(String videoAlbumId, String cemeteryId, Integer memberId) throws EqianyuanException;

    /***
     * 根据纪念人及分页信息查询专辑分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    PageResponse getList(String masterId, Pager page) throws EqianyuanException;
}
