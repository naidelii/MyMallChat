package com.naidelii.chat.ws.domain.vo.request;

import lombok.Data;

/**
 * @author naidelii
 */
@Data
public class RequestMessage {
    /**
     * 类型
     * @see com.naidelii.chat.ws.domain.enums.RequestTypeEnum
     */
    private Integer type;

    /**
     * 请求携带的数据
     */
    private String data;
}
