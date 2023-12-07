package com.naidelii.chat.ws.service.adapter;

import cn.hutool.json.JSONUtil;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.ws.domain.enums.ResponseTypeEnum;
import com.naidelii.chat.ws.domain.vo.request.RequestMessage;
import com.naidelii.chat.ws.domain.vo.response.BlackUser;
import com.naidelii.chat.ws.domain.vo.response.LoginSuccess;
import com.naidelii.chat.ws.domain.vo.response.LoginUrl;
import com.naidelii.chat.ws.domain.vo.response.ResponseMessage;
import com.naidelii.base.exception.MallChatException;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author naidelii
 */
@Slf4j
public class MessageAdapter {

    public static ResponseMessage<LoginUrl> buildLoginResp(String url) {
        ResponseMessage<LoginUrl> result = new ResponseMessage<>();
        result.setType(ResponseTypeEnum.LOGIN_URL.getType());
        result.setData(new LoginUrl(url));
        return result;
    }

    public static ResponseMessage<LoginSuccess> buildLoginSuccessResp(SysUser user, String token, Set<String> roles) {
        ResponseMessage<LoginSuccess> result = new ResponseMessage<>();
        result.setType(ResponseTypeEnum.LOGIN_SUCCESS.getType());
        LoginSuccess data = LoginSuccess
                .builder()
                .id(user.getId())
                .avatar(user.getAvatar())
                .nickname(user.getNickname())
                .token(token)
                .roles(roles)
                .build();
        result.setData(data);
        return result;
    }

    public static RequestMessage buildRequestMessage(String message) {
        try {
            // 转为实体类
            return JSONUtil.toBean(message, RequestMessage.class);
        } catch (Exception e) {
            log.error("{}：转换RequestMessage失败", message);
            throw new MallChatException("消息转换失败！");
        }
    }

    public static ResponseMessage<?> buildWaitAuthorize() {
        ResponseMessage<Object> result = new ResponseMessage<>();
        result.setType(ResponseTypeEnum.LOGIN_SCAN_SUCCESS.getType());
        return result;
    }

    public static ResponseMessage<?> buildInvalidToken() {
        ResponseMessage<Object> result = new ResponseMessage<>();
        result.setType(ResponseTypeEnum.INVALID_TOKEN.getType());
        return result;
    }

    public static ResponseMessage<BlackUser> buildBlack(SysUser user) {
        ResponseMessage<BlackUser> responseMessage = new ResponseMessage<>();
        BlackUser blackUser = new BlackUser();
        blackUser.setUserId(user.getId());
        responseMessage.setType(ResponseTypeEnum.BLACK.getType());
        responseMessage.setData(blackUser);
        return responseMessage;
    }
}
