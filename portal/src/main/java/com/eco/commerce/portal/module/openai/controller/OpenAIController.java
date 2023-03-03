package com.eco.commerce.portal.module.openai.controller;

import com.eco.commerce.core.annotation.NoRepeatSubmit;
import com.eco.commerce.core.module.member.dto.MemberDto;
import com.eco.commerce.core.utils.LockHelper;
import com.eco.commerce.core.utils.Response;
import com.eco.commerce.core.utils.WebThreadLocal;
import com.eco.commerce.portal.module.openai.dto.ro.ChatGPTRO;
import com.eco.commerce.portal.module.openai.service.OpenAIService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ray
 * @since 2023/2/28
 */
@Slf4j
@RestController
@RequestMapping({"/open-ai"})
public class OpenAIController {

    @Autowired
    private LockHelper lockHelper;

    @Autowired
    private OpenAIService openAIService;


    @NoRepeatSubmit
    @PostMapping("chat-to-ai")
    public Response chatToAI(@RequestBody ChatGPTRO ro) {
        MemberDto memberDto = WebThreadLocal.getMember();

        RLock lock = lockHelper.getLock(LockHelper.LOCK_MEMBER_CHAT, memberDto.getUidStr());

        try {
            lock.lock();
            return Response.of(openAIService.ChatToAI(ro, memberDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.ofError(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    @NoRepeatSubmit
    @PostMapping("ai-generate-image")
    public Response generateImages(@RequestBody ChatGPTRO ro) {
        MemberDto memberDto = WebThreadLocal.getMember();

        RLock lock = lockHelper.getLock(LockHelper.LOCK_MEMBER_CHAT, memberDto.getUidStr());

        try {
            lock.lock();
            return Response.of(openAIService.generateImage(ro));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.ofError(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

}
