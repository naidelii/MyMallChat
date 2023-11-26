package com.naidelii.chat.ws.domain.vo.response;

import lombok.Data;

/**
 * @author naidelii
 */
@Data
public class ResponseMessage<T> {
    /**
     * 业务类型
     * @see com.naidelii.chat.ws.domain.enums.ResponseTypeEnum
     */
    private Integer type;

    /**
     * 对应的实体类
     */
    private T data;
}
