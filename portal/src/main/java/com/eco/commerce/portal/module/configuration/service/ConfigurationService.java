package com.eco.commerce.portal.module.configuration.service;

import com.alibaba.fastjson.JSONObject;
import com.eco.commerce.core.module.configuration.enums.ConfigurationPlatformEnum;
import com.eco.commerce.core.module.configuration.model.Configuration;
import com.eco.commerce.core.module.configuration.service.ConfigurationCoreService;
import com.eco.commerce.core.utils.CustomizeException;
import com.eco.commerce.portal.cache.CacheDataUtil;
import com.eco.commerce.portal.module.configuration.dto.ro.ConfigurationRO;
import com.eco.commerce.portal.module.configuration.dto.vo.ConfigurationVO;
import com.eco.commerce.portal.module.openai.dto.vo.OpenAIConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ray
 * @since 2023/3/13
 */
@Service
public class ConfigurationService {

    @Autowired
    private ConfigurationCoreService configurationCoreService;

    public List<ConfigurationVO> findPlatformConfig() {
        return ConfigurationVO.ofList(configurationCoreService.findAllByPlatform(ConfigurationPlatformEnum.PORTAL.name()));
    }

    public void refreshConfig() {
        CacheDataUtil.configurationList = findPlatformConfig();

        if (!CacheDataUtil.configurationList.isEmpty()) {
            for (ConfigurationVO vo : CacheDataUtil.configurationList) {
                if (vo.getKey().equals("OPEN_AI")) {
                    CacheDataUtil.openAIConfigVO = JSONObject.parseObject(vo.getValue(), OpenAIConfigVO.class);
                }
            }
        }
    }

    @Transactional
    public ConfigurationVO updateConfiguration(String uid, ConfigurationRO ro) {
        Configuration configuration = configurationCoreService.findByUid(uid);
        if (configuration == null) {
            throw new CustomizeException("Cannot find configuration by uid");
        }
        configuration.setDescription(ro.getDescription());
        configuration.setValue(ro.getValue());
        configurationCoreService.update(configuration);
        return ConfigurationVO.of(configuration);
    }

    @Transactional
    public ConfigurationVO createConfiguration(ConfigurationRO ro) {
        if (configurationCoreService.isExistByKeyAndPlatform(ro.getKey(), ro.getPlatform())) {
            throw new CustomizeException("This Key and Platform is exist!!");
        }
        Configuration configuration = new Configuration();
        configuration.setKey(ro.getKey());
        configuration.setValue(ro.getValue());
        configuration.setPlatform(ro.getPlatform());
        configuration.setDescription(ro.getDescription());
        configurationCoreService.create(configuration);

        return ConfigurationVO.of(configuration);
    }

}
