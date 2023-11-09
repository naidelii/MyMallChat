package com.naidelii.websocket.domain.vo.request;

import lombok.Data;

/**
 * @author naidelii
 */
@Data
public class WebSocketRequest {
    /**
     * 类型
     * @see com.naidelii.websocket.domain.enums.WebSocketRequestTypeEnum
     */
    private Integer type;

    /**
     * 请求携带的数据
     */
    private String data;
}
