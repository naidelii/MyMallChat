package com.naidelii.chat.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.mapper.SysUserMapper;
import com.naidelii.chat.user.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {


}
