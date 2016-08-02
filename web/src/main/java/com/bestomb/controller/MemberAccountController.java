package com.bestomb.controller;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.response.PageResponse;
import com.bestomb.common.response.ServerResponse;
import com.bestomb.common.response.member.MemberAccountBo;
import com.bestomb.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system_MemberAccount")
public class MemberAccountController extends BaseController{


        @Autowired
        private IMemberService memberService;

    /**
     * 根据主键ID查询单挑数据
     */
    @RequestMapping("/info")
    @ResponseBody
    public ServerResponse getInfo(String memberId)throws EqianyuanException{
        MemberAccountBo memberAccountBo = memberService.getInfo(memberId);
        return new ServerResponse.ResponseBuilder().data(memberAccountBo).build();
    }

    /**
     * 分页查询
     *@param pageNo   分页页码
     * @param pageSize 分页每页显示条数
     */
    @RequestMapping("/getlistmemberCount")
    @ResponseBody
    public ServerResponse getList(@RequestParam(value = "pageNo",required = false,defaultValue = "1") int pageNo,
                                   @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize) throws EqianyuanException{
        PageResponse pageResponse =  memberService.getList(pageNo, pageSize);
        return new ServerResponse.ResponseBuilder().data(pageResponse).build();
    }

}
