package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.photoAlbum.PhotoAlbumEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.photoAlbum.PhotoAlbumBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.IPhotoAlbumDao;
import com.bestomb.entity.PhotoAlbum;
import com.bestomb.service.IPhotoAlbumService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 陵园纪念人相册接口业务实现
 * Created by jason on 2016-10-18.
 */
@Service
public class PhotoAlbumServiceImpl implements IPhotoAlbumService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IPhotoAlbumDao photoAlbumDao;

    @Autowired
    private CommonService commonService;

    /**
     * 创建纪念人相册
     *
     * @param photoAlbumEditRequest
     * @throws EqianyuanException
     */
    public void add(PhotoAlbumEditRequest photoAlbumEditRequest) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(photoAlbumEditRequest.getCemeteryId(), photoAlbumEditRequest.getMemberId());

        //构建持久化相册数据
        PhotoAlbum photoAlbum = new PhotoAlbum();
        photoAlbum.setMasterId(photoAlbumEditRequest.getMasterId());
        photoAlbum.setName(photoAlbumEditRequest.getName());
        photoAlbum.setRemark(photoAlbumEditRequest.getRemark());
        photoAlbum.setCreateTime(CalendarUtil.getSystemSeconds());
        //持久陵园纪念人相册数据
        photoAlbumDao.insertSelective(photoAlbum);
    }

    /**
     * 根据相册编号删除数据
     *
     * @param photoAlbumId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    public void del(String photoAlbumId, String cemeteryId, Integer memberId) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(cemeteryId, memberId);

        //删除数据
        photoAlbumDao.deleteByPrimaryKey(photoAlbumId);
        //todo 需要删除该相册的所有相片相关数据/附件等
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
        // 根据条件查询相册列表
        int dataCount = photoAlbumDao.countByCondition(masterId);
        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询陵园纪念人相册列表无数据");
            return new PageResponse(page, null);
        }
        List<PhotoAlbum> photoAlba = photoAlbumDao.selectByCondition(masterId, page);
        if (CollectionUtils.isEmpty(photoAlba)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询陵园纪念人相册列表无数据");
            return new PageResponse(page, null);
        }
        List<PhotoAlbumBo> photoAlbumBos = new ArrayList<PhotoAlbumBo>();
        for (PhotoAlbum m : photoAlba) {
            PhotoAlbumBo photoAlbumBo = new PhotoAlbumBo();
            BeanUtils.copyProperties(m, photoAlbumBo);
            photoAlbumBos.add(photoAlbumBo);
        }
        return new PageResponse(page, photoAlbumBos);
    }


}
