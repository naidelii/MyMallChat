package com.naidelii.chat.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.chat.user.domain.entity.UserFriend;
import com.naidelii.chat.user.mapper.UserFriendMapper;
import org.springframework.stereotype.Repository;

/**
 * @author naidelii
 */
@Repository
public class UserFriendDao extends ServiceImpl<UserFriendMapper, UserFriend> {


}
