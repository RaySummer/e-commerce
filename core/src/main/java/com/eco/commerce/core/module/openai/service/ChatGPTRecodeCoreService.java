package com.eco.commerce.core.module.openai.service;

import com.eco.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.eco.commerce.core.module.member.model.Member;
import com.eco.commerce.core.module.openai.model.ChatGPTRecode;
import com.eco.commerce.core.module.openai.repository.ChatGPTRecodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ray
 * @since 2023/3/2
 */
@Slf4j
@Service
public class ChatGPTRecodeCoreService extends BaseCrudServiceImpl<ChatGPTRecodeRepository, ChatGPTRecode, Long> {

    public List<ChatGPTRecode> findRecode(Member member) {
        return baseRepository.findRecodeByMember(member.getId());
    }

    @Transactional
    public void saveOrUpdateRecode(List<ChatGPTRecode> chatGPTRecodeList) {
        baseRepository.saveAllAndFlush(chatGPTRecodeList);
    }

    public void deleteRecodeByMember(long memberId) {
        baseRepository.deleteRecodeByMember(memberId);
    }
}
