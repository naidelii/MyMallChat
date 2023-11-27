package com.naidelii.base.constant;

/**
 * @author naidelii
 * 基本常量
 */
public interface CommonConstants {

    /**
     * 请求头
     */
    String AUTHORIZATION = "Authorization";

    /**
     * 请求参数校验失败返回错误信息
     */
    String PARAM_VERIFY_ERROR_STR = "请求参数不符合要求";

    /**
     * 新用户扫码关注的事件前缀
     */
    String QR_SCENE_PREFIX = "qrscene_";

    /**
     * 用户激活状态
     */
    Integer USER_ACTIVATION_STATE = 1;

    /**
     * 用户冻结状态
     */
    Integer USER_FROZEN_STATE = 2;

    /**
     * 用户在线
     */
    Integer USER_ONLINE_PRESENCE = 1;

    /**
     * 用户离线
     */
    Integer USER_OFFLINE_STATUS = 2;


    /**
     * 中文
     */
    String CHINESE = "zh_CN";

    /**
     * 默认的操作人
     */
    String DEFAULT_OPERATOR = "system";

    /**
     * null
     */
    String NULL_STR = "null";

    /**
     * 项目共用线程池
     */
    String MALL_CHAT_EXECUTOR = "mallChatExecutor";

    /**
     * 项目共用线程池前缀
     */
    String MALL_CHAT_EXECUTOR_PREFIX = "mallChat-executor-";

}
