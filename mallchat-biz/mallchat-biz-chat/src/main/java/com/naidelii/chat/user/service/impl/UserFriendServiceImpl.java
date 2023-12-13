package com.naidelii.chat.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.chat.user.domain.entity.UserFriend;
import com.naidelii.chat.user.mapper.UserFriendMapper;
import com.naidelii.chat.user.service.IUserFriendService;
import org.springframework.stereotype.Service;


/**
 * @author naidelii
 */
@Service
public class UserFriendServiceImpl extends ServiceImpl<UserFriendMapper, UserFriend> implements IUserFriendService {

}