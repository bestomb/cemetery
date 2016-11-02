package com.bestomb.service.impl;

import com.bestomb.common.Page;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.MasterEditRequest;
import com.bestomb.common.response.FileResponse;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.MasterBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.common.util.FileUtilHandle;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.IMasterDao;
import com.bestomb.entity.Master;
import com.bestomb.entity.MasterWithBLOBs;
import com.bestomb.service.IMasterService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MasterServiceImpl implements IMasterService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IMasterDao masterDao;

    @Autowired
    private CommonService commonService;

    /**
     * 根据陵园编号和分页信息查询陵园纪念人分页查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(int pageNo, int pageSize, Integer cemeteryId) throws EqianyuanException {
        Long dataCount = masterDao.countByPagination(cemeteryId);
        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }
        Page page = new Page(pageNo, pageSize);
        List<MasterWithBLOBs> masterList = masterDao.selectByPagination(page, cemeteryId);
        if (CollectionUtils.isEmpty(masterList)) {
            logger.warn("pageNo [" + pageNo + "], pageSize [" + pageSize + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }
        List<MasterBo> masterBoList = new ArrayList<MasterBo>();
        for (MasterWithBLOBs masterWithBLOBs : masterList) {
            MasterBo masterBo = new MasterBo();
            BeanUtils.copyProperties(masterWithBLOBs, masterBo);
            masterBoList.add(masterBo);
        }
        return new PageResponse(pageNo, pageSize, dataCount, masterBoList);
    }

    /**
     * 详细信息查询
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    public MasterBo queryById(String id) throws EqianyuanException {
        //主键是否为空
        if (StringUtils.isEmpty(id)) {
            logger.info("queryById fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_TOMBSTONE_INFORMATION_ID_IS_EMPTY);
        }
        //根据主键查询数据
        MasterWithBLOBs masterWithBLOBs = masterDao.selectByPrimaryKey(id);

        if (ObjectUtils.isEmpty(masterWithBLOBs)
                || StringUtils.isEmpty(masterWithBLOBs.getId())) {
            logger.info("编号" + id + "查询墓碑纪念人信息为空");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_MASTER_DATA_NOT_EXISTS);
        }

        MasterBo masterBo = new MasterBo();
        BeanUtils.copyProperties(masterWithBLOBs, masterBo);
//        masterBo.setDeathTime(CalendarUtil.secondsTimeToDateTimeString(masterWithBLOBs.getDeathTime()));
        return masterBo;
    }

    /**
     * 根据陵园墓碑编号查询墓中纪念人信息
     *
     * @param tombstoneId
     * @return
     * @throws EqianyuanException
     */
    public List<MasterBo> queryByTombstone(String tombstoneId) throws EqianyuanException {
        if (StringUtils.isEmpty(tombstoneId)) {
            logger.info("queryByTombstone fail , because tombstoneId is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_TOMBSTONE_INFORMATION_ID_IS_EMPTY);
        }
        //根据墓碑编号查询墓中纪念人信息
        List<Master> masters = masterDao.selectByTombstone(tombstoneId);

        if (CollectionUtils.isEmpty(masters)) {
            logger.info("根据墓碑编号查询墓中纪念人集合数据失败");
            return Collections.EMPTY_LIST;
        }
        List<MasterBo> masterBos = new ArrayList<MasterBo>();
        for (Master m : masters) {
            MasterBo masterBo = new MasterBo();
            BeanUtils.copyProperties(m, masterBo);
            masterBos.add(masterBo);
        }
        return masterBos;
    }

    /**
     * 添加墓碑纪念人
     *
     * @param masterEditRequest
     * @throws EqianyuanException
     */
    public void addMaster(MasterEditRequest masterEditRequest) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(String.valueOf(masterEditRequest.getCemeteryId()), masterEditRequest.getOperatorMemberId());

        //纪念人头像上传
        FileResponse fileResponse = FileUtilHandle.upload(masterEditRequest.getPortraitFile());

        /**
         * 构建头像文件持久化目录地址
         * 目录结构：持久化上传目录/陵园编号/master_portrait/文件
         */
        String portraitPath = SystemConf.FILE_UPLOAD_FIXED_DIRECTORY.toString() + File.separator + masterEditRequest.getCemeteryId() + File.separator + "master_portrait";

        //构建持久化墓碑中纪念人数据
        MasterWithBLOBs master = new MasterWithBLOBs();
        master.setSort(masterEditRequest.getSort());
        master.setName(master.getName());
        master.setPortrait(portraitPath + File.separator + fileResponse.getFileName());
        master.setTombstoneId(masterEditRequest.getTombstoneId());
        master.setBirthday(CalendarUtil.getSecondsByDate(masterEditRequest.getBirthday()));
        master.setDeathTime(CalendarUtil.getSecondsByDate(masterEditRequest.getDeathTime()));
        master.setLifeIntroduce(masterEditRequest.getLifeIntroduce());
        master.setLastWish(masterEditRequest.getLastWish());
        //持久化纪念人数据
        masterDao.insertSelective(master);

        //将纪念人头像文件从临时上传目录移动到持久目录
        FileUtilHandle.moveFile(fileResponse.getFilePath(), portraitPath);
    }

    /**
     * 修改墓碑纪念人信息
     *
     * @param masterEditRequest
     * @throws EqianyuanException
     */
    public void modifyMaster(MasterEditRequest masterEditRequest) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(String.valueOf(masterEditRequest.getCemeteryId()), masterEditRequest.getOperatorMemberId());

        //根据纪念人编号查询纪念人信息
        MasterWithBLOBs master = masterDao.selectByPrimaryKey(masterEditRequest.getId());
        if (ObjectUtils.isEmpty(master) || ObjectUtils.isEmpty(master.getId())) {
            logger.info("modifyMaster fail , because master id [" + masterEditRequest.getId() + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_MASTER_DATA_NOT_EXISTS);
        }

        /**
         * 构建头像文件持久化目录地址
         * 目录结构：持久化上传目录/陵园编号/master_portrait/文件
         */
        String portraitPath = SystemConf.FILE_UPLOAD_FIXED_DIRECTORY.toString() + File.separator + masterEditRequest.getCemeteryId() + File.separator + "master_portrait";

        FileResponse fileResponse = null;
        try {
            //纪念人头像上传
            fileResponse = FileUtilHandle.upload(masterEditRequest.getPortraitFile());
            master.setPortrait(portraitPath + File.separator + fileResponse.getFileName());
        } catch (EqianyuanException e) {
            logger.info("纪念人头像上传失败");
        }

        master.setSort(masterEditRequest.getSort());
        master.setName(masterEditRequest.getName());
        master.setBirthday(CalendarUtil.getSecondsByDate(masterEditRequest.getBirthday()));
        master.setDeathTime(CalendarUtil.getSecondsByDate(masterEditRequest.getDeathTime()));
        master.setLifeIntroduce(masterEditRequest.getLifeIntroduce());
        master.setLastWish(masterEditRequest.getLastWish());
        //持久化纪念人数据
        masterDao.updateByPrimaryKeySelective(master);

        if (fileResponse != null) {
            //将纪念人头像文件从临时上传目录移动到持久目录
            FileUtilHandle.moveFile(fileResponse.getFilePath(), portraitPath);
            //将纪念人旧头像文件从持久目录删除
            FileUtilHandle.deleteFile(SessionUtil.getSession().getServletContext().getRealPath("/") + master.getPortrait());
        }
    }

    /**
     * 删除墓碑纪念人
     *
     * @param masterId
     * @param cemeteryId
     * @param memberId
     * @throws EqianyuanException
     */
    public void delMaster(String masterId, String cemeteryId, Integer memberId) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        commonService.hasPermissionsByCemetery(cemeteryId, memberId);

        //根据纪念人编号查询纪念人信息
        MasterWithBLOBs master = masterDao.selectByPrimaryKey(masterId);
        if (ObjectUtils.isEmpty(master) || ObjectUtils.isEmpty(master.getId())) {
            logger.info("delMaster fail , because master id [" + masterId + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_MASTER_DATA_NOT_EXISTS);
        }

        //删除数据
        masterDao.deleteByPrimaryKey(masterId);

        //将纪念人头像文件从持久目录删除
        FileUtilHandle.deleteFile(SessionUtil.getSession().getServletContext().getRealPath("/") + master.getPortrait());

        //todo 删除墓中纪念人相关联的数据
    }
}