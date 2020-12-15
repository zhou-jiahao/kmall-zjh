package com.kgc.kmall.user.controller;

import com.kgc.kmall.user.bean.Member;
import com.kgc.kmall.user.service.MemberService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class KmallController {

    @Reference
    MemberService memberService;

    @RequestMapping("/")
    @ResponseBody
    public List<Member> index(){
        List<Member> members = memberService.selectAllMember();
        return members;
    }

}
