package com.naidelii.chat.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.naidelii.data.domain.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 背包表
 *
 * @author naidelii
 * @date 2023-11-29 16:47:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user_backpack")
public class UserBackpack extends BaseEntity {
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
     * 物品id
     */
    private String itemId;
    /**
     * 使用状态 0.待使用 1已使用
     */
    private Integer status;
    /**
     * 幂等号
     */
    private String idempotent;

}
