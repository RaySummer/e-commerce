package com.eco.commerce.core.module.member.repository;

import com.eco.commerce.core.module.base.repository.BaseRepository;
import com.eco.commerce.core.module.member.model.Member;

/**
 * @author Ray
 * @since 2023/2/15
 */
public interface MemberRepository extends BaseRepository<Member, Long> {

    Member findByAccount(String account);
}
