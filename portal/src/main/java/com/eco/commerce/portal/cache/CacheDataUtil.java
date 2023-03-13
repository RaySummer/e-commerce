package com.eco.commerce.portal.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eco.commerce.portal.module.configuration.dto.vo.ConfigurationVO;
import com.eco.commerce.portal.module.configuration.service.ConfigurationService;
import com.eco.commerce.portal.module.openai.dto.vo.OpenAIConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Ray
 * @since 2023/3/13
 */
@Component
public class CacheDataUtil {

    public static List<ConfigurationVO> configurationList = new ArrayList<>();

    public static OpenAIConfigVO openAIConfigVO = new OpenAIConfigVO();

    @Autowired
    private ConfigurationService configurationService;


    @PostConstruct
    public void init() {
        System.out.println("系统启动中。。。加载数据到缓存");
        //启动时加载configuration数据到缓存
        configurationList = configurationService.findPlatformConfig();

        if (!configurationList.isEmpty()) {
            for (ConfigurationVO vo : configurationList) {
                if (vo.getKey().equals("OPEN_AI")) {
                    openAIConfigVO = JSONObject.parseObject(JSON.toJSONString(JSON.parse(vo.getValue())), OpenAIConfigVO.class);
                }
            }
        }

    }

    @PreDestroy
    public void destroy() {
        System.out.println("系统运行结束");
    }

}
