package com.yufu.idaas.sdk.constants;

/**
 * Created by shuowang on 2018/6/4.
 */
public class YufuTokenConstants {
    public final static String IDP_TOKEN_CONSUME_URL = "https://portal.yufuid.com/api/v1/{tenantId}/external/sso";
    public final static String TENANT_ID_KEY = "tnt_id";
    public final static String APP_INSTANCE_ID_KEY = "appInstanceId";
    public final static String KEY_ID_SEPARATOR = "###";
    public final static String AUDIENCE_YUFU = "yufu";
    public final static int TOKEN_EXPIRE_TIME_IN_MS = 300000;
}
