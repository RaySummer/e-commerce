package com.eco.commerce.portal.module.member.service;

import com.eco.commerce.core.module.member.model.Member;
import com.eco.commerce.core.module.member.service.MemberCoreService;
import com.eco.commerce.portal.module.member.dto.ro.MemberRegisterRO;
import com.eco.commerce.portal.module.member.dto.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Ray
 * @since 2023/2/16
 */

@Slf4j
@Service()
public class MemberService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private MemberCoreService memberCoreService;

    public boolean checkMemberExists(String userName, String tokenAccount) {

        return false;
    }

    public MemberVO getMemberVOByAccount(String account) {
        return MemberVO.of(memberCoreService.findMemberByAccount(account));
    }

    public Member getMemberByAccount(String account) {
        return memberCoreService.findMemberByAccount(account);
    }

    public MemberVO register(MemberRegisterRO ro) {
        Member member = new Member();
        member.setAccount(ro.getUsername());
        member.setPassword(bcryptEncoder.encode(ro.getPassword()));
        member.setNickName("未命名");
        memberCoreService.create(member);
        return MemberVO.of(member);
    }

}
