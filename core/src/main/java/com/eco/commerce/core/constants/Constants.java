package com.eco.commerce.core.constants;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Ray
 * @since 2023/2/15
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String COOKIE_NAME_BROWSER_FINGERPRINT = "browser-fingerprint";

    public static final Integer DEFAULT_PAGE_SIZE = 10;

    public static final Integer MAX_PAGE_SIZE = 200;

    public static final String JSONB_TYPE = "JsonbType";
    public static final String JSONB_ARRAY_TYPE = "JsonbArrayType";

    public static final String CURRENCY_USD = "USD";

    public static final String USER_TOKEN = "Authorization";
    public static final String MEMBER_TOKEN = "Member-Authorization";
    public static final String ACCESS_TOKEN = "Access-Token";
    public static final String HEADER_HOSTNAME = "hostname";
    public static final String HEADER_PROXY_COMPANY = "proxyCompany";
    public static final String HEADER_CLIENT_ZONE_ID = "clientZoneId";
    public static final String HEADER_LANGUAGE = "language";
    public static final String HEADER_PATH = "path";
    public static final String HEADER_COMPANY_UID = "companyUid";
    public static final String HEADER_THEME_UID = "themeUid";
    public static final String HEADER_CURRENCY = "currency";

    public static final String GUEST_USER = "Guest@imanagesystems.com";
    public static final String SYSTEM = "System";

    public static final String URL_SEPARATOR = "/";

    public static final String EMAIL_PATTERN = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    public static final String FIELD_DELETED_TIME = "deletedTime";
    public static final String FIELD_DISPLAY_ORDER = "displayOrder";
    public static final String FIELD_ID = "id";

    public static final List<String> allowUploadTypeList = ImmutableList.<String>builder()
            .add("bmp")
            .add("jpg")
            .add("png")
            .add("jpeg")
            .add("gif")
            .add("webp")
            .add("mp4")
            .add("txt")
            .add("html")
            .add("js")
            .add("css")
            .add("json")
            .add("xls")
            .add("csv")
            .add("xlsx")
            .add("svg")
            .add("ico")
            .build();

    public static final List<String> COMMON_FILTER_URL = ImmutableList.<String>builder()
            .add("/favicon.ico")
            .add("/robots.txt")
            .add("/docs")
            .add("/swagger-ui/")
            .add("/v3/api-docs/")
            .add("/doc.html")
            .add("/webjars/")
            .add("/error")
            .add("/ims-client")
            .add("/instances")
            .add("/webhook_endpoints")
            .add("/stripe/webhook")
            .build();

    public static final List<String> CONSOLE_FILTER_URL = ImmutableList.<String>builder()
            .addAll(COMMON_FILTER_URL)
            .add("/login")
            .add("/startup")
            .build();

    public static final List<String> PORTAL_FILTER_URL = ImmutableList.<String>builder()
            .addAll(COMMON_FILTER_URL)
            .add("/cms/parse-context")
            .add("/sys-config/")
            .add("/sys/")
            .add("/sitemap/")
            .add("/webhook_endpoints")
            .add("/stripe/webhook")
            .build();

    public static final String SKIP_REQUEST_IN_FILTER = "SKIP_REQUEST_IN_FILTER";

    public static final String THEME_FILENAME_THEME_JSON = "theme.json";
    public static final String COMPONENT_LIBRARY_FILENAME_JSON = "component-library.json";
    public static final String GLOBAL_COMPONENT_FILENAME_JSON = "global-component.json";
    public static final String HBS = "hbs";
    public static final String JSON = "json";
    public static final String I18N = "i18n";

    public static final String TOPIC_COMPANY_DTO_CACHE = "TOPIC_COMPANY_DTO_CACHE";
    public static final String TOPIC_COMPANY_THEME_DTO_CACHE = "TOPIC_COMPANY_THEME_DTO_CACHE";
    public static final String TOPIC_C_MENU_VO_CACHE = "TOPIC_C_MENU_VO_CACHE";
    public static final String TOPIC_PRODUCT_DTO_CACHE = "TOPIC_PRODUCT_DTO_CACHE";
    public static final String TOPIC_AUTHORITY_CACHE = "TOPIC_AUTHORITY_CACHE";
    public static final String TOPIC_C_PAGE_DTO_CACHE = "TOPIC_C_PAGE_DTO_CACHE";
    public static final String TOPIC_PAGE_DTO_CACHE = "TOPIC_PAGE_DTO_CACHE";
    public static final String TOPIC_SEO_ROUTER_DTO_CACHE = "TOPIC_SEO_ROUTER_DTO_CACHE";
    public static final String TOPIC_THEME_VERSION_GLOBAL_COMPONENT_DTO_CACHE = "TOPIC_THEME_VERSION_GLOBAL_COMPONENT_DTO_CACHE";
    public static final String TOPIC_SYS_CONFIG_DTO_CACHE = "TOPIC_SYS_CONFIG_DTO_CACHE";
    public static final String TOPIC_COMPANY_SETTING_DTO_CACHE = "TOPIC_COMPANY_SETTING_DTO_CACHE";

    public static final String TOPIC_DATA_ALTERATION_TIME = "TOPIC_DATA_ALTERATION_TIME";

    public static final String LOCK_PREFIX_VERSION_NUMBER_C_PAGE = "version_number:c_page:";
    public static final String LOCK_PREFIX_VERSION_NUMBER_PAGE_CONTENT = "version_number:page_content:";
    public static final String LOCK_PREFIX_VERSION_NUMBER_COMPANY_THEME = "version_number:company_theme:";
    public static final String LOCK_PREFIX_ROUTER_COMPANY = "router:company:";
    public static final String LOCK_PREFIX_EXCHANGE_RATE = "exchange_rate:";
    public static final String LOCK_PREFIX_DATA_ALTERATION_AUDIT = "data_alteration_time:";

    public static final String FORGET_PASSWORD_CACHE_PREFIX = "EMAIL:";
    public static final String EMAIL_VERIFY_PREFIX = "EMAIL_VERIFY:";

    public static final String RUNTIME_ENV_DEV = "dev";
    public static final String RUNTIME_ENV_STG = "stg";
    public static final String RUNTIME_ENV_UAT = "uat";
    public static final String RUNTIME_ENV_PROD = "prod";
    public static final String RUNTIME_ENV_LOCAL = "local";

    public static final String GLOBAL_EXCEPTION_ERROR_STACK = "GLOBAL_EXCEPTION_ERROR_STACK";

    public static final String REQUEST_ID = "x-request-id";
    public static final String MEMBER_UID = "member-uid";
    public static final String REQ_PATH = "req-path";
    public static final String COMPANY_UID = "company-uid";
    public static final String ACTIVITY_LOG = "ACTIVITY_LOG";
    public static final String MEMBER_LOGIN_MODE = "member-login-mode";

    public static final String PAYMENT_METHOD_MODE_LIVE = "LIVE";


    public static final Map<String, String> WHISKY_AREA_MAPPING = ImmutableMap.<String, String>builder()
            .put("American Whisky", "United States")
            .put("Scotch Whisky", "Scotland")
            .put("Japanese Whisky", "Japan")
            // Others Whisky对应除以上三个的其他country
            // .put("Others Whisky", "")
            .build();


}
