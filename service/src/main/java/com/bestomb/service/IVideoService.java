package com.bestomb.service;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.videoAlbum.video.VideoEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.videoAlbum.video.VideoBo;

/**
 * 陵园纪念人专辑视频接口
 * Created by jason on 2016-10-18.
 */
public interface IVideoService {

    /**
     * 为专辑添加视频
     *
     * @param videoEditRequest
     * @throws EqianyuanException
     */
    void add(VideoEditRequest videoEditRequest) throws EqianyuanException;

    /**
     * 根据视频编号删除视频数据
     *
     * @param videoId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    void del(String videoId, String cemeteryId, Integer memberId) throws EqianyuanException;

    /***
     * 根据专辑编号查询视频分页集合
     *
     * @param videoAlbumId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    PageResponse getList(String videoAlbumId, Pager page) throws EqianyuanException;

    /**
     * 根据视频编号查询视频详情
     *
     * @param videoId
     * @return
     * @throws EqianyuanException
     */
    VideoBo getInfo(String videoId) throws EqianyuanException;
}
