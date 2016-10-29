package com.bestomb.controller.api;

import com.bestomb.common.Pager;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.tombstone.master.leaveMessage.LeaveMessageEditRequest;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.member.LeaveMessageBo;
import com.bestomb.controller.BaseController;
import com.bestomb.sevice.api.WebsiteLeaveMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 陵园墓碑纪念人留言API控制器
 * Created by jason on 2016-10-18.
 */
@Controller
@RequestMapping("/website/leaveMessage_api")
public class WebsiteLeaveMessageController extends BaseController {

    @Autowired
    private WebsiteLeaveMessageService websiteLeaveMessageService;

    /**
     * 添加陵园墓碑纪念人留言
     *
     * @param leaveMessageEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/addLeaveMessage")
    @ResponseBody
    public ServerResponse addLeaveMessage(LeaveMessageEditRequest leaveMessageEditRequest) throws EqianyuanException {
        websiteLeaveMessageService.addLeaveMessage(leaveMessageEditRequest);
        return new ServerResponse();
    }

    /**
     * 添加陵园墓碑纪念人留言
     *
     * @param leaveMessageEditRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/replyLeaveMessage")
    @ResponseBody
    public ServerResponse replyLeaveMessage(LeaveMessageEditRequest leaveMessageEditRequest) throws EqianyuanException {
        websiteLeaveMessageService.replyLeaveMessage(leaveMessageEditRequest);
        return new ServerResponse();
    }

    /***
     * 根据陵园墓碑纪念人编号查询墓中纪念人留言分页集合
     *
     * @param masterId 陵园墓碑纪念人编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getLeaveMessageByMaster")
    @ResponseBody
    public ServerResponse getLeaveMessageByMaster(String masterId, Pager page) throws EqianyuanException {
        PageResponse pageResponse = websiteLeaveMessageService.getLeaveMessageByMaster(masterId, page);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

    /***
     * 根据陵园墓碑纪念人留言编号查询墓中纪念人留言回复集合
     *
     * @param leaveMessageId 纪念人留言编号
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/getReplyLeaveMessageByMaster")
    @ResponseBody
    public ServerResponse getReplyLeaveMessageByMaster(String leaveMessageId) throws EqianyuanException {
        List<LeaveMessageBo> leaveMessageBoList = websiteLeaveMessageService.getReplyLeaveMessageByMaster(leaveMessageId);
        return new ServerResponse.ResponseBuilder().data(leaveMessageBoList).build();
    }

}
