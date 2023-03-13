package com.eco.commerce.portal.module.configuration.dto.ro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ray
 * @since 2023/3/13
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationRO {

    private String platform;

    private String key;

    private String value;

    private String description;
}
