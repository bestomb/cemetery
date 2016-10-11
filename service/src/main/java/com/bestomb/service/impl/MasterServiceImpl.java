package com.bestomb.service.impl;

import com.bestomb.common.Page;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.MasterBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.IMasterDao;
import com.bestomb.entity.MasterWithBLOBs;
import com.bestomb.service.IMasterService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MasterServiceImpl implements IMasterService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IMasterDao masterDao;

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(int pageNo, int pageSize,Integer cemeteryId) throws EqianyuanException {
        Long dataCount = masterDao.countByPagination();
        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }
        Page page = new Page(pageNo, pageSize);
        List<MasterWithBLOBs> masterList = masterDao.selectByPagination(page,cemeteryId);
        if (CollectionUtils.isEmpty(masterList)) {
            logger.warn("pageNo [" + pageNo + "], pageSize [" + pageSize + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }
        List<MasterBo> masterBoList = new ArrayList<MasterBo>();
        for (MasterWithBLOBs masterWithBLOBs : masterList){
            MasterBo masterBo = new MasterBo();
            BeanUtils.copyProperties(masterWithBLOBs, masterBo);
            masterBoList.add(masterBo);
        }
        return new PageResponse(pageNo, pageSize, dataCount, masterBoList);
    }

    /**
     * 详细信息查询
     * @param id
     * @return
     * @throws EqianyuanException
     */
    public MasterBo queryById(String id) throws EqianyuanException {
        //主键是否为空
        if (StringUtils.isEmpty(id)){
            logger.info("queryById fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_TOMBSTONE_INFORMATION_ID_IS_EMPTY);
        }
        //根据主键查询数据
        MasterWithBLOBs masterWithBLOBs = masterDao.selectByPrimaryKey(id);
        MasterBo masterBo = new MasterBo();
        BeanUtils.copyProperties(masterWithBLOBs, masterBo);
        masterBo.setDeathTime(CalendarUtil.secondsTimeToDateTimeString(masterWithBLOBs.getDeathTime()));
        return masterBo;
    }
}
