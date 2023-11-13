package com.naidelii.chat.ws.domain.vo.response;

import lombok.Data;

/**
 * @author naidelii
 */
@Data
public class WebSocketResponseMessage<T> {
    /**
     * @see com.naidelii.chat.ws.domain.enums.WebSocketResponseTypeEnum
     */
    private Integer type;


    /**
     * 对应的实体类
     */
    private T data;
}
