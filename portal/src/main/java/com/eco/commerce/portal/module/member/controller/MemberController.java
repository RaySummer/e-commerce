package com.eco.commerce.portal.module.member.controller;

import com.eco.commerce.core.utils.Response;
import com.eco.commerce.portal.module.member.dto.ro.MemberRO;
import com.eco.commerce.portal.module.member.dto.ro.MemberRegisterRO;
import com.eco.commerce.portal.module.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ray
 * @since 2023/2/16
 */
@Slf4j
@RestController
@RequestMapping({"/member"})
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public Response register(@RequestBody MemberRegisterRO ro) {
        return Response.of(memberService.register(ro));
    }

    @PostMapping("/quickly-register")
    public Response quicklyRegister(@RequestBody MemberRegisterRO ro) {
        return Response.of(memberService.register(ro));
    }

    @PostMapping("/modify")
    public Response modify(@RequestBody MemberRO ro) {
        return Response.of(memberService.modify(ro));
    }
}
