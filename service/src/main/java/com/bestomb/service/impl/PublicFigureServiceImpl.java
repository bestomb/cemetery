package com.bestomb.service.impl;

import com.bestomb.common.Page;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.publicFigure.PublicFigureRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.PublicFigure.PublicFigureBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.IMemberAccountDao;
import com.bestomb.dao.IMemberRoleRelateDao;
import com.bestomb.dao.IPublicFiguresDao;
import com.bestomb.dao.ISystemUserDao;
import com.bestomb.entity.MemberAccount;
import com.bestomb.entity.MemberRoleRelate;
import com.bestomb.entity.PublicFigures;
import com.bestomb.entity.SystemUser;
import com.bestomb.service.IPublicFigureService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublicFigureServiceImpl implements IPublicFigureService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IPublicFiguresDao publicFiguresDao;

    @Autowired
    private IMemberAccountDao memberAccountDao;

    @Autowired
    private ISystemUserDao systemUserDao;

    @Autowired
    private IMemberRoleRelateDao memberRoleRelateDao;

    /**
     * 分页查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(int pageNo, int pageSize, String status) throws EqianyuanException {

        Long dataCount = publicFiguresDao.countByPagination(status);
        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        Page page = new Page(pageNo, pageSize);
        List<PublicFigures> publicFiguresList = publicFiguresDao.selectByPagination(page, status);
        if (CollectionUtils.isEmpty(publicFiguresList)) {
            logger.warn("pageNo [" + pageNo + "], pageSize [" + pageSize + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        //遍历集合，获取会员编号
        List<String> publicfigureIds = new ArrayList<String>();
        //遍历集合，获取管理员信息
        List<String> systemuesrid = new ArrayList<String>();
        for (PublicFigures publicFigure : publicFiguresList) {

            if (!ObjectUtils.isEmpty(publicFigure.getMemberId())) {
                publicfigureIds.add(String.valueOf(publicFigure.getMemberId()));
            }
            if (!StringUtils.isEmpty(publicFigure.getSystemUser())) {
                systemuesrid.add(publicFigure.getSystemUser());
            }
        }

        //根据会员编号集合，查询会员数据信息集合
        List<MemberAccount> memberAccountList = null;
        if (!CollectionUtils.isEmpty(publicfigureIds)) {
            memberAccountList = memberAccountDao.selectByMemberIds(publicfigureIds);
        }
        //根据会员编号集合，查询会员数据信息集合
        List<SystemUser> systemUserList = null;
        if (!CollectionUtils.isEmpty(systemuesrid)) {
            systemUserList = systemUserDao.selectByMemberIds(systemuesrid);
        }
        List<PublicFigureBo> publicFigureBoList = new ArrayList<PublicFigureBo>();
        for (PublicFigures publicFigures : publicFiguresList) {
            PublicFigureBo publicFigureBo = new PublicFigureBo();
            BeanUtils.copyProperties(publicFigures, publicFigureBo);
            publicFigureBo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(publicFigures.getCreateTime()));

            if (!ObjectUtils.isEmpty(publicFigures.getDisposeTime())) {
                publicFigureBo.setDisposeTime(CalendarUtil.secondsTimeToDateTimeString(publicFigures.getDisposeTime()));
            }

            if (!CollectionUtils.isEmpty(memberAccountList)) {
                for (MemberAccount memberAccount : memberAccountList) {
                    //比较会员编号和会员集合中的会员是否一致
                    if (publicFigures.getMemberId().equals(memberAccount.getMemberId())) {
                        publicFigureBo.setMemberId(memberAccount.getNickName());
                        break;
                    }
                }
            }

            if (!CollectionUtils.isEmpty(systemUserList)) {
                for (SystemUser systemUser : systemUserList) {
                    //比较公共人物管理信息systemuserid和系统用户id是否一致
                    if (StringUtils.equals(publicFigures.getSystemUser(), systemUser.getId())) {
                        publicFigureBo.setSystemUser(systemUser.getLoginName());
                        break;
                    }
                }
            }

            publicFigureBoList.add(publicFigureBo);
        }
        return new PageResponse(pageNo, pageSize, dataCount, publicFigureBoList);
    }

    /**
     * 根据ID查询数据
     *
     * @param id
     * @return
     * @throws EqianyuanException
     */
    public PublicFigureBo queryById(String id) throws EqianyuanException {

        //主键是否为空
        if (StringUtils.isEmpty(id)) {
            logger.info("queryById fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.PUBLIC_FIGURES_INFORMATION_ID_IS_EMPTY);
        }

        //根据主键查询对象数据
        PublicFigures publicFiguresId = publicFiguresDao.selectByPrimaryKey(id);

        PublicFigureBo publicFigureBo = new PublicFigureBo();
        BeanUtils.copyProperties(publicFiguresId, publicFigureBo);
        publicFigureBo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(publicFiguresId.getCreateTime()));

        if (!ObjectUtils.isEmpty(publicFiguresId.getDisposeTime())) {
            publicFigureBo.setDisposeTime(CalendarUtil.secondsTimeToDateTimeString(publicFiguresId.getDisposeTime()));
        }

        //根据memberid编号查询信息
        MemberAccount memberAccount = memberAccountDao.selectByPrimaryKey(publicFiguresId.getMemberId());
        if (!StringUtils.isEmpty(String.valueOf(memberAccount.getMemberId()))) {
            publicFigureBo.setMemberId(memberAccount.getNickName());
        }

        //根据systemuserid编号查询信息
        SystemUser systemUser = systemUserDao.selectByPrimaryKey(publicFiguresId.getSystemUser());
        if (!ObjectUtils.isEmpty(systemUser) && !StringUtils.isEmpty(systemUser.getId())) {
            publicFigureBo.setSystemUser(systemUser.getLoginName());
        }

        return publicFigureBo;
    }

    /**
     * 修改数据
     *
     * @param publicFigureRequest
     * @throws EqianyuanException
     */
    @Transactional(rollbackFor = Exception.class)
    public void modify(PublicFigureRequest publicFigureRequest) throws EqianyuanException {
        //判断审核结果是否有值
        if (StringUtils.isEmpty(String.valueOf(publicFigureRequest.getStatus()))) {
            logger.info("queryByStatus fail , because status is empty");
            throw new EqianyuanException(ExceptionMsgConstant.PUBLIC_FIGURES_INFORMATION_Status_IS_EMPTY);
        }

        if (publicFigureRequest.getStatus() == 1) {
            //根据主键查询对象数据
            PublicFigures publicFiguresId = publicFiguresDao.selectByPrimaryKey(publicFigureRequest.getId());
            //以memberid =为条件查询数据
            MemberRoleRelate memberRoleRelate = memberRoleRelateDao.selectByPrimaryKey(publicFiguresId.getMemberId());
            if(!ObjectUtils.isEmpty(memberRoleRelate)){
                memberRoleRelateDao.updateByPrimaryKeySelective(memberRoleRelate);
            }else {
                MemberRoleRelate memberRoleRelat = new MemberRoleRelate();
                BeanUtils.copyProperties(publicFiguresId, memberRoleRelat);
                memberRoleRelateDao.insertSelective(memberRoleRelat);
            }
        }


        //获取系统当前时间
        publicFigureRequest.setDisposeTime(CalendarUtil.getSystemSeconds());
        //添加用户数据信息
        PublicFigures publicFigures = new PublicFigures();
        BeanUtils.copyProperties(publicFigureRequest, publicFigures);

        publicFiguresDao.updateByPrimaryKeySelective(publicFigures);

    }

}
