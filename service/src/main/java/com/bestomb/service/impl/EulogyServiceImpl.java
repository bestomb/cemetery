package com.bestomb.service.impl;

import com.bestomb.common.Pager;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.eulogy.EulogyEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.master.eulogy.EulogyBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.IEulogyDao;
import com.bestomb.dao.IMemberAccountDao;
import com.bestomb.entity.Eulogy;
import com.bestomb.entity.LeaveMessage;
import com.bestomb.entity.MemberAccount;
import com.bestomb.service.IEulogyService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 陵园墓碑接口业务实现
 * Created by jason on 2016-10-18.
 */
@Service
public class EulogyServiceImpl implements IEulogyService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private IEulogyDao eulogyDao;

    @Autowired
    private IMemberAccountDao memberAccountDao;

    /**
     * 添加祭文悼词
     *
     * @param eulogyEditRequest
     * @throws EqianyuanException
     */
    public void add(EulogyEditRequest eulogyEditRequest) throws EqianyuanException {
        //构建持久化祭文悼词数据
        Eulogy eulogy = new Eulogy();
        eulogy.setMemberId(eulogyEditRequest.getMemberId());
        eulogy.setMasterId(eulogyEditRequest.getMasterId());
        eulogy.setContent(eulogyEditRequest.getContent());
        eulogy.setCreateTime(CalendarUtil.getSystemSeconds());
        //持久祭文悼词数据
        eulogyDao.insertSelective(eulogy);
    }

    /**
     * 根据祭文悼词编号查询详情
     *
     * @param eulogyId
     * @return
     * @throws EqianyuanException
     */
    public EulogyBo getInfo(String eulogyId) throws EqianyuanException {
        Eulogy eulogy = eulogyDao.selectByPrimaryKey(eulogyId);

        if (ObjectUtils.isEmpty(eulogy)
                || StringUtils.isEmpty(eulogy.getId())) {
            logger.info("编号" + eulogyId + "查询纪念人祭文悼词信息为空");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_EULOGY_DATA_NOT_EXISTS);
        }
        EulogyBo eulogyBo = new EulogyBo();
        BeanUtils.copyProperties(eulogy, eulogyBo);
        return eulogyBo;
    }

    /**
     * 根据纪念人编号查询祭文悼词分页集合
     *
     * @param masterId
     * @param page
     * @return
     * @throws EqianyuanException
     */
    public PageResponse getList(String masterId, Pager page) throws EqianyuanException {
        // 根据条件查询音乐列表
        int dataCount = eulogyDao.countByCondition(masterId);
        page.setTotalRow(dataCount);
        if (dataCount <= 0) {
            logger.info("根据条件查询陵园纪念人祭文悼词列表无数据");
            return new PageResponse(page, null);
        }
        List<Eulogy> eulogies = eulogyDao.selectByCondition(masterId, page);
        if (CollectionUtils.isEmpty(eulogies)) {
            logger.info("pageNo [" + page.getPageNo() + "], pageSize [" + page.getPageSize() + "], 根据条件查询陵园纪念人祭文悼词列表无数据");
            return new PageResponse(page, null);
        }

        List<String> memberIds = new ArrayList<String>();
        for (Eulogy m : eulogies) {
            memberIds.add(String.valueOf(m.getMemberId()));
        }

        //根据会员编号查询获取会员昵称
        List<MemberAccount> memberAccounts = memberAccountDao.selectByMemberIds(memberIds);

        List<EulogyBo> eulogyBos = new ArrayList<EulogyBo>();
        for (Eulogy m : eulogies) {
            EulogyBo eulogyBo = new EulogyBo();
            BeanUtils.copyProperties(m, eulogyBo);
            eulogyBo.setCreateTime(CalendarUtil.secondsTimeToDateTimeString(m.getCreateTime())); // 转化创建时间

            if (!CollectionUtils.isEmpty(memberAccounts)) {
                for (MemberAccount memberAccount : memberAccounts) {
                    if (m.getMemberId().equals(memberAccount.getMemberId())) {
                        eulogyBo.setMemberNickName(memberAccount.getNickName());
                        break;
                    }
                }
            }

            eulogyBos.add(eulogyBo);
        }
        return new PageResponse(page, eulogyBos);
    }


}
