package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.photoAlbum.photo.PhotoEditRequest;
import com.bestomb.common.response.FileResponse;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.photoAlbum.photo.PhotoBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.common.util.FileUtilHandle;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.ICemeteryDao;
import com.bestomb.dao.IPhotoDao;
import com.bestomb.entity.Cemetery;
import com.bestomb.entity.Photo;
import com.bestomb.service.IPhotoService;
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
 * 陵园纪念人相册相片接口业务实现
 * Created by jason on 2016-10-18.
 */
@Service
public class PhotoServiceImpl implements IPhotoService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IPhotoDao photoDao;

    @Autowired
    private ICemeteryDao cemeteryDao;

    @Autowired
    private CommonService commonService;

    /**
     * 为相册添加相片
     *
     * @param photoEditRequest
     * @throws EqianyuanException
     */
    public void add(PhotoEditRequest photoEditRequest) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        Cemetery cemetery = commonService.hasPermissionsByCemetery(photoEditRequest.getCemeteryId(), photoEditRequest.getMemberId());

        //获得当前陵园剩余容量(bit)
        int remainingStorageSize = cemetery.getRemainingStorageSize();
        if (remainingStorageSize <= 0 || remainingStorageSize < photoEditRequest.getPhotoFile().getSize()) {
            logger.info("陵园【" + cemetery.getName() + "】的可用容量不足");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_STORAGE_SIZE_INSUFFICIENT);
        }

        //相片上传
        FileResponse fileResponse = FileUtilHandle.upload(photoEditRequest.getPhotoFile());

        //扣减陵园剩余容量
        cemetery.setRemainingStorageSize((int) (remainingStorageSize - photoEditRequest.getPhotoFile().getSize()));
        cemeteryDao.updateByPrimaryKeySelective(cemetery);

        /**
         * 构建相片文件持久化目录地址
         * 目录结构：持久化上传目录/陵园编号/纪念人编号/photo/文件
         */
        String phoptoPath = SystemConf.FILE_UPLOAD_FIXED_DIRECTORY.toString() + File.separator + photoEditRequest.getCemeteryId() + File.separator + photoEditRequest.getMasterId() + File.separator + "photo";

        //插入相片数据
        Photo photo = new Photo();
        photo.setName(photoEditRequest.getName());
        photo.setAlbumId(photoEditRequest.getAlbumId());
        photo.setFileSize((int) photoEditRequest.getPhotoFile().getSize());
        photo.setUrl(phoptoPath + File.separator + fileResponse.getFileName());
        photo.setCreateTime(CalendarUtil.getSystemSeconds());
        photoDao.insertSelective(photo);

        //将相片文件从临时上传目录移动到持久目录
        FileUtilHandle.moveFile(fileResponse.getFilePath(), phoptoPath);
    }

    /**
     * 根据相片编号删除相片数据
     *
     * @param photoId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    public void del(String photoId, String cemeteryId, Integer memberId) throws EqianyuanException {
        if (StringUtils.isEmpty(photoId)) {
            logger.warn("相片数据删除失败，因为相片编号不存在.");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_LACK_OF_REQUEST_PARAMETER);
        }

        //根据相片编号查询相片数据
        Photo photo = photoDao.selectByPrimaryKey(photoId);

        if (!ObjectUtils.isEmpty(photo)
                && !ObjectUtils.isEmpty(photo.getId())) {
            //检查当前登录会员是否拥有对该陵园的管理权限
            Cemetery cemetery = commonService.hasPermissionsByCemetery(cemeteryId, memberId);

            //删除相片数据
            photoDao.deleteByPrimaryKey(photoId);

            //获取被删除相片附件容量(bit)
            int fileSize = photo.getFileSize();

            //删除附件
            FileUtilHandle.deleteFile(SessionUtil.getSession().getServletContext().getRealPath("/") + photo.getUrl());
            //回收附件空间容量
            cemetery.setRemainingStorageSize(cemetery.getRemainingStorageSize() + fileSize);
            //持久化陵园数据
            cemeteryDao.updateByPrimaryKeySelective(cemetery);
        }
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
        // 根据条件查询相册列表
        int dataCount = photoDao.countByCondition(photoAlbumId);
        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询纪念人相册相片列表无数据");
            return new PageResponse(page, null);
        }
        List<Photo> photos = photoDao.selectByCondition(photoAlbumId, page);
        if (CollectionUtils.isEmpty(photos)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询纪念人相册相片列表无数据");
            return new PageResponse(page, null);
        }
        List<PhotoBo> photoBos = new ArrayList<PhotoBo>();
        for (Photo m : photos) {
            PhotoBo photoBo = new PhotoBo();
            BeanUtils.copyProperties(m, photoBo);
            photoBos.add(photoBo);
        }
        return new PageResponse(page, photoBos);
    }

    /**
     * 根据相片编号查询相片详情
     *
     * @param photoId
     * @return
     * @throws EqianyuanException
     */
    public PhotoBo getInfo(String photoId) throws EqianyuanException {
        Photo photo = photoDao.selectByPrimaryKey(photoId);
        if (ObjectUtils.isEmpty(photo)
                || ObjectUtils.isEmpty(photo.getId())) {
            logger.info("根据相片编号【" + photoId + "】查询数据为空");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_PHOTO_DATA_NOT_EXISTS);
        }

        PhotoBo photoBo = new PhotoBo();
        BeanUtils.copyProperties(photo, photoBo);
        return photoBo;
    }


}
