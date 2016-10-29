package com.bestomb.sevice.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.response.notice.NoticeBo;
import com.bestomb.common.response.notice.NoticeVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.INoticeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 网站公告业务调用类
 * Created by jason on 2016-08-09.
 */
@Service
public class WebsiteNoticeService {

    @Autowired
    private INoticeService noticeService;

    /**
     * 添加陵园公告
     *
     * @param content
     * @param cemeteryId
     * @throws EqianyuanException
     */
    public void add(String content, String cemeteryId) throws EqianyuanException {
        //获取session会员信息
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        noticeService.add(content, cemeteryId, memberLoginVo.getMemberId());
    }

    /**
     * 删除公告内容
     *
     * @param id
     * @throws EqianyuanException
     */
    public void delete(String... id) throws EqianyuanException {
        //获取session会员信息
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        noticeService.removeByIds(memberLoginVo.getMemberId(), id);
    }

    /**
     * 修改公告内容
     *
     * @param id
     * @param content
     * @throws EqianyuanException
     */
    public void modify(String id, String content) throws EqianyuanException {
        //获取session会员信息
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        noticeService.modify(id, content, memberLoginVo.getMemberId());
    }

    /**
     * 根据陵园编号查询陵园公告分页集合
     *
     * @param cemeteryId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageResponse getListByCemetery(String cemeteryId, int pageNo, int pageSize) throws EqianyuanException {
        PageResponse pageResponse = noticeService.queryByPagination(pageNo, pageSize, cemeteryId);
        List<NoticeBo> noticeBos = (List<NoticeBo>) pageResponse.getList();
        if (!CollectionUtils.isEmpty(noticeBos)) {
            setListByPageResponse(pageResponse, noticeBos);
        }
        return pageResponse;
    }

    /**
     * 设置分页返回对象数据
     *
     * @param pageResponse
     * @param cemeteryBos
     */
    private void setListByPageResponse(PageResponse pageResponse, List<NoticeBo> cemeteryBos) {
        List<NoticeVo> noticeVos = new ArrayList<NoticeVo>();
        for (NoticeBo noticeBo : cemeteryBos) {
            NoticeVo noticeVo = new NoticeVo();
            BeanUtils.copyProperties(noticeBo, noticeVo);
            noticeVos.add(noticeVo);
        }

        pageResponse.setList(noticeVos);
    }
}
