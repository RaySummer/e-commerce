package com.eco.commerce.portal.module.openai.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eco.commerce.core.module.member.dto.MemberDto;
import com.eco.commerce.core.utils.CustomizeException;
import com.eco.commerce.core.utils.WebThreadLocal;
import com.eco.commerce.portal.module.openai.dto.ro.ChatGPTRO;
import com.eco.commerce.portal.module.openai.dto.vo.ChatGPTVO;
import com.eco.commerce.portal.module.openai.dto.vo.OpenAIGenerateImageVO;
import com.eco.commerce.portal.module.openai.enums.ChatGPTTypeEnum;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ray
 * @since 2023/2/28
 */
@Slf4j
@Service
public class OpenAIService {

    @Value("${open.ai.private.key}")
    public String openAIKey;

    @Value("${open.ai.organization.id}")
    public String openAIOrganizationId;

    @Autowired
    private ChatGPTRecodeService chatGPTRecodeService;


    @Transactional
    public ChatGPTVO ChatToAI(ChatGPTRO chatGPTRO, MemberDto memberDto) throws Exception {
        String currentContent = chatGPTRO.getContent();

        // 1. 先找出之前的对话记录，然后把当前的内容放到对话记录的List里面
        ChatGPTVO chatGPTVO = chatGPTRecodeService.findRecodeByMember(memberDto);

        List<ChatGPTVO.ChatContentVO> chatContentVOList = chatGPTVO.getContents();

        if (chatContentVOList == null) {
            chatContentVOList = new ArrayList<>();
        }

        chatContentVOList.add(ChatGPTVO.ChatContentVO
                .builder()
                .content(chatGPTRO.getContent())
                .chatTime(new Date())
                .type(ChatGPTTypeEnum.TO_GPT.name())
                .build());

        log.warn("chatContentList: {}", JSONObject.parseArray(JSON.toJSONString(chatContentVOList)));

        //2. 生成OpenAI对象，把内容发送到OpenAI
        OpenAiService service = new OpenAiService(openAIKey);

        List<String> shopList = new ArrayList<>();
        shopList.add(memberDto.getNickName());
        shopList.add("AI");

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(currentContent)
                .temperature(0.9)
                .echo(true)
                .maxTokens(100)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.6)
                .stop(shopList)
                .user(memberDto.getNickName())
                .n(1)
                .build();
        CompletionResult completion = service.createCompletion(completionRequest);

        //3. 拿到OpenAI的回调数据，把数据拆分后封装到chatGPTVO
        String replyContent = "";
        for (CompletionChoice choices : completion.getChoices()) {
            replyContent = choices.getText();
        }
        log.warn("replyContent: {}", replyContent);

        //所有内容的字符长度计数器，用于截取回复内容，只需要回复内容不需要所有回复的记录
        int calcStrSum = 0;
        for (ChatGPTVO.ChatContentVO chatContentVO : chatContentVOList) {
            calcStrSum += chatContentVO.getContent().length();
        }
        log.warn("calcStrSum: {}", calcStrSum);

        if (StringUtils.isBlank(replyContent)) {
            throw new CustomizeException("OpenAI reply is null");
        }
        chatContentVOList.add(ChatGPTVO.ChatContentVO
                .builder()
                .content(replyContent.substring(calcStrSum))
                .chatTime(new Date())
                .type(ChatGPTTypeEnum.TO_ME.name())
                .build());
        log.warn("add reply after chatContentVOList : {}", JSONObject.parseArray(JSON.toJSONString(chatContentVOList)));
        chatGPTRecodeService.save(chatGPTVO);

        return chatGPTVO;
    }


    public OpenAIGenerateImageVO generateImage(ChatGPTRO chatGPTRO) {

        OpenAiService service = new OpenAiService(openAIKey);

        log.warn(" OpenAI creating image from content :{}", chatGPTRO.getContent());

        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(chatGPTRO.getContent())
                .n(1)
                .build();
        String imageLink = service.createImage(request).getData().get(0).getUrl();

        log.warn("OpenAI created image, The link is :{}", imageLink);

        return OpenAIGenerateImageVO.builder().imageLink(imageLink).build();
    }


}
