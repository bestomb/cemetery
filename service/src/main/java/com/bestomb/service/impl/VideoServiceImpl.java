package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.videoAlbum.video.VideoEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.videoAlbum.video.VideoBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.common.util.FileUtilHandle;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.ICemeteryDao;
import com.bestomb.dao.IVideoDao;
import com.bestomb.entity.Cemetery;
import com.bestomb.entity.Video;
import com.bestomb.service.IVideoService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 陵园纪念人专辑视频接口业务实现
 * Created by jason on 2016-10-18.
 */
@Service
public class VideoServiceImpl implements IVideoService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IVideoDao videoDao;

    @Autowired
    private ICemeteryDao cemeteryDao;

    @Autowired
    private CommonService commonService;

    /**
     * 为专辑添加视频
     *
     * @param videoEditRequest
     * @throws EqianyuanException
     */
    public void add(VideoEditRequest videoEditRequest) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        Cemetery cemetery = commonService.hasPermissionsByCemetery(videoEditRequest.getCemeteryId(), videoEditRequest.getMemberId());

        //将纪念人头像文件从临时上传目录移动到持久目录
        String absoluteDirectory = SessionUtil.getSession().getServletContext().getRealPath("/");

        //获得当前陵园剩余容量(bit)
        int remainingStorageSize = cemetery.getRemainingStorageSize();
        long fileSize = FileUtilHandle.getFileSize(absoluteDirectory + SystemConf.FILE_UPLOAD_TEMP_DIRECTORY.toString() + File.separator + videoEditRequest.getVideoName());
        if (remainingStorageSize <= 0 || remainingStorageSize < fileSize) {
            logger.info("陵园【" + cemetery.getName() + "】的可用容量不足");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_STORAGE_SIZE_INSUFFICIENT);
        }

        //扣减陵园剩余容量
        cemetery.setRemainingStorageSize((int) (remainingStorageSize - fileSize));
        cemeteryDao.updateByPrimaryKeySelective(cemetery);

        /**
         * 构建视频文件持久化目录地址
         * 目录结构：持久化上传目录/陵园编号/纪念人编号/video/文件
         */
        String videoPath = SystemConf.FILE_UPLOAD_FIXED_DIRECTORY.toString() + File.separator + videoEditRequest.getCemeteryId()
                + File.separator + videoEditRequest.getMasterId() + File.separator + "video";

        //插入视频数据
        Video video = new Video();
        video.setName(videoEditRequest.getName());
        video.setAlbumId(videoEditRequest.getAlbumId());
        video.setFileSize((int) fileSize);
        video.setUrl(videoPath + File.separator + videoEditRequest.getVideoName());
        video.setCreateTime(CalendarUtil.getSystemSeconds());
        videoDao.insertSelective(video);

        //将视频文件从临时上传目录移动到持久目录
        FileUtilHandle.moveFile(absoluteDirectory + SystemConf.FILE_UPLOAD_TEMP_DIRECTORY.toString() + File.separator + videoEditRequest.getVideoName(), videoPath);
    }

    /**
     * 根据视频编号删除视频数据
     *
     * @param videoId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    public void del(String videoId, String cemeteryId, Integer memberId) throws EqianyuanException {
        if (StringUtils.isEmpty(videoId)) {
            logger.warn("视频数据删除失败，因为视频编号不存在.");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
        }

        //根据视频编号查询视频数据
        Video video = videoDao.selectByPrimaryKey(videoId);

        if (!ObjectUtils.isEmpty(video)
                && !ObjectUtils.isEmpty(video.getId())) {
            //检查当前登录会员是否拥有对该陵园的管理权限
            Cemetery cemetery = commonService.hasPermissionsByCemetery(cemeteryId, memberId);

            //删除视频数据
            videoDao.deleteByPrimaryKey(videoId);

            //获取被删除视频附件容量(bit)
            int fileSize = video.getFileSize();

            //删除附件
            FileUtilHandle.deleteFile(SessionUtil.getSession().getServletContext().getRealPath("/") + video.getUrl());
            //回收附件空间容量
            cemetery.setRemainingStorageSize(cemetery.getRemainingStorageSize() + fileSize);
            //持久化陵园数据
            cemeteryDao.updateByPrimaryKeySelective(cemetery);
        }
    }

    /**
     * 根据专辑编号查询视频分页集合
     *
     * @param vodeoAlbumId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String vodeoAlbumId, Pager page) throws EqianyuanException {
        // 根据条件查询视频列表
        int dataCount = videoDao.countByCondition(vodeoAlbumId);
        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询纪念人专辑视频列表无数据");
            return new PageResponse(page, null);
        }
        List<Video> videos = videoDao.selectByCondition(vodeoAlbumId, page);
        if (CollectionUtils.isEmpty(videos)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询纪念人专辑视频列表无数据");
            return new PageResponse(page, null);
        }
        List<VideoBo> videoBos = new ArrayList<VideoBo>();
        for (Video m : videos) {
            VideoBo videoBo = new VideoBo();
            BeanUtils.copyProperties(m, videoBo);
            videoBos.add(videoBo);
        }
        return new PageResponse(page, videoBos);
    }

    /**
     * 根据视频编号查询视频详情
     *
     * @param vodeoId
     * @return
     * @throws EqianyuanException
     */
    public VideoBo getInfo(String vodeoId) throws EqianyuanException {
        Video video = videoDao.selectByPrimaryKey(vodeoId);
        if (ObjectUtils.isEmpty(video)
                || ObjectUtils.isEmpty(video.getId())) {
            logger.info("根据视频编号【" + vodeoId + "】查询数据为空");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_VIDEO_DATA_NOT_EXISTS);
        }

        VideoBo videoBo = new VideoBo();
        BeanUtils.copyProperties(video, videoBo);
        return videoBo;
    }


}
