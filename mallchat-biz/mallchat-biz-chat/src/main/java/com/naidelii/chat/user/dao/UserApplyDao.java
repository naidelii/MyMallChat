package com.naidelii.chat.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.chat.user.domain.entity.UserApply;
import com.naidelii.chat.user.mapper.UserApplyMapper;
import org.springframework.stereotype.Repository;

/**
 * 好友申请表
 *
 * @author naidelii
 * @date 2023-12-13 22:28:31
 */
@Repository
public class UserApplyDao extends ServiceImpl<UserApplyMapper, UserApply> {

}
