package com.bestomb.service.impl;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.cemetery.CemeteryByEditRequest;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.common.util.YamlForMapHandleUtil;
import com.bestomb.common.util.yamlMapper.ClientConf;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.*;
import com.bestomb.entity.*;
import com.bestomb.service.ICemeteryService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;

/**
 * 陵园业务逻辑接口实现类
 * Created by jason on 2016-07-15.
 */
@Service
public class CemeteryServiceImpl implements ICemeteryService {

    private Logger logger = Logger.getLogger(this.getClass());

    //陵园是否公开状态-公开
    private static final int CEMETERY_IS_OPEN_BY_PUBLIC = 1;
    //陵园是否公开状态-不公开
    private static final int CEMETERY_IS_OPEN_BY_PRIVATE = 0;
    //陵园名称DB许可字节长度
    private static final int CEMETERY_NAME_MAX_BYTES_BY_DB = 100;
    //陵园密码DB许可字节长度
    private static final int CEMETERY_PASSWORD_MAX_BYTES_BY_DB = 14;

    @Autowired
    private ICemeteryDao cemeteryDao;

    @Autowired
    private IProvinceDao provinceDao;

    @Autowired
    private ICityDao cityDao;

    @Autowired
    private ICountyDao countyDao;

    @Autowired
    private ITownDao townDao;

    @Autowired
    private IVillageDao villageDao;

    @Autowired
    private ICommunityDao communityDao;

    /**
     * 添加陵园
     *
     * @param cemeteryByEditRequest
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(CemeteryByEditRequest cemeteryByEditRequest) throws EqianyuanException {
        //陵园名称是否为空
        if (StringUtils.isEmpty(cemeteryByEditRequest.getName())) {
            logger.warn("add fail , because name is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_NAME_IS_EMPTY);
        }

        //陵园归属（省）是否为空
        if (StringUtils.isEmpty(cemeteryByEditRequest.getProvinceId())) {
            logger.warn("add fail , because province id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_PROVINCE_IS_EMPTY);
        }

        //陵园归属（市）是否为空
        if (StringUtils.isEmpty(cemeteryByEditRequest.getCityId())) {
            logger.warn("add fail , because city id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_CITY_IS_EMPTY);
        }

        //陵园归属（区）是否为空
        if (StringUtils.isEmpty(cemeteryByEditRequest.getCountyId())) {
            logger.warn("add fail , because county id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_COUNTY_IS_EMPTY);
        }

        //陵园归属（乡）是否为空
        if (StringUtils.isEmpty(cemeteryByEditRequest.getTownName())) {
            logger.warn("add fail , because town is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_TOWN_IS_EMPTY);
        }

        //陵园归属（村）是否为空
        if (StringUtils.isEmpty(cemeteryByEditRequest.getVillageName())) {
            logger.warn("add fail , because village is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_VILLAGE_IS_EMPTY);
        }

        //陵园归属（社）是否为空
        if (StringUtils.isEmpty(cemeteryByEditRequest.getCommunityName())) {
            logger.warn("add fail , because community id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_COMMUNITY_IS_EMPTY);
        }

        if (!ObjectUtils.isEmpty(cemeteryByEditRequest.getIsOpen())) {
            //判断陵园是否为私有的非公开的
            if (cemeteryByEditRequest.getIsOpen() == CEMETERY_IS_OPEN_BY_PRIVATE) {
                //陵园密码是否为空
                if (StringUtils.isEmpty(cemeteryByEditRequest.getPassword())) {
                    logger.warn("add fail , because community id is null.");
                    throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_PASSWORD_IS_EMPTY);
                }
            }
        }

        //陵园名称内容长度是否超出DB许可长度
        try {
            if (cemeteryByEditRequest.getName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > CEMETERY_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because name [" + cemeteryByEditRequest.getName()
                        + "] bytes greater than" + CEMETERY_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + cemeteryByEditRequest.getName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //陵园密码内容长度是否超出DB许可长度
        try {
            if (cemeteryByEditRequest.getPassword().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > CEMETERY_PASSWORD_MAX_BYTES_BY_DB) {
                logger.info("add fail , because password [" + cemeteryByEditRequest.getPassword()
                        + "] bytes greater than" + CEMETERY_PASSWORD_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_PASSWORD_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + cemeteryByEditRequest.getName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //判断客户端配置默认社区名称可重命名次数是否存在
        Object renameCount = YamlForMapHandleUtil.getMapByKey(ClientConf.getMap(), ClientConf.Cemetery.renameCount.toString());
        if (ObjectUtils.isEmpty(renameCount)) {
            logger.warn("batchSend fail , because renameCount not exists the client-conf.yaml");
            throw new EqianyuanException(ExceptionMsgConstant.GET_CONFIGURATION_ERROR);
        }

        //判断客户端配置初始化陵园容量大小是否存在
        Object initStorageSize = YamlForMapHandleUtil.getMapByKey(ClientConf.getMap(), ClientConf.Cemetery.init_storage_size.toString());
        if (ObjectUtils.isEmpty(initStorageSize)) {
            logger.warn("batchSend fail , because init_storage_size not exists the client-conf.yaml");
            throw new EqianyuanException(ExceptionMsgConstant.GET_CONFIGURATION_ERROR);
        }

        //根据省编号查询省数据，检查编号正确性
        Province province = provinceDao.selectById(cemeteryByEditRequest.getProvinceId());
        if (ObjectUtils.isEmpty(province)) {
            logger.info("add fail , because select province by province id [" + cemeteryByEditRequest.getProvinceId() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_PROVINCE_DATA_NO_EXISTS);
        }

        //根据市编号查询市数据，检查编号正确性
        City city = cityDao.selectById(cemeteryByEditRequest.getProvinceId(), cemeteryByEditRequest.getCityId());
        if (ObjectUtils.isEmpty(city)) {
            logger.info("add fail , because select city by city id [" + cemeteryByEditRequest.getCityId() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_CITY_DATA_NO_EXISTS);
        }

        //根据区编号查询区数据，检查编号正确性
        County county = countyDao.selectById(cemeteryByEditRequest.getCityId(), cemeteryByEditRequest.getCountyId());
        if (ObjectUtils.isEmpty(county)) {
            logger.info("add fail , because select county by county id [" + cemeteryByEditRequest.getCountyId() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_COUNTY_DATA_NO_EXISTS);
        }

        //构建陵园DB对象
        Cemetery cemetery = new Cemetery();
        BeanUtils.copyProperties(cemeteryByEditRequest, cemetery);
        cemetery.setCreateTime(CalendarUtil.getSystemSeconds());

        //根据区编号和乡名称查询乡数据
        Town town = townDao.selectByName(cemeteryByEditRequest.getCountyId(), cemeteryByEditRequest.getTownName());
        Village village;
        Community community;
        //判断是否存在乡数据，如不存在，则插入一条乡数据
        if (ObjectUtils.isEmpty(town)
                || StringUtils.isEmpty(town.getId())) {
            town = new Town();
            town.setCountyId(cemeteryByEditRequest.getCountyId());
            town.setName(cemeteryByEditRequest.getTownName());

            //增加乡数据
            townDao.insertSelective(town);
            //乡数据都不存在的话，村和社是肯定不存在，所以，可以直接插入村和社数据
            village = new Village();
            village.setTownId(town.getId());
            village.setName(cemeteryByEditRequest.getVillageName());
            villageDao.insertSelective(village);

            community = new Community();
            community.setVillageId(village.getId());
            community.setName(cemeteryByEditRequest.getCommunityName());
            community.setRenameCount(Integer.parseInt(String.valueOf(renameCount)));
            communityDao.insertSelective(community);

            cemetery.setTownId(town.getId());
            cemetery.setCommunityId(community.getId());
            cemetery.setStorageSize(Integer.parseInt(String.valueOf(initStorageSize)));
            cemetery.setRemainingStorageSize(Integer.parseInt(String.valueOf(initStorageSize)));
            cemeteryDao.insertSelective(cemetery);
            return;
        }

        //根据乡编号和村名称查询村数据
        village = villageDao.selectByName(town.getId(), cemeteryByEditRequest.getVillageName());

        //判断是否存在村数据，如不存在，则插入一条村数据
        if (ObjectUtils.isEmpty(village)
                || StringUtils.isEmpty(village.getId())) {
            village = new Village();
            village.setTownId(town.getId());
            village.setName(cemeteryByEditRequest.getTownName());

            //增加村数据
            villageDao.insertSelective(village);
            //村数据都不存在的话，社是肯定不存在，所以，可以直接插入社数据
            community = new Community();
            community.setVillageId(village.getId());
            community.setName(cemeteryByEditRequest.getCommunityName());
            community.setRenameCount(Integer.parseInt(String.valueOf(renameCount)));
            communityDao.insertSelective(community);

            cemetery.setTownId(town.getId());
            cemetery.setCommunityId(community.getId());
            cemetery.setStorageSize(Integer.parseInt(String.valueOf(initStorageSize)));
            cemetery.setRemainingStorageSize(Integer.parseInt(String.valueOf(initStorageSize)));
            cemeteryDao.insertSelective(cemetery);
            return;
        }

        //根据村编号和社名称查询社数据
        community = communityDao.selectByName(village.getId(), cemeteryByEditRequest.getCommunityName());

        //判断是否存在社数据，如不存在，则插入一条社数据
        if (ObjectUtils.isEmpty(community)
                || StringUtils.isEmpty(community.getId())) {
            community = new Community();
            community.setVillageId(village.getId());
            community.setName(cemeteryByEditRequest.getCommunityName());
            community.setRenameCount(Integer.parseInt(String.valueOf(renameCount)));
            communityDao.insertSelective(community);

            cemetery.setTownId(town.getId());
            cemetery.setCommunityId(community.getId());
            cemetery.setStorageSize(Integer.parseInt(String.valueOf(initStorageSize)));
            cemetery.setRemainingStorageSize(Integer.parseInt(String.valueOf(initStorageSize)));
            cemeteryDao.insertSelective(cemetery);
            return;
        }

    }
}
