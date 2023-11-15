package com.naidelii.constant;

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
     * 中文
     */
    String CHINESE = "zh_CN";

    /**
     * 默认的操作人
     */
    String DEFAULT_OPERATOR = "system";
}
