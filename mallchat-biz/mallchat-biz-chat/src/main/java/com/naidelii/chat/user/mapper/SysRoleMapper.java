package com.naidelii.chat.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naidelii.chat.user.domain.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 角色表
 *
 * @author naidelii
 * @date 2023-12-05 09:35:52
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户id查询所拥有的角色
     *
     * @param userId 用户id
     * @return 角色列表
     */
    Set<String> getRolesByUserId(@Param("userId") String userId);
}
