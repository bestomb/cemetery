package com.bestomb.sevice.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.MasterEditRequest;
import com.bestomb.common.response.master.MasterBo;
import com.bestomb.common.response.member.MemberLoginVo;
import com.bestomb.common.util.SessionContextUtil;
import com.bestomb.common.util.SessionUtil;
import com.bestomb.common.util.yamlMapper.SystemConf;
import com.bestomb.service.IMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jason on 2016-10-18.
 */
@Service
public class WebsiteMasterService {

    @Autowired
    private IMasterService masterService;

    /**
     * 创建陵园墓碑纪念人
     *
     * @param masterEditRequest
     * @throws EqianyuanException
     */
    public void addMaster(MasterEditRequest masterEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        masterEditRequest.setOperatorMemberId(memberLoginVo.getMemberId());
        masterService.addMaster(masterEditRequest);
    }

    /**
     * 修改陵园墓碑纪念人
     *
     * @param masterEditRequest
     * @throws EqianyuanException
     */
    public void modifyMaster(MasterEditRequest masterEditRequest) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        masterEditRequest.setOperatorMemberId(memberLoginVo.getMemberId());
        masterService.modifyMaster(masterEditRequest);
    }

    /**
     * 删除陵园墓碑
     *
     * @param cemeteryId 陵园编号
     * @param masterId   纪念人编号
     * @throws EqianyuanException
     */
    public void delMaster(String cemeteryId, String masterId) throws EqianyuanException {
        /**
         * 从session池中获取系统用户信息
         */
        MemberLoginVo memberLoginVo = (MemberLoginVo) SessionUtil.getAttribute(SessionContextUtil.getInstance().getSession(SessionUtil.getSessionByHeader()), SystemConf.WEBSITE_SESSION_MEMBER.toString());
        masterService.delMaster(masterId, cemeteryId, memberLoginVo.getMemberId());
    }

    /**
     * 根据条件陵园墓碑编号查询墓中纪念人集合
     *
     * @param tombstoneId
     * @return
     * @throws EqianyuanException
     */
    public List<MasterBo> queryByTombstone(String tombstoneId) throws EqianyuanException {
        return masterService.queryByTombstone(tombstoneId);
    }
}
