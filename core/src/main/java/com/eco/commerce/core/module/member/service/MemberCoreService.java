package com.eco.commerce.core.module.member.service;

import com.eco.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.eco.commerce.core.module.member.model.Member;
import com.eco.commerce.core.module.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ray
 * @since 2023/2/15
 */
@Slf4j
@Service
public class MemberCoreService extends BaseCrudServiceImpl<MemberRepository, Member, Long> {

    public Member findMemberByAccount(String account) {
        return baseRepository.findByAccount(account);
    }
}
