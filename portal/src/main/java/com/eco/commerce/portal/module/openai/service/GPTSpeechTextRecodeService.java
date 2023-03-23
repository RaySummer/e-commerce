package com.eco.commerce.portal.module.openai.service;

import com.eco.commerce.core.module.member.dto.MemberDto;
import com.eco.commerce.core.module.member.model.Member;
import com.eco.commerce.core.module.member.service.MemberCoreService;
import com.eco.commerce.core.module.openai.model.GPTSpeechTextRecode;
import com.eco.commerce.core.module.openai.service.GPTSpeechTextRecodeCoreService;
import com.eco.commerce.core.utils.WebThreadLocal;
import com.eco.commerce.portal.module.openai.dto.vo.OpenAISpeechToTextVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author Ray
 * @since 2023/3/23
 */
@Slf4j
@Service
public class GPTSpeechTextRecodeService {

    @Autowired
    private GPTSpeechTextRecodeCoreService gptSpeechTextRecodeCoreService;

    @Autowired
    private MemberCoreService memberCoreService;

    public OpenAISpeechToTextVO getSpeechToTextResult(String key) {
        Member member = memberCoreService.findByUid(WebThreadLocal.getMember().getUid());

        Assert.notNull(member, "Member is null");

        GPTSpeechTextRecode recode = gptSpeechTextRecodeCoreService.findRecode(key, member.getId());

        Assert.notNull(recode, "The key cannot find content, content is null");

        return OpenAISpeechToTextVO.builder().content(recode.getContent()).key(recode.getKey()).build();
    }

    @Transactional
    public void saveSpeechToTextRecode(MemberDto memberDto, String content, String key) {
        Assert.notNull(memberDto, "Member is null");
        Member member = memberCoreService.findByUid(WebThreadLocal.getMember().getUid());
        Assert.notNull(member, "Member is null");

        GPTSpeechTextRecode recode = new GPTSpeechTextRecode();
        recode.setMember(member);
        recode.setContent(content);
        recode.setKey(key);
        recode.setUsed(Boolean.FALSE);
        gptSpeechTextRecodeCoreService.create(recode);

    }


}
