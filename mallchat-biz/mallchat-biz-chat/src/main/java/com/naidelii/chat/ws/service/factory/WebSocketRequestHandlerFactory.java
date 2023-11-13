package com.naidelii.chat.ws.service.factory;

import com.naidelii.chat.ws.domain.enums.WebSocketRequestTypeEnum;
import com.naidelii.chat.ws.service.WebSocketRequestMessageStrategyHandler;
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
public class WebSocketRequestHandlerFactory implements ApplicationContextAware {

    /**
     * 根据类型获取对应的处理策略
     */
    private static final Map<WebSocketRequestTypeEnum, WebSocketRequestMessageStrategyHandler> HANDLER_MAP = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        Map<String, WebSocketRequestMessageStrategyHandler> beansOfType = context.getBeansOfType(WebSocketRequestMessageStrategyHandler.class);
        for (Map.Entry<String, WebSocketRequestMessageStrategyHandler> entry : beansOfType.entrySet()) {
            WebSocketRequestMessageStrategyHandler value = entry.getValue();
            HANDLER_MAP.put(value.type(), value);
        }
    }

    /**
     * 根据类型获取WebSocketRequestMessageStrategyHandler
     *
     * @param type 类型
     * @return WebSocketRequestMessageStrategyHandler
     */
    public WebSocketRequestMessageStrategyHandler getHandlerByType(WebSocketRequestTypeEnum type) {
        WebSocketRequestMessageStrategyHandler handler = HANDLER_MAP.get(type);
        if (handler != null) {
            return handler;
        }
        // 如果没有对应的处理策略，返回一个默认的处理策略
        return HANDLER_MAP.get(WebSocketRequestTypeEnum.UNKNOWN);
    }

}
