package com.naidelii.chat.ws.service.factory;

import com.naidelii.chat.ws.domain.enums.RequestTypeEnum;
import com.naidelii.chat.ws.service.RequestMessageStrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author naidelii
 * WebSocket 请求处理策略工厂类
 */
@Component
@Slf4j
public class WebSocketRequestHandlerFactory implements ApplicationContextAware {

    /**
     * 根据类型获取对应的处理策略
     */
    private static final Map<RequestTypeEnum, RequestMessageStrategyHandler> HANDLER_MAP = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        Map<String, RequestMessageStrategyHandler> handlerMap = context.getBeansOfType(RequestMessageStrategyHandler.class);
        if (handlerMap.isEmpty()) {
            log.warn("RequestMessageStrategyHandler is Empty");
            return;
        }
        // 将所有的处理策略放入map中
        handlerMap.values().forEach(handler -> HANDLER_MAP.put(handler.type(), handler));
    }

    /**
     * 根据类型获取WebSocketRequestMessageStrategyHandler
     *
     * @param type 类型
     * @return WebSocketRequestMessageStrategyHandler
     */
    public RequestMessageStrategyHandler getHandlerByType(RequestTypeEnum type) {
        RequestMessageStrategyHandler handler = HANDLER_MAP.get(type);
        if (handler != null) {
            return handler;
        }
        // 如果没有对应的处理策略，返回一个默认的处理策略
        return HANDLER_MAP.get(RequestTypeEnum.UNKNOWN);
    }

}
