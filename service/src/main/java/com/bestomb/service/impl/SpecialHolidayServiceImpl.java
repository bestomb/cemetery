package com.bestomb.service.impl;

import com.bestomb.common.Page;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.specialholiday.SpecialHolidayRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.specialholiday.SpecialHolidayBo;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.ISpecialHolidayDao;
import com.bestomb.entity.SpecialHoliday;
import com.bestomb.service.ISpecialHolidayService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialHolidayServiceImpl implements ISpecialHolidayService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ISpecialHolidayDao specialHolidayDao;

    //特殊节日推送语字节长度
    private static final int HOLIDAY_PUSH_MAXIMUM_BYTE = 300;

    /**
     * 分页查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(int pageNo, int pageSize) throws EqianyuanException {
        Long dataCount = specialHolidayDao.countByPagination();
        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }
        Page page = new Page(pageNo, pageSize);
        List<SpecialHoliday> specialHolidayList = specialHolidayDao.selectByPagination(page);
        if (CollectionUtils.isEmpty(specialHolidayList)) {
            logger.warn("pageNo [" + pageNo + "], pageSize [" + pageSize + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }
        List<SpecialHolidayBo> specialHolidayBoList = new ArrayList<SpecialHolidayBo>();
        for (SpecialHoliday specialHoliday : specialHolidayList) {
            SpecialHolidayBo specialHolidayBo = new SpecialHolidayBo();
            BeanUtils.copyProperties(specialHoliday, specialHolidayBo);
            specialHolidayBoList.add(specialHolidayBo);
        }
        return new PageResponse(pageNo, pageSize, dataCount, specialHolidayBoList);
    }

    /**
     * 添加特殊节日
     * @param specialHolidayRequest
     * @throws EqianyuanException
     */
    public void add(SpecialHolidayRequest specialHolidayRequest) throws EqianyuanException {
        //判断特殊节日长度大小
        try{
            if (specialHolidayRequest.getMessage().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > HOLIDAY_PUSH_MAXIMUM_BYTE) {
                logger.info("add fail , because real message [" + specialHolidayRequest.getMessage()
                        + "] bytes greater than" + HOLIDAY_PUSH_MAXIMUM_BYTE);
                throw new EqianyuanException(ExceptionMsgConstant.HOLIDAY_PUSH_LANGUAGE_CONTENT_IS_TOO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because real message [" + specialHolidayRequest.getMessage()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }
        SpecialHoliday specialHoliday = new SpecialHoliday();
        BeanUtils.copyProperties(specialHolidayRequest,specialHoliday);
        specialHolidayDao.insertSelective(specialHoliday);
    }

    /**
     * 删除特殊节日
     * @param id
     * @throws EqianyuanException
     */
    public void removeByIds(String... id) throws EqianyuanException {
        if(ObjectUtils.isEmpty(id)){
            logger.info("removeByIds fail , because id is null, a full table delete is prohibited");
            throw new EqianyuanException(ExceptionMsgConstant.SPECIAL_HOLIDAY_ID_IS_EMPTY);
        }
        //删除信息
        specialHolidayDao.deleteByPrimaryKey(id);
    }
}
