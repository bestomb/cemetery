package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.memberRoleRelate.MemberRoleRelateRequest;
import com.bestomb.common.response.MemberRoleRelate.MemberRoleRelateBo;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.service.IMemberRoleRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/sys_memberrole")
public class MemberRoleRelateController extends BaseController {

    @Autowired
    private IMemberRoleRelateService memberRoleRelateService;

    /**
     * 根据memberid查询
     * @param memberId
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/list")
    @ResponseBody
    public ServerResponse getList(String memberId) throws EqianyuanException {
        List<MemberRoleRelateBo> memberRoleRelateBos = memberRoleRelateService.getList(memberId);
        return new ServerResponse.ResponseBuilder().data(memberRoleRelateBos).build();
    }

    /**
     * 根据memberid，type查询数据
     * @param memberRoleRelateRequest
     * @return
     * @throws EqianyuanException
     */
    @RequestMapping("/memberadd")
    @ResponseBody
    public ServerResponse add(MemberRoleRelateRequest memberRoleRelateRequest) throws EqianyuanException {
        memberRoleRelateService.add(memberRoleRelateRequest);
        return new ServerResponse();
    }
}
