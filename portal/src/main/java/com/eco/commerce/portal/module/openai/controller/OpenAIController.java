package com.eco.commerce.portal.module.openai.controller;

import com.eco.commerce.core.annotation.NoRepeatSubmit;
import com.eco.commerce.core.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
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


    @NoRepeatSubmit
    @PostMapping("chat-to-ai")
    public Response chatToAI() {


        return Response.of();
    }

}
