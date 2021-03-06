package com.bestomb.service.impl;

import com.bestomb.common.Page;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.cemetery.CemeteryByAreaListRequest;
import com.bestomb.common.request.cemetery.CemeteryByEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.cemetery.CemeteryBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.common.util.VIPIDFilterUtil;
import com.bestomb.common.util.YamlForMapHandleUtil;
import com.bestomb.common.util.yamlMapper.ClientConf;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.dao.*;
import com.bestomb.entity.*;
import com.bestomb.service.ICemeteryService;
import com.bestomb.service.ITombstoneService;
import org.apache.commons.lang.StringUtils;
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
    //陵园乡DB许可字节长度
    private static final int CEMETERY_TOWN_NAME_MAX_BYTES_BY_DB = 64;
    //陵园村DB许可字节长度
    private static final int CEMETERY_VILLAGE_NAME_MAX_BYTES_BY_DB = 64;
    //陵园社DB许可字节长度
    private static final int CEMETERY_COMMUNITY_NAME_MAX_BYTES_BY_DB = 64;

    @Autowired
    private ICemeteryDao cemeteryDao;

    @Autowired
    private IMemberAccountDao memberAccountDao;

    @Autowired
    private ICemeteryIdBuildDao cemeteryIdBuildDao;

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

    @Autowired
    private CommonService commonService;

    @Autowired
    private ITombstoneService tombstoneService;

    /**
     * 添加陵园
     *
     * @param cemeteryByEditRequest
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(CemeteryByEditRequest cemeteryByEditRequest) throws EqianyuanException {
        //根据会员编号查询会员以建设陵园总数
        Long cemeteryCount = cemeteryDao.countByMemberId(cemeteryByEditRequest.getMemberId());

        //根据会员编号查询会员可建设陵园总数
        MemberAccount memberAccount = memberAccountDao.selectByPrimaryKey(Integer.parseInt(cemeteryByEditRequest.getMemberId()));

        //检查当前会员已建陵园总数是否超出当前会员可建设总数
        if (!ObjectUtils.isEmpty(cemeteryCount)
                && cemeteryCount >= memberAccount.getConstructionCount()) {
            logger.warn("add fail , because the cemetery count by memberId [" + memberAccount.getMemberId() + "] is beyond allows you to create");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_CONSTRUCTION_COUNT_TO_LONG);
        }

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

        //陵园乡内容长度是否超出DB许可长度
        try {
            if (cemeteryByEditRequest.getTownName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > CEMETERY_TOWN_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because townName [" + cemeteryByEditRequest.getTownName()
                        + "] bytes greater than" + CEMETERY_TOWN_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_TOWN_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + cemeteryByEditRequest.getTownName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //陵园村内容长度是否超出DB许可长度
        try {
            if (cemeteryByEditRequest.getVillageName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > CEMETERY_VILLAGE_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because villageName [" + cemeteryByEditRequest.getVillageName()
                        + "] bytes greater than" + CEMETERY_VILLAGE_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_VILLAGE_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + cemeteryByEditRequest.getVillageName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //陵园社内容长度是否超出DB许可长度
        try {
            if (cemeteryByEditRequest.getCommunityName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > CEMETERY_COMMUNITY_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because communityName [" + cemeteryByEditRequest.getCommunityName()
                        + "] bytes greater than" + CEMETERY_COMMUNITY_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_COMMUNITY_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + cemeteryByEditRequest.getCommunityName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
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
        cemetery.setMemberId(Integer.parseInt(cemeteryByEditRequest.getMemberId()));
        cemetery.setCreateTime(CalendarUtil.getSystemSeconds());
        cemetery.setStorageSize(Integer.parseInt(String.valueOf(initStorageSize)));
        cemetery.setRemainingStorageSize(Integer.parseInt(String.valueOf(initStorageSize)));

        do {
            //构建一个编号并设置状态为：1=未使用
            CemeteryIdBuild cemeteryIdBuild = new CemeteryIdBuild();
            cemeteryIdBuild.setStatus(1);

            //获取陵园编号
            cemeteryIdBuildDao.insertSelective(cemeteryIdBuild);
            Integer cemeteryId = cemeteryIdBuild.getId();

            //检查编号是否为靓号，如果是靓号，则重新获取编号
            if (VIPIDFilterUtil.doFilter(String.valueOf(cemeteryId))) {
                continue;
            }

            cemetery.setId(cemeteryId);

            //更新会员账号为：2=已使用
            cemeteryIdBuild.setStatus(2);
            cemeteryIdBuildDao.updateByPrimaryKeySelective(cemeteryIdBuild);
            break;
        } while (true);

        //根据区编号和乡名称查询乡数据
        Town town = townDao.selectByName(cemeteryByEditRequest.getCountyId(), cemeteryByEditRequest.getTownName());
        //判断是否存在乡数据，如不存在，则插入一条乡数据
        if (ObjectUtils.isEmpty(town)
                || StringUtils.isEmpty(town.getId())) {
            town = addTown(cemeteryByEditRequest.getCountyId(), cemeteryByEditRequest.getTownName());
            //乡数据都不存在的话，村和社是肯定不存在，所以，可以直接插入村和社数据
            Village village = addVillage(town.getId(), cemeteryByEditRequest.getVillageName());
            Community community = addCommunity(village.getId(), cemeteryByEditRequest.getCommunityName(), Integer.parseInt(String.valueOf(renameCount)));
            setAddress(cemetery, town, village, community);
            cemeteryDao.insertSelective(cemetery);
            return;
        }

        //根据乡编号和村名称查询村数据
        Village village = villageDao.selectByName(town.getId(), cemeteryByEditRequest.getVillageName());
        //判断是否存在村数据，如不存在，则插入一条村数据
        if (ObjectUtils.isEmpty(village)
                || StringUtils.isEmpty(village.getId())) {
            village = addVillage(town.getId(), cemeteryByEditRequest.getVillageName());
            //村数据都不存在的话，社是肯定不存在，所以，可以直接插入社数据
            Community community = addCommunity(village.getId(), cemeteryByEditRequest.getCommunityName(), Integer.parseInt(String.valueOf(renameCount)));
            setAddress(cemetery, town, village, community);
            cemeteryDao.insertSelective(cemetery);
            return;
        }

        //根据村编号和社名称查询社数据
        Community community = communityDao.selectByName(village.getId(), cemeteryByEditRequest.getCommunityName());
        //判断是否存在社数据，如不存在，则插入一条社数据
        if (ObjectUtils.isEmpty(community)
                || StringUtils.isEmpty(community.getId())) {
            community = addCommunity(village.getId(), cemeteryByEditRequest.getCommunityName(), Integer.parseInt(String.valueOf(renameCount)));
            setAddress(cemetery, town, village, community);
            cemeteryDao.insertSelective(cemetery);
            return;
        }

        setAddress(cemetery, town, village, community);
        cemeteryDao.insertSelective(cemetery);
    }

    /**
     * 修改陵园
     *
     * @param cemeteryByEditRequest
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(CemeteryByEditRequest cemeteryByEditRequest) throws EqianyuanException {
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
        //陵园乡内容长度是否超出DB许可长度
        try {
            if (cemeteryByEditRequest.getTownName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > CEMETERY_TOWN_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because townName [" + cemeteryByEditRequest.getTownName()
                        + "] bytes greater than" + CEMETERY_TOWN_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_TOWN_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + cemeteryByEditRequest.getTownName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //陵园村内容长度是否超出DB许可长度
        try {
            if (cemeteryByEditRequest.getVillageName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > CEMETERY_VILLAGE_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because villageName [" + cemeteryByEditRequest.getVillageName()
                        + "] bytes greater than" + CEMETERY_VILLAGE_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_VILLAGE_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + cemeteryByEditRequest.getVillageName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
        }

        //陵园社内容长度是否超出DB许可长度
        try {
            if (cemeteryByEditRequest.getCommunityName().getBytes(SystemConf.PLATFORM_CHARSET.toString()).length > CEMETERY_COMMUNITY_NAME_MAX_BYTES_BY_DB) {
                logger.info("add fail , because communityName [" + cemeteryByEditRequest.getCommunityName()
                        + "] bytes greater than" + CEMETERY_COMMUNITY_NAME_MAX_BYTES_BY_DB);
                throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_COMMUNITY_NAME_TO_LONG);
            }
        } catch (UnsupportedEncodingException e) {
            logger.info("add fail , because name [" + cemeteryByEditRequest.getCommunityName()
                    + "] getBytes(" + SystemConf.PLATFORM_CHARSET.toString() + ") fail");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_GET_BYTE_FAIL);
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
        cemetery.setMemberId(Integer.parseInt(cemeteryByEditRequest.getMemberId()));
        cemetery.setId(Integer.parseInt(cemeteryByEditRequest.getId()));
        cemetery.setStorageSize(Integer.parseInt(String.valueOf(initStorageSize)));
        cemetery.setRemainingStorageSize(Integer.parseInt(String.valueOf(initStorageSize)));

        //根据区编号和乡名称查询乡数据
        Town town = townDao.selectByName(cemeteryByEditRequest.getCountyId(), cemeteryByEditRequest.getTownName());
        //判断是否存在乡数据，如不存在，则插入一条乡数据
        if (ObjectUtils.isEmpty(town)
                || StringUtils.isEmpty(town.getId())) {
            town = addTown(cemeteryByEditRequest.getCountyId(), cemeteryByEditRequest.getTownName());
            //乡数据都不存在的话，村和社是肯定不存在，所以，可以直接插入村和社数据
            Village village = addVillage(town.getId(), cemeteryByEditRequest.getVillageName());
            Community community = addCommunity(village.getId(), cemeteryByEditRequest.getCommunityName(), Integer.parseInt(String.valueOf(renameCount)));
            setAddress(cemetery, town, village, community);
            cemeteryDao.updateByPrimaryKeySelective(cemetery);
            return;
        }

        //根据乡编号和村名称查询村数据
        Village village = villageDao.selectByName(town.getId(), cemeteryByEditRequest.getVillageName());
        //判断是否存在村数据，如不存在，则插入一条村数据
        if (ObjectUtils.isEmpty(village)
                || StringUtils.isEmpty(village.getId())) {
            village = addVillage(town.getId(), cemeteryByEditRequest.getVillageName());
            //村数据都不存在的话，社是肯定不存在，所以，可以直接插入社数据
            Community community = addCommunity(village.getId(), cemeteryByEditRequest.getCommunityName(), Integer.parseInt(String.valueOf(renameCount)));
            setAddress(cemetery, town, village, community);
            cemeteryDao.updateByPrimaryKeySelective(cemetery);
            return;
        }

        //根据村编号和社名称查询社数据
        Community community = communityDao.selectByName(village.getId(), cemeteryByEditRequest.getCommunityName());
        //判断是否存在社数据，如不存在，则插入一条社数据
        if (ObjectUtils.isEmpty(community)
                || StringUtils.isEmpty(community.getId())) {
            community = addCommunity(village.getId(), cemeteryByEditRequest.getCommunityName(), Integer.parseInt(String.valueOf(renameCount)));
            setAddress(cemetery, town, village, community);
            cemeteryDao.updateByPrimaryKeySelective(cemetery);
            return;
        }

        setAddress(cemetery, town, village, community);
        cemeteryDao.updateByPrimaryKeySelective(cemetery);
    }

    /**
     * 根据地区及分页信息查询陵园集合
     *
     * @param cemeteryByAreaListRequest
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageResponse getListByArea(CemeteryByAreaListRequest cemeteryByAreaListRequest, int pageNo, int pageSize) throws EqianyuanException {
        //陵园归属（省）是否为空
        if (StringUtils.isEmpty(cemeteryByAreaListRequest.getProvinceId())) {
            logger.warn("getListByArea fail , because province id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_PROVINCE_IS_EMPTY);
        }

        //陵园归属（市）是否为空
        if (StringUtils.isEmpty(cemeteryByAreaListRequest.getCityId())) {
            logger.warn("getListByArea fail , because city id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_CITY_IS_EMPTY);
        }

        //陵园归属（区）是否为空
        if (StringUtils.isEmpty(cemeteryByAreaListRequest.getCountyId())) {
            logger.warn("getListByArea fail , because county id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_COUNTY_IS_EMPTY);
        }

        //陵园归属（乡）是否为空
        if (StringUtils.isEmpty(cemeteryByAreaListRequest.getTownName())) {
            logger.warn("getListByArea fail , because town is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_TOWN_IS_EMPTY);
        }

        //陵园归属（村）是否为空
        if (StringUtils.isEmpty(cemeteryByAreaListRequest.getVillageName())) {
            logger.warn("getListByArea fail , because village is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_VILLAGE_IS_EMPTY);
        }

        //陵园归属（社）是否为空
        if (StringUtils.isEmpty(cemeteryByAreaListRequest.getCommunityName())) {
            logger.warn("getListByArea fail , because community id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_COMMUNITY_IS_EMPTY);
        }

        //根据省编号查询省数据，检查编号正确性
        Province province = provinceDao.selectById(cemeteryByAreaListRequest.getProvinceId());
        if (ObjectUtils.isEmpty(province)) {
            logger.info("getListByArea fail , because select province by province id [" + cemeteryByAreaListRequest.getProvinceId() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_PROVINCE_DATA_NO_EXISTS);
        }

        //根据市编号查询市数据，检查编号正确性
        City city = cityDao.selectById(cemeteryByAreaListRequest.getProvinceId(), cemeteryByAreaListRequest.getCityId());
        if (ObjectUtils.isEmpty(city)) {
            logger.info("getListByArea fail , because select city by city id [" + cemeteryByAreaListRequest.getCityId() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_CITY_DATA_NO_EXISTS);
        }

        //根据区编号查询区数据，检查编号正确性
        County county = countyDao.selectById(cemeteryByAreaListRequest.getCityId(), cemeteryByAreaListRequest.getCountyId());
        if (ObjectUtils.isEmpty(county)) {
            logger.info("getListByArea fail , because select county by county id [" + cemeteryByAreaListRequest.getCountyId() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_COUNTY_DATA_NO_EXISTS);
        }
        //根据区编号和乡名称查询乡数据
        Town town = townDao.selectByName(cemeteryByAreaListRequest.getCountyId(), cemeteryByAreaListRequest.getTownName());
        if (ObjectUtils.isEmpty(town)) {
            logger.info("getListByArea fail , because select county by town name [" + cemeteryByAreaListRequest.getTownName() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_TOWN_DATA_NO_EXISTS);
        }
        //根据乡编号和村名称查询村数据
        Village village = villageDao.selectByName(town.getId(), cemeteryByAreaListRequest.getVillageName());
        if (ObjectUtils.isEmpty(village)) {
            logger.info("getListByArea fail , because select county by town name [" + cemeteryByAreaListRequest.getTownName() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_VILLAGE_DATA_NO_EXISTS);
        }
        //根据村编号和社名称查询社数据
        Community community = communityDao.selectByName(village.getId(), cemeteryByAreaListRequest.getCommunityName());
        if (ObjectUtils.isEmpty(community)) {
            logger.info("getListByArea fail , because select county by town name [" + cemeteryByAreaListRequest.getTownName() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_COMMUNITY_DATA_NO_EXISTS);
        }

        //根据社区编号获取陵园总数
        Long dataCount = cemeteryDao.countByPagination(community.getId());

        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("communityId [" + community.getId() + "] get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        Page page = new Page(pageNo, pageSize);
        List<Cemetery> cemeteries = cemeteryDao.selectByPagination(page, community.getId());
        if (CollectionUtils.isEmpty(cemeteries)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "], communityId [" + community.getId() + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        List<CemeteryBo> cemeteryBos = getCemeteryBos(province, city, county, town, village, community, cemeteries);
        return new PageResponse(pageNo, pageSize, dataCount, cemeteryBos);
    }

    /**
     * 根据陵园编号查询陵园归属地陵园分页集合
     *
     * @param cemeteryId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageResponse getListByCemeteryId(String cemeteryId, int pageNo, int pageSize) throws EqianyuanException {
        if (StringUtils.isEmpty(cemeteryId)) {
            logger.warn("getListByCemeteryId fail , because cemeteryId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_ID_IS_EMPTY);
        }

        //根据陵园编号获取陵园
        Cemetery cemetery = cemeteryDao.selectByPrimaryKey(cemeteryId);
        if (ObjectUtils.isEmpty(cemetery)
                || ObjectUtils.isEmpty(cemetery.getId())) {
            logger.info("getListByCemeteryId fail , because cemeteryId [" + cemeteryId + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_DATA_NOT_EXISTS);
        }

        //根据社区编号获取陵园总数
        Long dataCount = cemeteryDao.countByPagination(cemetery.getCommunityId());

        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("communityId [" + cemetery.getCommunityId() + "] get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        //根据社区编号及陵园编号定位陵园数据所在数据行数
        Long positioningRow = cemeteryDao.selectByPositioning(cemetery.getCommunityId(), cemeteryId);
        //根据陵园定位行数计算pageNo
        pageNo = (int) (positioningRow % pageSize == 0 ? positioningRow / pageSize : positioningRow / pageSize + 1);

        Page page = new Page(pageNo, pageSize);
        List<Cemetery> cemeteries = cemeteryDao.selectByPagination(page, cemetery.getCommunityId());
        if (CollectionUtils.isEmpty(cemeteries)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "], communityId [" + cemetery.getCommunityId() + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        List<CemeteryBo> cemeteryBos = new ArrayList<CemeteryBo>();
        for (Cemetery cemeteryDo : cemeteries) {
            CemeteryBo cemeteryBo = new CemeteryBo();
            BeanUtils.copyProperties(cemeteryDo, cemeteryBo);
            cemeteryBos.add(cemeteryBo);
        }
        return new PageResponse(pageNo, pageSize, dataCount, cemeteryBos);
    }

    /**
     * 任意门
     *
     * @param cemeteryByAreaListRequest
     * @param behavior
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getListByArbitraryDoor(CemeteryByAreaListRequest cemeteryByAreaListRequest, String behavior, String pageNo, int pageSize) throws EqianyuanException {
        //陵园归属（省）是否为空
        if (StringUtils.isEmpty(cemeteryByAreaListRequest.getProvinceId())) {
            logger.warn("getListByArbitraryDoor fail , because province id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_PROVINCE_IS_EMPTY);
        }

        //陵园归属（市）是否为空
        if (StringUtils.isEmpty(cemeteryByAreaListRequest.getCityId())) {
            logger.warn("getListByArbitraryDoor fail , because city id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_CITY_IS_EMPTY);
        }

        //陵园归属（区）是否为空
        if (StringUtils.isEmpty(cemeteryByAreaListRequest.getCountyId())) {
            logger.warn("getListByArbitraryDoor fail , because county id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_COUNTY_IS_EMPTY);
        }

        //陵园归属（乡）是否为空
        if (StringUtils.isEmpty(cemeteryByAreaListRequest.getTownName())) {
            logger.warn("getListByArbitraryDoor fail , because town is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_TOWN_IS_EMPTY);
        }

        //陵园归属（村）是否为空
        if (StringUtils.isEmpty(cemeteryByAreaListRequest.getVillageName())) {
            logger.warn("getListByArbitraryDoor fail , because village is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_VILLAGE_IS_EMPTY);
        }

        //陵园归属（社）是否为空
        if (StringUtils.isEmpty(cemeteryByAreaListRequest.getCommunityName())) {
            logger.warn("getListByArbitraryDoor fail , because community id is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_COMMUNITY_IS_EMPTY);
        }

        //判断行为是否为空
        if (StringUtils.isEmpty(behavior)) {
            logger.warn("getListByArbitraryDoor fail , because behavior is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_BEHAVIOR_IS_EMPTY);
        }

        //判断页码是否为空
        if (StringUtils.isEmpty(pageNo)) {
            logger.warn("getListByArbitraryDoor fail , because pageNo is null.");
            throw new EqianyuanException(ExceptionMsgConstant.PAGINATION_PAGE_NO_IS_EMPTY);
        }

        //根据省编号查询省数据，检查编号正确性
        Province province = provinceDao.selectById(cemeteryByAreaListRequest.getProvinceId());
        if (ObjectUtils.isEmpty(province)) {
            logger.info("getListByArbitraryDoor fail , because select province by province id [" + cemeteryByAreaListRequest.getProvinceId() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_PROVINCE_DATA_NO_EXISTS);
        }

        //根据市编号查询市数据，检查编号正确性
        City city = cityDao.selectById(cemeteryByAreaListRequest.getProvinceId(), cemeteryByAreaListRequest.getCityId());
        if (ObjectUtils.isEmpty(city)) {
            logger.info("getListByArbitraryDoor fail , because select city by city id [" + cemeteryByAreaListRequest.getCityId() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_CITY_DATA_NO_EXISTS);
        }

        //根据区编号查询区数据，检查编号正确性
        County county = countyDao.selectById(cemeteryByAreaListRequest.getCityId(), cemeteryByAreaListRequest.getCountyId());
        if (ObjectUtils.isEmpty(county)) {
            logger.info("getListByArbitraryDoor fail , because select county by county id [" + cemeteryByAreaListRequest.getCountyId() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_COUNTY_DATA_NO_EXISTS);
        }
        //根据区编号和乡名称查询乡数据
        Town town = townDao.selectByName(cemeteryByAreaListRequest.getCountyId(), cemeteryByAreaListRequest.getTownName());
        if (ObjectUtils.isEmpty(town)) {
            logger.info("getListByArbitraryDoor fail , because select county by town name [" + cemeteryByAreaListRequest.getTownName() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_TOWN_DATA_NO_EXISTS);
        }
        //根据乡编号和村名称查询村数据
        Village village = villageDao.selectByName(town.getId(), cemeteryByAreaListRequest.getVillageName());
        if (ObjectUtils.isEmpty(village)) {
            logger.info("getListByArbitraryDoor fail , because select county by town name [" + cemeteryByAreaListRequest.getTownName() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_VILLAGE_DATA_NO_EXISTS);
        }
        //根据村编号和社名称查询社数据
        Community community = communityDao.selectByName(village.getId(), cemeteryByAreaListRequest.getCommunityName());
        if (ObjectUtils.isEmpty(community)) {
            logger.info("getListByArbitraryDoor fail , because select county by town name [" + cemeteryByAreaListRequest.getTownName() + "] result is null");
            throw new EqianyuanException(ExceptionMsgConstant.AREA_COMMUNITY_DATA_NO_EXISTS);
        }

        int page_no = Integer.parseInt(pageNo);
        Page page = new Page(page_no, pageSize);

        //根据分页条件查询社区结果总数
        Long countByLimit = cemeteryDao.countyByLimit(page, community.getId());
        if (ObjectUtils.isEmpty(countByLimit)
                || countByLimit == 0) {
            //社区编号
            String villageId = community.getVillageId();
            //当前社区创建时间
            Integer createTime = community.getCreateTime();
            //查找上一个或下一个社区
            if (StringUtils.equalsIgnoreCase(behavior, "next")) {
                community = communityDao.selectByNext(villageId, createTime);
            } else {
                community = communityDao.selectByPrev(villageId, createTime);
            }

            page_no = 1;

            if (ObjectUtils.isEmpty(community)
                    || StringUtils.isEmpty(community.getId())) {
                logger.info("getListByArbitraryDoor fail , because select " + behavior + " community by village id [" + villageId + "] , create time [" + createTime + "] result is null");
                return new PageResponse(page_no, pageSize, 0, null);
            }
        }

        //根据社区编号获取陵园总数
        Long dataCount = cemeteryDao.countByPagination(community.getId());

        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("communityId [" + community.getId() + "] get total count is null");
            return new PageResponse(page_no, pageSize, dataCount, null);
        }

        page = new Page(page_no, pageSize);
        List<Cemetery> cemeteries = cemeteryDao.selectByPagination(page, community.getId());
        if (CollectionUtils.isEmpty(cemeteries)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "], communityId [" + community.getId() + "] get List is null");
            return new PageResponse(page_no, pageSize, dataCount, null);
        }

        List<CemeteryBo> cemeteryBos = getCemeteryBos(province, city, county, town, village, community, cemeteries);
        return new PageResponse(page_no, pageSize, dataCount, cemeteryBos);
    }

    /**
     * 获取我的陵园集合
     *
     * @param memberId 会员编号
     * @return
     */
    public List<CemeteryBo> getListByMemberId(Integer memberId) throws EqianyuanException {
        if (ObjectUtils.isEmpty(memberId)) {
            logger.info("getMineList fail , because memberId is null");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBER_NO_AUTHORIZATION_BY_LOGIN);
        }

        //根据会员编号查找陵园集合数据
        List<Cemetery> cemeteries = cemeteryDao.selectByMemberId(memberId);
        List<CemeteryBo> cemeteryBos = new ArrayList<CemeteryBo>();
        for (Cemetery cemetery : cemeteries) {
            CemeteryBo cemeteryBo = new CemeteryBo();
            BeanUtils.copyProperties(cemetery, cemeteryBo);
            cemeteryBo.setCreateTimeForStr(CalendarUtil.secondsTimeToDateTimeString(cemeteryBo.getCreateTime()));
            cemeteryBos.add(cemeteryBo);
        }
        return cemeteryBos;
    }

    /**
     * 分页获取我的陵园集合
     *
     * @param memberId 会员编号
     * @return
     */
    public PageResponse pagingByMemberId(Integer memberId, String pageNo, int pageSize) throws EqianyuanException {
        if (ObjectUtils.isEmpty(memberId)) {
            logger.info("getMineList fail , because memberId is null");
            throw new EqianyuanException(ExceptionMsgConstant.MEMBER_NO_AUTHORIZATION_BY_LOGIN);
        }

        //根据会员编号获取陵园总数
        Long dataCount = cemeteryDao.countByMemberId(String.valueOf(memberId));

        int page_no = Integer.parseInt(pageNo);
        Page page = new Page(page_no, pageSize);

        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("会员【" + memberId + "】还没有建设陵园");
            return new PageResponse(page_no, pageSize, dataCount, null);
        }

        //根据会员编号和分页条件查找陵园集合数据
        List<Cemetery> cemeteries = cemeteryDao.pagingByMemberId(page, memberId);

        if (CollectionUtils.isEmpty(cemeteries)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "], cemetery [" + memberId + "] get Cemetery List is null");
            return new PageResponse(page_no, pageSize, dataCount, null);
        }

        List<CemeteryBo> cemeteryBos = new ArrayList<CemeteryBo>();
        for (Cemetery cemetery : cemeteries) {
            CemeteryBo cemeteryBo = new CemeteryBo();
            BeanUtils.copyProperties(cemetery, cemeteryBo);
            cemeteryBo.setCreateTimeForStr(CalendarUtil.secondsTimeToDateTimeString(cemeteryBo.getCreateTime()));
            cemeteryBos.add(cemeteryBo);
        }
        return new PageResponse(page_no, pageSize, dataCount, cemeteryBos);
    }

    /**
     * 根据陵园编号获取陵园信息
     *
     * @param cemeteryId
     * @return
     * @throws EqianyuanException
     */
    public CemeteryBo getInfoById(String cemeteryId) throws EqianyuanException {
        CemeteryBo cemeteryBo = getBasicInfoById(cemeteryId);

        //根据省编号查询省数据
        Province province = provinceDao.selectById(cemeteryBo.getProvinceId());
        //根据市编号查询市数据
        City city = cityDao.selectById(cemeteryBo.getProvinceId(), cemeteryBo.getCityId());
        //根据区编号查询区数据
        County county = countyDao.selectById(cemeteryBo.getCityId(), cemeteryBo.getCountyId());
        //根据乡编号查询乡数据
        Town town = townDao.selectByPrimaryKey(cemeteryBo.getTownId());
        //根据村编号查询村数据
        Village village = villageDao.selectByPrimaryKey(cemeteryBo.getVillageId());
        //根据社编号查询社数据
        Community community = communityDao.selectByPrimaryKey(cemeteryBo.getCommunityId());

        cemeteryBo.setProvinceName(province.getProvinceName());
        cemeteryBo.setCityName(city.getCityName());
        cemeteryBo.setCountyName(county.getCountyName());
        cemeteryBo.setTownName(town.getName());
        cemeteryBo.setVillageName(village.getName());
        cemeteryBo.setCommunityName(community.getName());
        return cemeteryBo;
    }

    /**
     * 根据陵园编号获取陵园信息
     *
     * @param cemeteryId
     * @return
     * @throws EqianyuanException
     */
    public CemeteryBo getBasicInfoById(String cemeteryId) throws EqianyuanException {
        if (StringUtils.isEmpty(cemeteryId)) {
            logger.warn("getBasicInfoById fail , because cemeteryId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_ID_IS_EMPTY);
        }

        //根据陵园编号获取陵园
        Cemetery cemetery = cemeteryDao.selectByPrimaryKey(cemeteryId);
        if (ObjectUtils.isEmpty(cemetery)
                || ObjectUtils.isEmpty(cemetery.getId())) {
            logger.info("getBasicInfoById fail , because cemeteryId [" + cemeteryId + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_DATA_NOT_EXISTS);
        }

        CemeteryBo cemeteryBo = new CemeteryBo();
        BeanUtils.copyProperties(cemetery, cemeteryBo);
        cemeteryBo.setCreateTimeForStr(CalendarUtil.secondsTimeToDateTimeString(cemeteryBo.getCreateTime()));

        //根据会员编号查询会员信息
        MemberAccount memberAccount = memberAccountDao.selectByPrimaryKey(cemetery.getMemberId());
        cemeteryBo.setMemberName(memberAccount.getNickName());
        return cemeteryBo;
    }

    /**
     * 根据陵园编号及访问密码核对陵园是否允许访问
     *
     * @param cemeteryId
     * @param enterPwd
     * @return
     * @throws EqianyuanException
     */
    public void checkAccessPassword(String cemeteryId, String enterPwd) throws EqianyuanException {
        if (StringUtils.isEmpty(cemeteryId)) {
            logger.warn("getInfoByEnter fail , because cemeteryId is null.");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_ID_IS_EMPTY);
        }

        //根据陵园编号获取陵园
        Cemetery cemetery = cemeteryDao.selectByPrimaryKey(cemeteryId);

        if (ObjectUtils.isEmpty(cemetery)
                || ObjectUtils.isEmpty(cemetery.getId())) {
            logger.info("getInfoByEnter fail , because cemeteryId [" + cemeteryId + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_DATA_NOT_EXISTS);
        }

        //如果陵园是公开的，则不需要校验密码，直接返回对象
        if (cemetery.getIsOpen() == CEMETERY_IS_OPEN_BY_PRIVATE) {
            //校验访问密码
            if (StringUtils.isEmpty(enterPwd)) {
                logger.warn("getInfoByEnter fail , because enterPwd is null.");
                throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_PASSWORD_IS_EMPTY);
            }

            if (!StringUtils.equals(cemetery.getPassword(), enterPwd)) {
                logger.warn("getInfoByEnter fail , because password is null.");
                throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_ACCESS_PASSWORD_ERROR);
            }
        }
    }

    /**
     * 根据Do集合获取Bo集合
     *
     * @param province
     * @param city
     * @param county
     * @param town
     * @param village
     * @param community
     * @param cemeteries
     * @return
     */
    private List<CemeteryBo> getCemeteryBos(Province province, City city, County county, Town town, Village village, Community community, List<Cemetery> cemeteries) {
        List<CemeteryBo> cemeteryBos = new ArrayList<CemeteryBo>();
        for (Cemetery cemetery : cemeteries) {
            CemeteryBo cemeteryBo = new CemeteryBo();
            BeanUtils.copyProperties(cemetery, cemeteryBo);
            cemeteryBo.setProvinceName(province.getProvinceName());
            cemeteryBo.setCityName(city.getCityName());
            cemeteryBo.setCountyName(county.getCountyName());
            cemeteryBo.setTownName(town.getName());
            cemeteryBo.setVillageName(village.getName());
            cemeteryBo.setCommunityName(community.getName());
            cemeteryBo.setCreateTimeForStr(CalendarUtil.secondsTimeToDateTimeString(cemeteryBo.getCreateTime()));
            cemeteryBos.add(cemeteryBo);
        }
        return cemeteryBos;
    }

    /**
     * 设置陵园乡村社地址信息
     *
     * @param cemetery
     * @param town
     * @param village
     * @param community
     */
    private void setAddress(Cemetery cemetery, Town town, Village village, Community community) {
        cemetery.setTownId(town.getId());
        cemetery.setVillageId(village.getId());
        cemetery.setCommunityId(community.getId());
    }

    /**
     * 添加乡地址
     *
     * @param countyId 区编号
     * @param townName 乡名称
     * @return
     */
    private Town addTown(String countyId, String townName) {
        Town town = new Town();
        town.setCountyId(countyId);
        town.setName(townName);
        //增加乡数据
        townDao.insertSelective(town);
        return town;
    }

    /**
     * 添加村地址
     *
     * @param townId      乡编号
     * @param villageName 村名称
     * @return
     */
    public Village addVillage(String townId, String villageName) {
        Village village = new Village();
        village.setTownId(townId);
        village.setName(villageName);
        villageDao.insertSelective(village);
        return village;
    }

    /**
     * 添加社区
     *
     * @param villageId     归属村编号
     * @param communityName 社区名称
     * @param renameCount   社区名允许重命名次数
     * @return
     */

    private Community addCommunity(String villageId, String communityName, int renameCount) {
        Community community = new Community();
        community.setVillageId(villageId);
        community.setName(communityName);
        community.setRenameCount(renameCount);
        community.setCreateTime(CalendarUtil.getSystemSeconds());
        communityDao.insertSelective(community);
        return community;
    }


    /**
     * 获取陵园分页集合
     *
     * @param cemeteryByAreaListRequest
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageResponse getList(CemeteryByAreaListRequest cemeteryByAreaListRequest, int pageNo, int pageSize) throws EqianyuanException {
        //根据社区编号获取陵园总数
        Long dataCount = cemeteryDao.countByAreaPagination(cemeteryByAreaListRequest.getProvinceId(), cemeteryByAreaListRequest.getCityId(), cemeteryByAreaListRequest.getCountyId());

        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("countByAreaPagination by provinceId [" + cemeteryByAreaListRequest.getProvinceId() + "] cityId [" + cemeteryByAreaListRequest.getCityId() + "]" +
                    "countyId [" + cemeteryByAreaListRequest.getCountyId() + "] get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        Page page = new Page(pageNo, pageSize);
        List<Cemetery> cemeteries = cemeteryDao.selectByAreaPagination(page, cemeteryByAreaListRequest.getProvinceId(), cemeteryByAreaListRequest.getCityId(), cemeteryByAreaListRequest.getCountyId());

        if (CollectionUtils.isEmpty(cemeteries)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "], provinceId [" + cemeteryByAreaListRequest.getProvinceId() + "] cityId [" + cemeteryByAreaListRequest.getCityId() + "]" +
                    "countyId[" + cemeteryByAreaListRequest.getCountyId() + "]get List is null ");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        //遍历陵园集合，获取陵园创建会员编号
        List<String> memberIds = new ArrayList<String>();
        for (Cemetery cemetery : cemeteries) {
            memberIds.add(String.valueOf(cemetery.getMemberId()));
        }
        //根据会员编号集合，查询会员数据信息集合
        List<MemberAccount> memberAccountList = memberAccountDao.selectByMemberIds(memberIds);
        List<CemeteryBo> cemeteryBos = new ArrayList<CemeteryBo>();
        for (Cemetery cemetery : cemeteries) {
            CemeteryBo cemeteryBo = new CemeteryBo();
            BeanUtils.copyProperties(cemetery, cemeteryBo);
            cemeteryBo.setCreateTimeForStr(CalendarUtil.secondsTimeToDateTimeString(cemeteryBo.getCreateTime()));
            cemeteryBos.add(cemeteryBo);

            for (MemberAccount memberAccount : memberAccountList) {
                //比较陵园中创建者会员编号和会员集合中的会员是否一致
                if (StringUtils.equals(String.valueOf(cemetery.getMemberId()), String.valueOf(memberAccount.getMemberId()))) {
                    cemeteryBo.setMemberName(memberAccount.getNickName());
                    break;
                }
            }
        }
        return new PageResponse(pageNo, pageSize, dataCount, cemeteryBos);
    }

    /**
     * 删除陵园
     * @param cemeteryId
     * @return
     * @throws EqianyuanException
     */
    public boolean deleteCemetery(Integer memberId, String cemeteryId) throws EqianyuanException {
        //检查当前登录会员是否拥有对该陵园的管理权限
        Cemetery cemetery = commonService.hasPermissionsByCemetery(cemeteryId, memberId);
        cemeteryDao.deleteByPrimaryKey(cemeteryId);


        return true;
    }
}
