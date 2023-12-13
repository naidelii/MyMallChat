package com.naidelii.chat.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.naidelii.data.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 联系人表
 *
 * @author naidelii
 * @date 2023-12-13 22:28:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user_friend")
public class UserFriend extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 好友的id
     */
    private String friendId;

    /**
     * 逻辑删除标志位删除状态(0：正常,1：已删除)
     */
    private Integer delFlag;

}
