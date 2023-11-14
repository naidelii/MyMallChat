package com.naidelii.chat.ws.domain.vo.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author naidelii
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUrl {

    private String loginUrl;
}
