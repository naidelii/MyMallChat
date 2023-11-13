package com.naidelii.chat.ws.domain.vo.request;

import com.naidelii.chat.ws.domain.enums.WebSocketRequestTypeEnum;
import lombok.Data;

/**
 * @author naidelii
 */
@Data
public class WebSocketRequestMessage {
    /**
     * 类型
     * @see WebSocketRequestTypeEnum
     */
    private Integer type;

    /**
     * 请求携带的数据
     */
    private String data;
}
