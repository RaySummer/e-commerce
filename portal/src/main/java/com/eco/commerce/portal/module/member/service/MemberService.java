package com.eco.commerce.portal.module.member.service;

import com.eco.commerce.core.module.member.service.MemberCoreService;
import com.eco.commerce.portal.module.member.dto.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ray
 * @since 2023/2/16
 */

@Slf4j
@Service
public class MemberService {

    @Autowired
    private MemberCoreService memberCoreService;

    public boolean checkMemberExists(String userName, String tokenAccount) {

        return false;
    }

    public MemberVO getMemberByAccount(String account) {
        return MemberVO.of(memberCoreService.findMemberByAccount(account));
    }

}
