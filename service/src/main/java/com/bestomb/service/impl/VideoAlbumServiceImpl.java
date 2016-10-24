package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.videoAlbum.VideoAlbumEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.videoAlbum.VideoAlbumBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.IVideoAlbumDao;
import com.bestomb.entity.VideoAlbum;
import com.bestomb.service.IPhotoAlbumService;
import com.bestomb.service.IVideoAlbumService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 陵园纪念人专辑接口业务实现
 * Created by jason on 2016-10-18.
 */
@Service
public class VideoAlbumServiceImpl implements IVideoAlbumService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IVideoAlbumDao videoAlbumDao;

    @Autowired
    private CommonService commonService;

    /**
     * 创建纪念人专辑
     *
     * @param videoAlbumEditRequest
     * @throws EqianyuanException
     */
    public void add(VideoAlbumEditRequest videoAlbumEditRequest) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(videoAlbumEditRequest.getCemeteryId(), videoAlbumEditRequest.getMemberId());

        //构建持久化专辑数据
        VideoAlbum videoAlbum = new VideoAlbum();
        videoAlbum.setMasterId(videoAlbumEditRequest.getMasterId());
        videoAlbum.setName(videoAlbumEditRequest.getName());
        videoAlbum.setRemark(videoAlbumEditRequest.getRemark());
        videoAlbum.setCreateTime(CalendarUtil.getSystemSeconds());
        //持久陵园纪念人专辑数据
        videoAlbumDao.insertSelective(videoAlbum);
    }

    /**
     * 根据专辑编号删除数据
     *
     * @param videoAlbumId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    public void del(String videoAlbumId, String cemeteryId, Integer memberId) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(cemeteryId, memberId);

        //删除数据
        videoAlbumDao.deleteByPrimaryKey(videoAlbumId);
        //todo 需要删除该相册的所有视频相关数据/附件等
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
        // 根据条件查询专辑列表
        int dataCount = videoAlbumDao.countByCondition(masterId);
        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询陵园纪念人专辑列表无数据");
            return new PageResponse(page, null);
        }
        List<VideoAlbum> videoAlbumList = videoAlbumDao.selectByCondition(masterId, page);
        if (CollectionUtils.isEmpty(videoAlbumList)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询陵园纪念人专辑列表无数据");
            return new PageResponse(page, null);
        }
        List<VideoAlbumBo> videoAlbumBos = new ArrayList<VideoAlbumBo>();
        for (VideoAlbum m : videoAlbumList) {
            VideoAlbumBo videoAlbumBo = new VideoAlbumBo();
            BeanUtils.copyProperties(m, videoAlbumBo);
            videoAlbumBos.add(videoAlbumBo);
        }
        return new PageResponse(page, videoAlbumBos);
    }


}
