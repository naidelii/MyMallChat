package com.naidelii.data.constant;

/**
 * @author naidelii
 */
public class RedisKey {

    private static final String BASE_KEY = "mallchat:";

    /**
     * 登录用户Token令牌缓存KEY前缀
     */
    public static final String PREFIX_USER_TOKEN = "userToken:%s";

    public static String getKey(String key, Object... objects) {
        return BASE_KEY + String.format(key, objects);
    }

}
