package com.eco.commerce.core.module.configuration.service;

import com.eco.commerce.core.module.base.service.impl.BaseCrudServiceImpl;
import com.eco.commerce.core.module.configuration.model.Configuration;
import com.eco.commerce.core.module.configuration.repository.ConfigurationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ray
 * @since 2023/2/21
 */
@Slf4j
@Service
public class ConfigurationCoreService extends BaseCrudServiceImpl<ConfigurationRepository, Configuration, Long> {

}
