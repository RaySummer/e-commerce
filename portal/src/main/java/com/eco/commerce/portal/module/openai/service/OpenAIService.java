package com.eco.commerce.portal.module.openai.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eco.commerce.core.module.member.dto.MemberDto;
import com.eco.commerce.core.utils.CustomizeException;
import com.eco.commerce.core.utils.HttpClientResponse;
import com.eco.commerce.core.utils.HttpClientUtil;
import com.eco.commerce.portal.cache.CacheDataUtil;
import com.eco.commerce.portal.module.openai.dto.ro.ChatGPTRO;
import com.eco.commerce.portal.module.openai.dto.vo.ChatGPTVO;
import com.eco.commerce.portal.module.openai.dto.vo.OpenAIGenerateImageVO;
import com.eco.commerce.portal.module.openai.dto.vo.OpenAISpeechToTextVO;
import com.eco.commerce.portal.module.openai.enums.ChatGPTTypeEnum;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ray
 * @since 2023/2/28
 */
@Slf4j
@Service
public class OpenAIService {

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

        List<String> shopList = new ArrayList<>();
        shopList.add(memberDto.getNickName());
        shopList.add("AI");

        log.warn("chatContentList: {}", JSONObject.parseArray(JSON.toJSONString(chatContentVOList)));
        String replyContent = null;

//        try {
            //2. 生成OpenAI对象，把内容发送到OpenAI
            OpenAiService service = new OpenAiService(CacheDataUtil.openAIConfigVO.getApiKey());

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
            replyContent = "";
            for (CompletionChoice choices : completion.getChoices()) {
                replyContent = choices.getText();
            }
            log.warn("replyContent: {}", replyContent);
//        } catch (Exception e) {
//            log.error("Using OpenAI Service worn：{}", e.getMessage());
//
//            Map<String, Object> bodyMap = new HashMap<>();
//            bodyMap.put("model", "text-davinci-003");
//            bodyMap.put("prompt", currentContent);
//            bodyMap.put("temperature", 0.9);
//            bodyMap.put("max_tokens", 150);
//            bodyMap.put("frequency_penalty", 0);
//            bodyMap.put("presence_penalty", 0.6);
//            bodyMap.put("stop", shopList);
//            bodyMap.put("user", memberDto.getNickName());
//
//            String body = JSON.toJSONString(bodyMap);
//            log.warn("body:{}", body);
//
//            String result = HttpClientUtil.doPost(CacheDataUtil.openAIConfigVO.getOpenAIUrl(), body, CacheDataUtil.openAIConfigVO.getApiKey(), true);
//
//            log.warn(result);
//
//        }

        if (StringUtils.isBlank(replyContent)) {
            throw new CustomizeException("OpenAI reply is null");
        }

        //所有内容的字符长度计数器，用于截取回复内容，只需要回复内容不需要所有回复的记录
        int calcStrSum = 0;
        for (ChatGPTVO.ChatContentVO chatContentVO : chatContentVOList) {
            calcStrSum += chatContentVO.getContent().length();
        }
        log.warn("calcStrSum: {}", calcStrSum);

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

        OpenAiService service = new OpenAiService(CacheDataUtil.openAIConfigVO.getApiKey());

        log.warn(" OpenAI creating image from content :{}", chatGPTRO.getContent());

        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(chatGPTRO.getContent())
                .n(1)
                .build();
        String imageLink = service.createImage(request).getData().get(0).getUrl();

        log.warn("OpenAI created image, The link is :{}", imageLink);

        return OpenAIGenerateImageVO.builder().imageLink(imageLink).build();
    }

    public OpenAISpeechToTextVO speechToText(File uploadFile) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + CacheDataUtil.openAIConfigVO.getApiKey());

        Map<String, Object> param = new HashMap<>();

        param.put("file", uploadFile);
        param.put("model", "whisper-1");

        HttpClientResponse httpClientResponse = (HttpClientResponse) HttpClientUtil.multipartPost(CacheDataUtil.openAIConfigVO.getOpenAIUrl(), headers, param);

        if (httpClientResponse.getStatusCode() != 200) {
            throw new CustomizeException("http execute failed.");
        }
        log.warn("http response is {}", httpClientResponse.getEntityContent());
        return OpenAISpeechToTextVO.builder().text(httpClientResponse.getEntityContent()).build();
    }


}
