package com.bestomb.service.impl;

import com.bestomb.common.Page;
import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.notice.NoticeBo;
import com.bestomb.common.util.CalendarUtil;
import com.bestomb.dao.ICemeteryDao;
import com.bestomb.dao.IMemberAuthorizationDao;
import com.bestomb.dao.INoticeDao;
import com.bestomb.entity.Cemetery;
import com.bestomb.entity.MemberAuthorization;
import com.bestomb.entity.Notice;
import com.bestomb.service.INoticeService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 系陵园公告业务逻辑
 * Created by jason on 2016-07-06.
 */
@Service
public class NoticeServiceImpl implements INoticeService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ICemeteryDao cemeteryDao;

    @Autowired
    private INoticeDao noticeDao;

    @Autowired
    private IMemberAuthorizationDao memberAuthorizationDao;

    /**
     * 根据主键编号删除数据
     *
     * @param id
     */
    public void removeByIds(Integer memberId, String... id) throws EqianyuanException {
        if (ObjectUtils.isEmpty(id)) {
            logger.info("removeByIds fail , because id is null, a full table delete is prohibited");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_NOTICE_ID_IS_EMPTY);
        }

        //根据公告信息查询公告归属陵园
        List<Notice> notices = noticeDao.selectByPrimaryKeys(id);
        if (CollectionUtils.isEmpty(notices)) {
            return;
        }

        //遍历并封装公告归属的陵园编号
        List<Integer> cemeteryIds = new ArrayList<Integer>();
        for (Notice notice : notices) {
            cemeteryIds.add(notice.getCemeteryId());
        }

        //根据陵园编号查询陵园园主信息
        List<Cemetery> cemeteries = cemeteryDao.selectMemberIdByCemeteryIds(cemeteryIds);
        if (CollectionUtils.isEmpty(cemeteries)) {
            return;
        }

        List<Integer> cemeteriesByMine = new ArrayList<Integer>();
        //遍历园主信息
        for (Cemetery cemetery : cemeteries) {
            //检查陵园是否为当前操作会员所建，仅删除自己所建陵园公告
            if (cemetery.getMemberId().intValue() == memberId) {
                cemeteriesByMine.add(cemetery.getId());
            }
        }

        //根据陵园编号查询授权用户
        List<MemberAuthorization> memberAuthorizations = memberAuthorizationDao.selectByCemeteryId(cemeteryIds);
        if (!CollectionUtils.isEmpty(memberAuthorizations)) {
            for (MemberAuthorization memberAuthorization : memberAuthorizations) {
                //检查陵园是否为当前操作会员所建，仅删除自己所建陵园公告
                if (memberAuthorization.getMemberId().intValue() == memberId.intValue()) {
                    cemeteriesByMine.add(memberAuthorization.getCemeteryId());
                }
            }
        }

        //遍历公告集合
        for (Notice notice : notices) {
            if (cemeteriesByMine.contains(notice.getCemeteryId())) {
                //删除
                noticeDao.deleteByPrimaryKey(notice.getId());
            }
        }
    }

    /**
     * 添加数据
     *
     * @param content
     * @param cemeteryId
     * @throws EqianyuanException
     */
    public void add(String content, String cemeteryId, Integer memberId) throws EqianyuanException {
        //陵园编号是否为空
        if (StringUtils.isEmpty(cemeteryId)) {
            logger.info("add fail , because cemeteryId is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_ID_IS_EMPTY);
        }

        //公告内容是否为空
        if (StringUtils.isEmpty(content)) {
            logger.info("add fail , because content is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_NOTICE_CONTENT_IS_EMPTY);
        }

        //根据陵园编号查询陵园是否存在
        Cemetery cemetery = cemeteryDao.selectByPrimaryKey(cemeteryId);
        if (ObjectUtils.isEmpty(cemetery)
                || ObjectUtils.isEmpty(cemetery.getId())) {
            logger.info("add fail , because cemeteryId [" + cemeteryId + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_DATA_NOT_EXISTS);
        }

        //检查陵园是否为当前操作会员所建
        if (cemetery.getMemberId().intValue() == memberId) {
            //添加数据信息
            Notice notice = new Notice();
            notice.setCemeteryId(cemetery.getId());
            notice.setContent(content);
            notice.setCreateTime(CalendarUtil.getSystemSeconds());
            noticeDao.insertSelective(notice);

            return;
        }

        //根据陵园编号查询授权用户
        List<MemberAuthorization> memberAuthorizations = memberAuthorizationDao.selectByCemeteryId(Arrays.asList(cemetery.getId()));
        if (CollectionUtils.isEmpty(memberAuthorizations)) {
            logger.info("add fail , because operator member is no master");
            throw new EqianyuanException(ExceptionMsgConstant.IS_NO_MASTER_BY_CEMETERY);
        }

        for (MemberAuthorization memberAuthorization : memberAuthorizations) {
            //检查陵园是否为当前操作会员所建，仅删除自己所建陵园公告
            if (memberAuthorization.getMemberId().intValue() == memberId.intValue()) {
                //添加数据信息
                Notice notice = new Notice();
                notice.setCemeteryId(cemetery.getId());
                notice.setContent(content);
                notice.setCreateTime(CalendarUtil.getSystemSeconds());
                noticeDao.insertSelective(notice);
                return;
            }
        }

        logger.info("add fail , because operator member is no master");
        throw new EqianyuanException(ExceptionMsgConstant.IS_NO_MASTER_BY_CEMETERY);
    }

    /**
     * 修改数据
     *
     * @param id
     * @param content
     * @param memberId
     * @throws EqianyuanException
     */
    public void modify(String id, String content, Integer memberId) throws EqianyuanException {
        //主键是否为空
        if (StringUtils.isEmpty(id)) {
            logger.info("modify fail , because id is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_NOTICE_ID_IS_EMPTY);
        }

        //公告内容是否为空
        if (StringUtils.isEmpty(content)) {
            logger.info("add fail , because content is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_NOTICE_CONTENT_IS_EMPTY);
        }

        //根据公告信息查询公告归属陵园
        Notice notice = noticeDao.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(notice) ||
                StringUtils.isEmpty(notice.getId())) {
            logger.info("modify fail , because id [" + id + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_NOTICE_DATA_NOT_EXISTS);
        }

        //根据陵园编号查询陵园信息
        Cemetery cemetery = cemeteryDao.selectByPrimaryKey(String.valueOf(notice.getCemeteryId()));
        if (ObjectUtils.isEmpty(cemetery)
                || ObjectUtils.isEmpty(cemetery.getId())) {
            logger.info("modify fail , because cemeteryId [" + notice.getCemeteryId() + "] query data is empty");
            throw new EqianyuanException(ExceptionMsgConstant.CEMETERY_DATA_NOT_EXISTS);
        }

        //检查陵园是否为当前操作会员所建
        if (cemetery.getMemberId().intValue() == memberId) {
            //修改
            notice.setContent(content);
            noticeDao.updateByPrimaryKeySelective(notice);
            return;
        }

        //根据陵园编号查询授权用户
        List<MemberAuthorization> memberAuthorizations = memberAuthorizationDao.selectByCemeteryId(Arrays.asList(cemetery.getId()));
        if (CollectionUtils.isEmpty(memberAuthorizations)) {
            logger.info("modify fail , because operator member is no master");
            throw new EqianyuanException(ExceptionMsgConstant.IS_NO_MASTER_BY_CEMETERY);
        }

        for (MemberAuthorization memberAuthorization : memberAuthorizations) {
            //检查陵园是否为当前操作会员所建，仅删除自己所建陵园公告
            if (memberAuthorization.getMemberId().intValue() == memberId.intValue()) {
                //修改
                notice.setContent(content);
                noticeDao.updateByPrimaryKeySelective(notice);
                return;
            }
        }

        logger.info("modify fail , because operator member is no master");
        throw new EqianyuanException(ExceptionMsgConstant.IS_NO_MASTER_BY_CEMETERY);
    }

    /**
     * 根据分页条件查询数据集合
     *
     * @param pageNo
     * @param pageSize
     * @param cemeteryId
     * @return
     */
    public PageResponse queryByPagination(int pageNo, int pageSize, String cemeteryId) {
        Long dataCount = noticeDao.countByPagination(cemeteryId);
        if (ObjectUtils.isEmpty(dataCount)) {
            logger.info("cemeteryId [" + cemeteryId + "] get total count is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        Page page = new Page(pageNo, pageSize);
        List<Notice> notices = noticeDao.selectByPagination(page, cemeteryId);
        if (CollectionUtils.isEmpty(notices)) {
            logger.info("pageNo [" + pageNo + "], pageSize [" + pageSize + "], cemeteryId [" + cemeteryId + "] get List is null");
            return new PageResponse(pageNo, pageSize, dataCount, null);
        }

        List<NoticeBo> noticeBos = new ArrayList<NoticeBo>();
        for (Notice notice : notices) {
            NoticeBo noticeBo = new NoticeBo();
            BeanUtils.copyProperties(notice, noticeBo);
            noticeBo.setCreateTimeForStr(CalendarUtil.secondsTimeToDateTimeString(notice.getCreateTime()));
            noticeBos.add(noticeBo);
        }
        return new PageResponse(pageNo, pageSize, dataCount, noticeBos);
    }

}
