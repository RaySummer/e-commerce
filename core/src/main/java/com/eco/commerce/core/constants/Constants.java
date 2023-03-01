package com.eco.commerce.core.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Ray
 * @since 2023/2/15
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String COOKIE_NAME_BROWSER_FINGERPRINT = "browser-fingerprint";

    public static final String HEADER_TOKEN_NAME = "Authorization";

    public static final String FIELD_DELETED_TIME = "deletedTime";

    public static final String URL_SEPARATOR = "/";

    public static final String EMAIL_PATTERN = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    public static final String RUNTIME_ENV_LOCAL = "local";

}
