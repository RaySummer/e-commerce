package com.eco.commerce.portal.module.configuration.controller;

import com.eco.commerce.core.utils.Response;
import com.eco.commerce.portal.module.configuration.dto.ro.ConfigurationRO;
import com.eco.commerce.portal.module.configuration.service.ConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ray
 * @since 2023/3/13
 */
@Slf4j
@RestController
@RequestMapping({"/configuration"})
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @GetMapping("/refresh")
    public Response refreshConfig() {
        configurationService.refreshConfig();
        return Response.of();
    }

    @PutMapping("#{uid}")
    public Response updateConfig(@PathVariable String uid, @RequestBody ConfigurationRO configurationRO) {
        return Response.of(configurationService.updateConfiguration(uid, configurationRO));
    }

    @PostMapping
    public Response createConfig(@RequestBody ConfigurationRO configurationRO) {
        return Response.of(configurationService.createConfiguration(configurationRO));
    }


}
