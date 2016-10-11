package com.bestomb.service.impl;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.memberRoleRelate.MemberRoleRelateRequest;
import com.bestomb.common.response.MemberRoleRelate.MemberRoleRelateBo;
import com.bestomb.dao.IMemberRoleRelateDao;
import com.bestomb.entity.MemberRoleRelate;
import com.bestomb.service.IMemberRoleRelateService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberRoleRelateServiceImpl implements IMemberRoleRelateService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IMemberRoleRelateDao memberRoleRelateDao;

    /**
     * 根获取数据集合
     *
     * @return
     */
    public List<MemberRoleRelateBo> getList(String memberId) throws EqianyuanException {

        if (ObjectUtils.isEmpty(memberId)) {
            logger.info("get List is null");
            throw new EqianyuanException(ExceptionMsgConstant.SYSTEM_ROLE_DATA_NOT_EXISTS);
        }

        List<MemberRoleRelate> memberRoleRelates = memberRoleRelateDao.selectByList(Integer.valueOf(memberId));

        List<MemberRoleRelateBo> memberRoleRelateBos = new ArrayList<MemberRoleRelateBo>();
        for (MemberRoleRelate memberRoleRelate : memberRoleRelates) {
            MemberRoleRelateBo memberRoleRelateBo = new MemberRoleRelateBo();
            BeanUtils.copyProperties(memberRoleRelate, memberRoleRelateBo);
            memberRoleRelateBos.add(memberRoleRelateBo);
        }
        return memberRoleRelateBos;
    }

    /**
     * 插入一条数据
     *
     * @param memberRoleRelateRequest
     * @throws EqianyuanException
     */
    public void add(MemberRoleRelateRequest memberRoleRelateRequest) throws EqianyuanException {

        //根据memberid,type查询对象数据
        List<MemberRoleRelate> memberRoleRelates = memberRoleRelateDao.selectByPrimary(memberRoleRelateRequest.getMemberId(), memberRoleRelateRequest.getType());

        if (ObjectUtils.isEmpty(memberRoleRelateRequest.getType())) {
            memberRoleRelateDao.deleteByPrimaryKey(String.valueOf(memberRoleRelateRequest.getMemberId()),memberRoleRelateRequest.getType());
        } else if (CollectionUtils.isEmpty(memberRoleRelates)) {
            MemberRoleRelate memberRoleRelate = new MemberRoleRelate();
            BeanUtils.copyProperties(memberRoleRelateRequest, memberRoleRelate);
            memberRoleRelateDao.insert(memberRoleRelate);
        }
    }
}
