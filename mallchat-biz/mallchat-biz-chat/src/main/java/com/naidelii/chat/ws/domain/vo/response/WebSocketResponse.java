package com.naidelii.chat.ws.domain.vo.response;

import lombok.Data;

/**
 * @author naidelii
 */
@Data
public class WebSocketResponse<T> {
    /**
     * @see com.naidelii.chat.domain.enums.WebSocketResponseTypeEnum
     */
    private Integer type;


    /**
     * 对应的实体类
     */
    private T data;
}
